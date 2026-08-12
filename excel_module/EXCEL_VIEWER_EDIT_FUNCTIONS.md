# Mục đích các hàm edit trong `ExcelViewerView`

Tài liệu này mô tả các API chỉnh sửa được public bởi `ExcelViewerView` và cách dùng chúng từ UI Android.

## Nguyên tắc chung

- Chỉ gọi các hàm edit sau khi tài liệu đã mở và `onDocumentReady` được gọi.
- Các thao tác sửa ô tác động lên cell/range đang được chọn trong native document.
- Hầu hết hàm edit trả về `Boolean`:
  - `true`: lệnh đã được chuyển cho editor.
  - `false`: editor chưa sẵn sàng, không có selection phù hợp hoặc thao tác không được phép.
- Sau một edit thành công, view tự render lại sheet hiện tại và phát lại `onCellSelected` với trạng thái mới. Không cần gọi `refresh()` thêm.
- `copy()` là ngoại lệ: thao tác này không làm thay đổi tài liệu nên không render lại sheet.
- Sau khi gọi `close()`, không được gọi lại các API của view; các hàm edit sẽ phát sinh `IllegalStateException`.
- Nên dựa vào `selectedCell()` hoặc `onCellSelected` để bật/tắt các nút edit theo `canEditText`, `canCopy`, `canDelete` và `canPaste`.

## Sửa nội dung và clipboard

| Hàm | Mục đích | Điều kiện chính |
|---|---|---|
| `replaceSelection(value: String)` | Thay nội dung của cell/range đang chọn bằng `value`. | `canEditText == true` |
| `deleteSelection()` | Xóa nội dung của selection hiện tại. | `canDelete == true` |
| `copy()` | Sao chép selection vào clipboard; không sửa tài liệu. | `canCopy == true` |
| `cut()` | Sao chép rồi xóa selection. | Đồng thời `canCopy` và `canDelete` |
| `paste()` | Dán nội dung clipboard vào selection hiện tại. | `canPaste == true` |

## Định dạng chữ

Các hàm dưới đây áp dụng định dạng cho selection hiện tại:

| Hàm | Mục đích |
|---|---|
| `setBold(enabled)` | Bật/tắt chữ đậm. |
| `setItalic(enabled)` | Bật/tắt chữ nghiêng. |
| `setUnderline(enabled)` | Bật/tắt gạch chân. |
| `setStrikeThrough(enabled)` | Bật/tắt gạch ngang chữ. |
| `setFontName(name)` | Đặt tên font, ví dụ `"Arial"`. |
| `setFontSize(size)` | Đặt cỡ chữ bằng giá trị `Double`. |
| `setFontColor(color)` | Đặt màu chữ theo chuỗi màu mà native engine hỗ trợ. |

## Màu nền, căn chỉnh và đường viền

| Hàm | Mục đích |
|---|---|
| `setFillColor(color)` | Đặt fill color của selection. |
| `setBackgroundColor(color)` | Đặt màu nền của selection. |
| `setBackgroundTransparent()` | Xóa màu nền, chuyển nền sang trong suốt. |
| `setAlignment(value)` | Đặt căn lề ngang theo mã alignment của native engine. |
| `setLineColor(color)` | Đặt màu đường viền/line của selection. |

`setFillColor` và `setBackgroundColor` gọi hai API native khác nhau; không nên xem chúng là hai tên thay thế cho cùng một thao tác. Giá trị `color` và `alignment` hiện được truyền thẳng xuống native engine, vì vậy caller cần dùng đúng định dạng/mã mà engine quy định.

## Định dạng ô và kích thước

| Hàm | Mục đích |
|---|---|
| `setCellFormat(format)` | Đặt format hiển thị của ô, ví dụ format số, ngày hoặc phần trăm theo cú pháp native engine. |
| `setColumnWidth(width)` | Đặt độ rộng của cột đang chọn. |
| `setRowHeight(height)` | Đặt chiều cao của hàng đang chọn. |

Các giá trị `format`, `width` và `height` không được validate tại `ExcelViewerView`; chúng được chuyển trực tiếp cho native document.

## Thao tác sheet

| Hàm | Mục đích | Kết quả `false` khi |
|---|---|---|
| `deleteCurrentSheet()` | Xóa sheet đang hiển thị rồi chuyển sang sheet hợp lệ gần nhất. | Chỉ còn một sheet hoặc index hiện tại không hợp lệ. |
| `moveCurrentSheet(toIndex)` | Di chuyển sheet hiện tại tới `toIndex`. | Index nguồn/đích không hợp lệ hoặc editor chưa sẵn sàng. |
| `moveSheet(fromIndex, toIndex)` | Di chuyển một sheet bất kỳ và giữ sheet đang xem nhất quán. | Index nguồn/đích không thuộc `0 until sheetCount()`. |

Nếu nguồn và đích giống nhau, `moveSheet()` trả về `true` và không thay đổi tài liệu. Sau khi danh sách sheet được sửa và render thành công, callback `onSheetListChanged` nhận danh sách tên sheet mới.

## Các hàm hỗ trợ edit

| Hàm/thuộc tính | Mục đích |
|---|---|
| `selectedText()` | Lấy text hiện tại của selection; trả chuỗi rỗng nếu editor chưa sẵn sàng. |
| `selectedCell()` | Lấy snapshot vị trí, kích thước range, text và các quyền edit. |
| `selection` | Truy cập selection model để UI kiểm tra trạng thái chi tiết. |
| `refresh(onComplete)` | Render lại sheet hiện tại khi tài liệu bị thay đổi từ luồng khác ngoài các hàm edit của view. |

## Ví dụ tích hợp

```kotlin
excelViewer.onCellSelected = { state ->
    editButton.isEnabled = state.canEditText
    copyButton.isEnabled = state.canCopy
    deleteButton.isEnabled = state.canDelete
    pasteButton.isEnabled = state.canPaste
}

editButton.setOnClickListener {
    excelViewer.replaceSelection(input.text.toString())
}

boldButton.setOnCheckedChangeListener { _, checked ->
    excelViewer.setBold(checked)
}
```

## Luồng xử lý nội bộ

Các hàm edit ô/format đi qua `editIf()`:

1. Kiểm tra view chưa bị đóng.
2. Kiểm tra editor đã sẵn sàng.
3. Kiểm tra quyền của selection nếu thao tác có yêu cầu.
4. Gọi `ExcelEditorController`; controller sửa native document và đánh dấu document đã thay đổi.
5. Render lại sheet hiện tại.
6. Gửi lại trạng thái selection qua `onCellSelected`.

`edit()` là phiên bản nội bộ không có điều kiện quyền riêng, được các hàm định dạng sử dụng. `editIf()` là phiên bản có kiểm tra điều kiện, được các thao tác text và clipboard sử dụng.
