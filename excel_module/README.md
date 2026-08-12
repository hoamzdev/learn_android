# Hướng dẫn load, hiển thị và chỉnh sửa Excel (`.xls`, `.xlsx`)

`excel_module` là Android Library dùng native Artifex (`libso.so`) để mở và render file Excel. Module hiện hỗ trợ đúng hai định dạng:

- Excel 97–2003: `.xls`
- Excel Workbook: `.xlsx`

Module không parse Excel bằng Kotlin. File được mở thành `SODoc`, UI render document vào bitmap, sau đó các editor controller thay đổi vùng đang được chọn trong native document.

## 1. Thêm module

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

Module yêu cầu tối thiểu Android 7.0 (API 24). Native library hiện chỉ có cho `armeabi-v7a` và `arm64-v8a`; emulator/device `x86` hoặc `x86_64` sẽ không load được thư viện.

## 2. Khởi tạo engine một lần

Khởi tạo native engine trong `Application`:

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

Khai báo trong `AndroidManifest.xml` của app:

```xml
<application
    android:name=".App"
    ... />
```

Không gọi `DocumentEngine.shutdown()` trong từng Activity vì engine có vòng đời toàn ứng dụng.

## 3. Activity hoàn chỉnh: chọn, load và hiển thị file

Ví dụ sau dùng Storage Access Framework nên không cần quyền đọc toàn bộ bộ nhớ. Nó kiểm tra file, mở file trên IO thread và render page đầu tiên sau khi view đã layout:

```kotlin
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.hwa.excelllll.core.DocumentLoader
import com.hwa.excelllll.core.DocumentOpenResult
import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.excel.ExcelDocumentManager
import com.hwa.excelllll.excel.ExcelFileAccess
import com.hwa.excelllll.excel.ExcelNativeLoader
import com.hwa.excelllll.ui.editor.ExcelPageContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExcelViewerActivity : ComponentActivity() {

    private lateinit var root: FrameLayout
    private var viewer: ExcelPageContainer? = null

    private val manager by lazy {
        ExcelDocumentManager(
            context = applicationContext,
            opener = ExcelNativeLoader { path ->
                when (val opened = DocumentLoader.open(path)) {
                    is DocumentOpenResult.Success -> opened.document
                    is DocumentOpenResult.Error -> null
                }
            }
        )
    }

    private val selectFile = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let(::loadExcel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        root = FrameLayout(this)
        val selectButton = Button(this).apply {
            text = "Chọn file Excel"
            setOnClickListener {
                selectFile.launch(
                    arrayOf(
                        "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
                )
            }
        }

        root.addView(selectButton)
        setContentView(root)
    }

    private fun loadExcel(uri: Uri) {
        if (!ExcelFileAccess.isExcel(uri)) {
            showMessage("Chỉ hỗ trợ file .xls và .xlsx")
            return
        }

        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                manager.open(uri)
            }

            result
                .onSuccess(::showDocument)
                .onFailure {
                    showMessage(it.message ?: "Không mở được file Excel")
                }
        }
    }

    private fun showDocument(document: SODoc) {
        root.removeAllViews()

        val newViewer = ExcelPageContainer(this, document)
        viewer = newViewer
        root.addView(
            newViewer,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        // Đợi view có kích thước thật rồi mới tạo bitmap/render.
        newViewer.post {
            val bitmap = Bitmap.createBitmap(
                newViewer.width.coerceAtLeast(1),
                newViewer.height.coerceAtLeast(1),
                Bitmap.Config.ARGB_8888
            )

            newViewer.renderPage(index = 0, bitmap = bitmap) { status ->
                if (status != 0) {
                    showMessage("Render lỗi: $status")
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        // Đóng SODoc và xóa bản copy trong cache.
        manager.close()
        viewer = null
        super.onDestroy()
    }
}
```

Project sử dụng ví dụ này cần có `activity-ktx`, lifecycle KTX và Kotlin coroutines. App trong repository hiện đã có các dependency cần thiết.

## 4. Native opener hoạt động thế nào?

Đoạn sau là adapter nối manager với native JNI:

```kotlin
val opener = ExcelNativeLoader { path ->
    when (val result = DocumentLoader.open(path)) {
        is DocumentOpenResult.Success -> result.document
        is DocumentOpenResult.Error -> null
    }
}
```

`DocumentLoader.open()` bắt lỗi native và trả về `DocumentOpenResult`. `ExcelDocumentManager` chịu trách nhiệm:

1. Kiểm tra `Uri` là `.xls` hoặc `.xlsx`.
2. Copy nội dung vào thư mục cache của app.
3. Gọi opener để tạo `SODoc`.
4. Giữ document trong suốt vòng đời màn hình.
5. Đóng document và xóa file cache khi gọi `close()`.

## 5. Hiển thị page khác

`index` bắt đầu từ `0`. Ví dụ render page thứ hai:

```kotlin
viewer.renderPage(index = 1, bitmap = bitmap) { status ->
    if (status == 0) {
        // Render thành công.
    }
}
```

Khi kích thước view thay đổi (xoay màn hình hoặc split screen), tạo bitmap mới theo kích thước mới rồi gọi lại `renderPage()`.

## 6. Có nên dùng `ExcelEditorView`?

Module có sẵn:

```kotlin
val editorView = ExcelEditorFactory.create(this, document)
```

Tuy nhiên, ở trạng thái code hiện tại, `ExcelEditorView.bind()` mới tạo `ExcelPageView`; nó chưa tự gọi `setup()` và chưa cấp bitmap để render. Nếu mục tiêu là **load và nhìn thấy nội dung ngay**, dùng `ExcelPageContainer.renderPage()` như ví dụ phía trên.

`ExcelEditorView` phù hợp khi màn hình tự quản lý bitmap, page, zoom và các thao tác chỉnh sửa.

## 7. Quản lý vòng đời

- Mỗi màn hình nên giữ một `ExcelDocumentManager`.
- Gọi `manager.close()` khi không còn dùng document.
- Không gọi thêm `document.close()` nếu document đang do manager quản lý.
- `ExcelPageContainer` tự giải phóng page và render job khi bị detach khỏi window.
- Không dùng cùng một `SODoc` cho hai manager.

## 8. Lỗi thường gặp

### `UnsatisfiedLinkError`

Nguyên nhân thường là:

- Chạy trên ABI `x86` hoặc `x86_64`.
- Chưa gọi `DocumentEngine.initialize()`.
- Tên/signature JNI trong `libso.so` không khớp `DocumentLoader.openDocument()`.

Nếu lỗi nói không tìm thấy `DocumentLoader.openDocument`, cần nối `ExcelNativeLoader` với đúng hàm open document được export bởi bộ Artifex/JNI gốc. Đây là điểm adapter có chủ ý của module.

### Báo không hỗ trợ dù file đúng định dạng

`ExcelFileAccess.isExcel(uri)` hiện lấy extension từ `Uri.lastPathSegment`. Một số provider không để tên file trong URI, nên extension có thể rỗng dù MIME type đúng. Khi gặp trường hợp này, cần lấy tên từ `OpenableColumns.DISPLAY_NAME` hoặc bổ sung fallback đó vào `ExcelFileAccess.extensionOf()`.

### View trống dù load thành công

Kiểm tra:

- Đã đợi view layout xong bằng `viewer.post { ... }`.
- Bitmap có width và height lớn hơn `0`.
- Đã gọi `renderPage(0, bitmap)`, không chỉ tạo `ExcelEditorView`.
- `status` trong callback bằng `0`.

### File lớn gây tốn bộ nhớ

Bitmap `ARGB_8888` dùng xấp xỉ `width × height × 4` byte. Chỉ tạo bitmap theo vùng cần hiển thị và tái sử dụng bitmap khi có thể.

## 9. Luồng xử lý

```text
Uri (.xls/.xlsx)
    -> ExcelFileAccess kiểm tra extension
    -> copy file vào cache
    -> ExcelNativeLoader / DocumentLoader mở native
    -> SODoc
    -> ExcelPageContainer tạo SOPage
    -> render vào Bitmap
    -> DocPageView vẽ Bitmap lên màn hình
```

## 10. Bật chức năng chỉnh sửa

Các lệnh chỉnh sửa luôn tác động lên **ô hoặc vùng đang được chọn** trong `SODoc`:

```text
Người dùng chạm vào ô
    -> SOPage.select(...) tạo native selection
    -> ExcelEditorController thay đổi selection
    -> render lại page
    -> save document
```

Thêm property vào `ExcelViewerActivity`:

```kotlin
import com.hwa.excelllll.ui.editor.ExcelEditorController

private var editor: ExcelEditorController? = null
```

Khởi tạo sau khi load thành công:

```kotlin
private fun showDocument(document: SODoc) {
    editor = ExcelEditorController(document, applicationContext)

    // Phần tạo viewer và render page giữ nguyên như mục 3.
}
```

> Manager và editor đang cùng tham chiếu một `SODoc`. Với cấu trúc này, chỉ gọi `manager.close()` trong `onDestroy()`, không gọi thêm `editor.close()` để tránh đóng document hai lần.

## 11. Chọn ô bằng thao tác chạm

Các lệnh edit/format sẽ không có tác dụng nếu document chưa có selection. Sau khi lấy `DocPageView` từ `ExcelPageContainer.showPage()`, nối touch:

```kotlin
import android.view.MotionEvent

val pageView = newViewer.showPage(
    index = 0,
    width = newViewer.width.coerceAtLeast(1),
    height = newViewer.height.coerceAtLeast(1)
)

pageView.setOnTouchListener { _, event ->
    if (event.action == MotionEvent.ACTION_UP) {
        // onSingleTap nhận tọa độ màn hình nên dùng rawX/rawY.
        pageView.onSingleTap(
            screenX = event.rawX.toInt(),
            screenY = event.rawY.toInt(),
            selectCell = true
        )
        true
    } else {
        false
    }
}
```

Double tap:

```kotlin
pageView.onDoubleTap(event.rawX.toInt(), event.rawY.toInt())
```

Nếu tự làm drag selection:

```kotlin
pageView.setSelectionStart(startPointOnScreen)
pageView.setSelectionEnd(endPointOnScreen)
```

## 12. Sửa nội dung ô

```kotlin
editor?.replaceSelection("Nội dung mới")
editor?.deleteSelection()

val selectedText: String? = editor?.selection?.text()
```

Ví dụ mở dialog nhập nội dung:

```kotlin
val input = EditText(this).apply {
    setText(editor?.selection?.text().orEmpty())
}

AlertDialog.Builder(this)
    .setTitle("Sửa ô")
    .setView(input)
    .setPositiveButton("Lưu") { _, _ ->
        editor?.replaceSelection(input.text.toString())
        renderCurrentPage()
    }
    .setNegativeButton("Hủy", null)
    .show()
```

`ExcelCellEditView` và `ExcelEditorInputController` phụ thuộc `ExcelEditorView`. Với viewer tự render, dialog/EditText riêng dễ kiểm soát hơn.

## 13. Copy, cut và paste

```kotlin
editor?.copy()
editor?.cut()
editor?.paste()
```

Kiểm tra trước khi bật các nút toolbar:

```kotlin
val canCopy = editor?.selection?.canCopy() == true
val canPaste = editor?.selection?.canPaste() == true
val canDelete = editor?.selection?.canDelete() == true
```

Sau `cut()` hoặc `paste()`, render lại page.

## 14. Thêm và xóa dòng/cột

```kotlin
editor?.insertRowAbove()
editor?.insertRowBelow()
editor?.deleteRows()

editor?.insertColumnLeft()
editor?.insertColumnRight()
editor?.deleteColumns()
```

Các lệnh dùng selection hiện tại để xác định dòng/cột cần thao tác.

## 15. Format ô

```kotlin
editor?.setBold(true)
editor?.setItalic(true)
editor?.setUnderline(true)
editor?.setFontName("Arial")
editor?.setFontSize(14.0)
editor?.setFontColor("#FF0000")
editor?.setFillColor("#FFF2CC")
editor?.setAlignment(1)

editor?.mergeCells(true)  // Merge
editor?.mergeCells(false) // Unmerge
```

`ExcelFormattingController` còn có các lệnh chưa được facade expose:

```kotlin
editor?.formatting?.apply {
    strikeThrough(true)
    verticalAlignment(1)
    rotation(45f)
    lineColor("#000000")
    lineWidth(1f)
}
```

Giá trị alignment/line type phụ thuộc constant của native Artifex; cần đối chiếu bridge gốc trước khi hard-code trong production.

## 16. Render lại sau khi edit

Controller thay đổi native document nhưng không tự yêu cầu Android view vẽ lại. Gom edit và render vào một hàm:

```kotlin
private fun applyEdit(action: ExcelEditorController.() -> Unit) {
    val currentEditor = editor ?: return
    currentEditor.action()
    renderCurrentPage()
}
```

Ví dụ:

```kotlin
boldButton.setOnClickListener {
    applyEdit { setBold(true) }
}

deleteButton.setOnClickListener {
    applyEdit { deleteSelection() }
}
```

`renderCurrentPage()` thực hiện lại logic tạo bitmap và render trong mục 3. Với file lớn, nên tái sử dụng bitmap.

## 17. Lưu file đã chỉnh sửa

`ExcelEditorController.save()` nhận **đường dẫn filesystem**, không nhận `content:// Uri`.

```kotlin
import java.io.File

private fun saveEditedFile() {
    val output = File(filesDir, "edited_${System.currentTimeMillis()}.xlsx")

    editor?.save(output.absolutePath) { result, extra ->
        runOnUiThread {
            if (result == 0) {
                showMessage("Đã lưu: ${output.absolutePath}")
            } else {
                showMessage("Lưu thất bại: result=$result, extra=$extra")
            }
        }
    }
}
```

Nếu đầu vào là `.xls`, nên dùng extension `.xls` cho output. Chỉ đổi extension không đảm bảo native engine tự chuyển định dạng.

### Lưu ra `content:// Uri`

Storage Access Framework trả về URI trong khi native save cần path. Quy trình:

1. Save vào file tạm trong `cacheDir`.
2. Mở `contentResolver.openOutputStream(destinationUri)`.
3. Copy file tạm sang output stream.
4. Xóa file tạm.

```kotlin
private fun saveToUri(destination: Uri, extension: String) {
    val temp = File(cacheDir, "edited_${System.currentTimeMillis()}.$extension")

    editor?.save(temp.absolutePath) { result, _ ->
        if (result != 0) {
            temp.delete()
            runOnUiThread { showMessage("Không lưu được file") }
            return@save
        }

        runCatching {
            contentResolver.openOutputStream(destination)?.use { output ->
                temp.inputStream().use { input -> input.copyTo(output) }
            } ?: error("Không mở được output Uri")
        }.onSuccess {
            runOnUiThread { showMessage("Đã lưu file") }
        }.onFailure {
            runOnUiThread { showMessage(it.message ?: "Không copy được file") }
        }

        temp.delete()
    }
}
```

Nên chạy phần copy file trên `Dispatchers.IO`.

## 18. Trạng thái chỉnh sửa

```kotlin
val state = editor?.session?.state
```

- `READY`: document sẵn sàng hoặc vừa save thành công.
- `MODIFIED`: đã có lệnh chỉnh sửa.
- `SAVING`: đang lưu.
- `ERROR`: save lỗi.
- `CLOSED`: document đã đóng.

Có thể kiểm tra `MODIFIED` khi người dùng bấm Back để hỏi có muốn lưu.

## 19. Function nào cần dùng?

```text
DocumentEngine                 khởi tạo native
ExcelDocumentManager           mở/đóng file, quản lý cache
DocumentLoader                 tạo SODoc qua JNI
ExcelPageContainer/DocPageView hiển thị và chọn ô
ExcelEditorController          edit/format/clipboard/save
```

Không gọi function chỉ vì IDE báo chưa được sử dụng. Đặc biệt:

- `ExcelDocumentSession.loadFromUri()` chưa implement và sẽ throw exception.
- `ExcelSelectionModel.setLimits()` chưa implement và sẽ throw exception.
- `ExcelEditorView` chưa tự setup/render đầy đủ.
- API search chỉ cần dùng khi có chức năng tìm kiếm.

## 20. Giới hạn hiện tại

- Chỉ hỗ trợ `.xls` và `.xlsx`; chưa hỗ trợ `.xlsm`, `.xltx`, `.csv`.
- Chỉ có native binaries cho ARM 32-bit và ARM 64-bit.
- Chưa có API một bước kiểu `viewer.load(uri)`; caller phải nối manager, opener và render surface.
- Khả năng mở file phụ thuộc JNI symbol của `libso.so` có khớp `DocumentLoader` hay không.
- Ví dụ chỉ render một page tại một thời điểm; danh sách nhiều page, zoom và pan cần được màn hình app bổ sung.
- Selection handle, highlight và toolbar editor hoàn chỉnh chưa được tự dựng.
- Sau khi edit, caller phải chủ động render lại page.
- Manager và editor chưa thống nhất ownership của `SODoc`; tránh đóng document hai lần.
