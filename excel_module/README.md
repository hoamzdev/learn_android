# Excel Module for Android

`excel_module` dùng native Artifex (`libso.so`) để mở, hiển thị, chỉnh sửa, lưu và xuất PDF từ file Excel trên Android.

## Khả năng hiện tại

- Mở `.xls` và `.xlsx` từ `content:// Uri`.
- Hiển thị sheet, kéo hai chiều và pinch-to-zoom.
- Chọn ô/vùng, sửa text, copy/cut/paste.
- Thêm/xóa hàng và cột, merge/unmerge.
- Đổi font, cỡ chữ, màu, nền, căn lề và một số định dạng khác.
- Lưu document về `.xls`/`.xlsx`.
- Convert document sang PDF.

Native library hiện có cho:

- `arm64-v8a`
- `armeabi-v7a`

Không chạy trên emulator/device chỉ hỗ trợ `x86` hoặc `x86_64`. Min SDK của module là API 24.

---

## 1. Thêm module vào app

Trong `settings.gradle.kts`:

```kotlin
include(":excel_module")
```

Trong `build.gradle.kts` của app:

```kotlin
dependencies {
    implementation(project(":excel_module"))
}
```

Nếu release bật R8/ProGuard, module đã cung cấp consumer rule giữ các class JNI:

```proguard
-keep class com.hwa.excelllll.** { *; }
-keep interface com.hwa.excelllll.** { *; }
```

---

## 2. Khởi tạo native engine

Chỉ khởi tạo một lần trong `Application`:

```kotlin
import android.app.Application
import com.hwa.excelllll.core.DocumentEngine

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DocumentEngine.initialize(this)
    }
}
```

Khai báo Application trong manifest:

```xml
<application
    android:name=".App"
    ... />
```

Không gọi `DocumentEngine.shutdown()` trong từng Activity. Engine có vòng đời toàn ứng dụng.

---

## 3. Chỉ xem Excel bằng `ExcelViewerView`

Đây là cách đơn giản nhất nếu màn hình chỉ cần xem file.

### XML

```xml
<com.hwa.excelllll.ui.editor.ExcelViewerView
    android:id="@+id/excelViewer"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### Activity/Fragment

```kotlin
binding.excelViewer.onDocumentReady = {
    // Viewport đầu tiên đã render xong.
}

binding.excelViewer.onDocumentError = { message ->
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

binding.excelViewer.open(excelUri)
```

Đóng view khi màn hình kết thúc:

```kotlin
override fun onDestroy() {
    binding.excelViewer.close()
    super.onDestroy()
}
```

Sau khi gọi `close()`, không được tái sử dụng instance `ExcelViewerView` đó.

### Chọn file bằng Storage Access Framework

```kotlin
private val openExcel = registerForActivityResult(
    ActivityResultContracts.OpenDocument()
) { uri ->
    uri ?: return@registerForActivityResult

    contentResolver.takePersistableUriPermission(
        uri,
        Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
    binding.excelViewer.open(uri)
}

private fun selectExcel() {
    openExcel.launch(
        arrayOf(
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )
    )
}
```

Storage Access Framework không yêu cầu quyền đọc toàn bộ bộ nhớ.

> `ExcelViewerView` tự sở hữu và đóng `SODoc`. Nó phù hợp cho chỉ xem. Nếu cần sửa/lưu/PDF, dùng luồng ở phần tiếp theo để giữ được reference tới document.

---

## 4. Mở document để sửa, lưu hoặc convert PDF

Tạo manager ở Activity/Fragment:

```kotlin
private val documentManager by lazy {
    ExcelDocumentManager(
        applicationContext,
        ExcelNativeLoader { path ->
            when (val result = DocumentLoader.open(path)) {
                is DocumentOpenResult.Success -> result.document
                is DocumentOpenResult.Error -> null
            }
        }
    )
}

private var document: SODoc? = null
private var editor: ExcelEditorController? = null
private var pageContainer: ExcelPageContainer? = null
```

Các import cần dùng:

```kotlin
import com.hwa.excelllll.core.DocumentLoader
import com.hwa.excelllll.core.DocumentOpenResult
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.excel.ExcelDocumentManager
import com.hwa.excelllll.excel.ExcelNativeLoader
import com.hwa.excelllll.ui.editor.ExcelEditorController
import com.hwa.excelllll.ui.editor.ExcelPageContainer
```

Mở URI trên IO thread:

```kotlin
private fun openForEditing(uri: Uri) {
    lifecycleScope.launch {
        val result = withContext(Dispatchers.IO) {
            documentManager.open(uri)
        }

        result.onSuccess { doc ->
            document = doc
            editor = ExcelEditorController(doc, applicationContext)
            showEditableDocument(doc)
        }.onFailure { error ->
            Toast.makeText(
                this@ExcelActivity,
                error.message ?: "Không mở được file Excel",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
```

`ExcelDocumentManager` thực hiện các việc sau:

1. Kiểm tra `.xls`/`.xlsx`.
2. Copy URI vào cache vì native API cần filesystem path.
3. Mở và giữ `SODoc`.
4. Đóng document và xóa cache khi gọi `close()`.

---

## 5. Hiển thị document có thể chỉnh sửa

Giả sử layout có một `FrameLayout` tên `excelHost`:

```xml
<FrameLayout
    android:id="@+id/excelHost"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

Tạo page container:

```kotlin
private fun showEditableDocument(doc: SODoc) {
    val container = ExcelPageContainer(this, doc)
    pageContainer = container

    binding.excelHost.removeAllViews()
    binding.excelHost.addView(
        container,
        FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    )

    container.post {
        renderPage(pageIndex = 0)
        attachCellSelection(pageIndex = 0)
    }
}
```

Render page vào bitmap:

```kotlin
private var renderBitmap: Bitmap? = null

private fun renderPage(pageIndex: Int) {
    val container = pageContainer ?: return
    val width = container.width.coerceAtLeast(1)
    val height = container.height.coerceAtLeast(1)

    val bitmap = renderBitmap
        ?.takeIf { it.width == width && it.height == height }
        ?: Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565).also {
            renderBitmap = it
        }

    container.renderPage(pageIndex, bitmap) { status ->
        if (status != 0) {
            runOnUiThread {
                Toast.makeText(this, "Render lỗi: $status", Toast.LENGTH_LONG).show()
            }
        }
    }
}
```

Page index bắt đầu từ `0`.

---

## 6. Chọn ô và vùng cần sửa

### Lấy số lượng và tên sheet

Sau khi `ExcelDocumentManager.open()` trả về `SODoc`:

```kotlin
val count: Int = document.sheetCount()
val names: List<String> = document.sheetNames()

names.forEachIndexed { index, name ->
    Log.d("Excel", "Sheet $index: $name")
}
```

Lấy riêng một tên sheet:

```kotlin
val firstSheetName: String? = document.sheetName(0)
```

Các native function bên dưới là:

- `SODoc.getNumPagesInternal()` lấy số lượng sheet.
- `SODoc.getPage(index, null)` lấy page đại diện cho sheet.
- `SOPage.getPageTitle()` lấy tên sheet.

Nên dùng các wrapper `sheetCount()`, `sheetName()` và `sheetNames()` vì wrapper tự kiểm tra index và giải phóng `SOPage` sau khi đọc tên.

Các lệnh edit luôn tác động lên selection hiện tại. Cần chọn ô trước khi gọi editor:

```kotlin
private fun attachCellSelection(pageIndex: Int) {
    val container = pageContainer ?: return
    val pageView = container.showPage(
        pageIndex,
        container.width.coerceAtLeast(1),
        container.height.coerceAtLeast(1)
    )

    pageView.setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            pageView.onSingleTap(
                event.rawX.toInt(),
                event.rawY.toInt(),
                selectCell = true
            )
            true
        } else {
            false
        }
    }
}
```

Double tap:

```kotlin
pageView.onDoubleTap(event.rawX.toInt(), event.rawY.toInt())
```

Đọc selection hiện tại:

```kotlin
val text = editor?.selection?.text().orEmpty()
val canEdit = editor?.selection?.canEditText() == true
val canCopy = editor?.selection?.canCopy() == true
val canPaste = editor?.selection?.canPaste() == true
```

---

## 7. Sửa nội dung và cấu trúc sheet

Sửa hoặc xóa nội dung selection:

```kotlin
editor?.replaceSelection("Nội dung mới")
editor?.deleteSelection()
```

Clipboard:

```kotlin
editor?.copy()
editor?.cut()
editor?.paste()
```

Hàng và cột:

```kotlin
editor?.insertRowAbove()
editor?.insertRowBelow()
editor?.deleteRows()

editor?.insertColumnLeft()
editor?.insertColumnRight()
editor?.deleteColumns()
```

Merge/unmerge:

```kotlin
editor?.mergeCells(true)  // Merge
editor?.mergeCells(false) // Unmerge
```

Sau mỗi thay đổi, render lại page:

```kotlin
private fun applyEdit(action: ExcelEditorController.() -> Unit) {
    val controller = editor ?: return
    controller.action()
    renderPage(0)
}

// Ví dụ
applyEdit { replaceSelection("Hello") }
```

---

## 8. Định dạng ô

Các API thường dùng:

```kotlin
editor?.setBold(true)
editor?.setItalic(true)
editor?.setUnderline(true)
editor?.setFontName("Arial")
editor?.setFontSize(14.0)
editor?.setFontColor("#FF0000")
editor?.setFillColor("#FFF2CC")
editor?.setAlignment(1)
```

API nâng cao qua `formatting`:

```kotlin
editor?.formatting?.apply {
    strikeThrough(true)
    backgroundColor("#FFFFFF")
    backgroundTransparent()
    verticalAlignment(1)
    rotation(45f)
    lineColor("#000000")
    lineType(1)
    lineWidth(1f)
}
```

Giá trị alignment và line type là constant của native Artifex. Cần test giá trị mong muốn trước khi dùng cố định trong production.

---

## 9. Lưu file Excel

Native save nhận filesystem path, không nhận trực tiếp `content:// Uri`.

### Lưu vào bộ nhớ riêng của app

```kotlin
private fun saveExcelToAppStorage() {
    val output = File(filesDir, "edited_${System.currentTimeMillis()}.xlsx")

    editor?.save(output.absolutePath) { result, extra ->
        runOnUiThread {
            if (result == 0) {
                Toast.makeText(this, "Đã lưu: ${output.absolutePath}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Lưu lỗi: $result/$extra", Toast.LENGTH_LONG).show()
            }
        }
    }
}
```

Giữ đúng extension đầu vào. Nếu document mở từ `.xls`, nên lưu ra `.xls`; đổi extension thành `.xlsx` không đảm bảo native engine tự chuyển định dạng.

### Save As ra URI do người dùng chọn

Tạo destination bằng `CreateDocument`:

```kotlin
private val createExcel = registerForActivityResult(
    ActivityResultContracts.CreateDocument(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )
) { uri ->
    uri?.let { saveExcelToUri(it, "xlsx") }
}

private fun requestSaveAs() {
    createExcel.launch("edited.xlsx")
}
```

Native save vào file tạm, sau đó copy sang URI:

```kotlin
private fun saveExcelToUri(destination: Uri, extension: String) {
    val temp = File(cacheDir, "excel_export_${System.currentTimeMillis()}.$extension")
    val controller = editor ?: return

    controller.save(temp.absolutePath) { result, extra ->
        if (result != 0) {
            temp.delete()
            runOnUiThread {
                Toast.makeText(this, "Lưu lỗi: $result/$extra", Toast.LENGTH_LONG).show()
            }
            return@save
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val copyResult = runCatching {
                contentResolver.openOutputStream(destination, "w")?.use { output ->
                    temp.inputStream().use { input -> input.copyTo(output) }
                } ?: error("Không mở được URI đầu ra")
            }
            temp.delete()

            withContext(Dispatchers.Main) {
                copyResult.onSuccess {
                    Toast.makeText(this@ExcelActivity, "Đã lưu Excel", Toast.LENGTH_LONG).show()
                }.onFailure {
                    Toast.makeText(this@ExcelActivity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
```

`result == 0` nghĩa là native save thành công.

---

## 10. Convert Excel sang PDF

`ExcelEditorController` chưa expose `savePdf()`, vì vậy dùng `SODoc.savePdf()` trên document đang mở.

### Convert vào file trong app

```kotlin
import com.hwa.excelllll.SODocSaveListener

private fun convertToPdf() {
    val doc = document ?: return
    val output = File(filesDir, "excel_${System.currentTimeMillis()}.pdf")

    doc.savePdf(
        output.absolutePath,
        false,
        SODocSaveListener { result, extra ->
            runOnUiThread {
                if (result == 0) {
                    Toast.makeText(this, "PDF: ${output.absolutePath}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Convert PDF lỗi: $result/$extra", Toast.LENGTH_LONG).show()
                }
            }
        }
    )
}
```

Tham số Boolean được chuyển trực tiếp tới native Artifex (`includeAnnotations`/`flatten` tùy cách gọi API). Với Excel thông thường có thể truyền `false`.

### Export PDF ra `content:// Uri`

```kotlin
private val createPdf = registerForActivityResult(
    ActivityResultContracts.CreateDocument("application/pdf")
) { uri ->
    uri?.let(::convertPdfToUri)
}

private fun requestPdfDestination() {
    createPdf.launch("converted.pdf")
}
```

```kotlin
private fun convertPdfToUri(destination: Uri) {
    val doc = document ?: return
    val temp = File(cacheDir, "pdf_export_${System.currentTimeMillis()}.pdf")

    doc.savePdf(temp.absolutePath, false, SODocSaveListener { result, extra ->
        if (result != 0) {
            temp.delete()
            runOnUiThread {
                Toast.makeText(this, "Convert lỗi: $result/$extra", Toast.LENGTH_LONG).show()
            }
            return@SODocSaveListener
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val copyResult = runCatching {
                contentResolver.openOutputStream(destination, "w")?.use { output ->
                    temp.inputStream().use { input -> input.copyTo(output) }
                } ?: error("Không mở được URI PDF")
            }
            temp.delete()

            withContext(Dispatchers.Main) {
                copyResult.onSuccess {
                    Toast.makeText(this@ExcelActivity, "Đã xuất PDF", Toast.LENGTH_LONG).show()
                }.onFailure {
                    Toast.makeText(this@ExcelActivity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    })
}
```

Nếu vừa chỉnh sửa document, có thể gọi `savePdf()` trực tiếp; PDF được tạo từ trạng thái document hiện tại trong bộ nhớ.

---

## 11. Trạng thái chỉnh sửa

```kotlin
when (editor?.session?.state) {
    ExcelDocumentState.READY -> Unit
    ExcelDocumentState.MODIFIED -> Unit
    ExcelDocumentState.SAVING -> Unit
    ExcelDocumentState.ERROR -> Unit
    ExcelDocumentState.CLOSED -> Unit
    null -> Unit
}
```

Có thể kiểm tra `MODIFIED` khi người dùng bấm Back để hỏi lưu thay đổi.

Native document cũng cung cấp:

```kotlin
val modified = document?.getHasBeenModified() == true
```

---

## 12. Lifecycle và quyền sở hữu document

Với luồng edit, `ExcelDocumentManager` là owner của `SODoc`. Chỉ đóng một lần:

```kotlin
override fun onDestroy() {
    renderBitmap = null
    pageContainer = null
    editor = null
    document = null
    documentManager.close()
    super.onDestroy()
}
```

Không gọi đồng thời `editor.close()`, `document.close()` và `documentManager.close()` trên cùng document, vì sẽ destroy native object nhiều lần.

Quy tắc:

- Dùng `ExcelViewerView`: chỉ gọi `viewer.close()`.
- Dùng `ExcelDocumentManager`: chỉ gọi `manager.close()`.
- Tự mở `SODoc` không qua manager: code sở hữu document phải gọi `document.close()`.

Không đóng document khi save hoặc convert PDF đang chạy. Chờ callback hoàn tất trước khi rời màn hình.

---

## 13. Lỗi thường gặp

### `UnsatisfiedLinkError`

Kiểm tra:

- Đã gọi `DocumentEngine.initialize()` chưa.
- Device có ABI `arm64-v8a` hoặc `armeabi-v7a` không.
- APK có đóng gói đúng `libso.so` không.
- Package JNI có đúng `com.hwa.excelllll` không.
- R8/ProGuard có đổi tên class native không.

### File mở được nhưng view trống

- Chờ view layout xong bằng `post { ... }` trước khi render.
- Bitmap phải có width/height lớn hơn `0`.
- Callback render thành công khi status bằng `0`.
- Không đóng manager/document trước callback render.

### Save/PDF không tạo file ở URI

- Native API chỉ nhận filesystem path.
- Phải save vào file tạm rồi copy sang `content:// Uri`.
- Kiểm tra `openOutputStream()` không trả `null`.
- Chỉ copy sau khi callback native trả `result == 0`.

### File không được nhận diện là Excel

Module chỉ chấp nhận `.xls` và `.xlsx`. Tên do provider trả về phải có extension hợp lệ. `.xlsm`, `.xltx`, `.csv` hiện chưa được hỗ trợ bởi lớp kiểm tra file.

---

## 14. API nên dùng

| Mục đích | API |
|---|---|
| Khởi tạo native | `DocumentEngine` |
| Chỉ xem nhanh | `ExcelViewerView` |
| Mở/đóng URI và quản lý cache | `ExcelDocumentManager` |
| Native document | `SODoc` |
| Hiển thị khi cần edit | `ExcelPageContainer` / `DocPageView` |
| Edit, format, clipboard, save | `ExcelEditorController` |
| Convert PDF | `SODoc.savePdf()` |

Luồng chỉ xem:

```text
Uri -> ExcelViewerView.open() -> ExcelDocumentManager -> SODoc -> viewport bitmap
```

Luồng chỉnh sửa và export:

```text
Uri
  -> ExcelDocumentManager.open()
  -> SODoc
  -> ExcelPageContainer hiển thị/chọn ô
  -> ExcelEditorController chỉnh sửa
  -> editor.save(path) lưu Excel
  -> document.savePdf(path) xuất PDF
```

---

## 15. Giới hạn hiện tại

- Chỉ xác nhận hỗ trợ `.xls` và `.xlsx`.
- Chưa có native library cho `x86`/`x86_64`.
- `ExcelViewerView` chỉ expose luồng xem, không expose document để edit.
- `ExcelEditorView` chưa tự setup/render đầy đủ; luồng edit nên dùng `ExcelPageContainer`.
- Save và PDF cần filesystem path trung gian khi đích là `content:// Uri`.
- Page count hiện chưa có native API công khai đáng tin cậy trong wrapper.

Tài liệu đổi package JNI riêng nằm tại [`tools/README_RENAME_JNI_PACKAGE.md`](./tools/README_RENAME_JNI_PACKAGE.md).
