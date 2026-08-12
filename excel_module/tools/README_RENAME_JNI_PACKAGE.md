# Đổi package JNI trong `libso.so`

Script [`rename_jni_package.py`](./rename_jni_package.py) dùng để đổi package JNI được nhúng trong thư viện Android ELF (`.so`). Script không chỉ thay chuỗi package mà còn rebuild bảng `.hash` và `.gnu.hash`, để Android linker có thể tìm thấy các native method sau khi đổi package Kotlin/Java.

## Yêu cầu bắt buộc

- Máy đã cài Python 3.
- File đầu vào là ELF little-endian 32-bit hoặc 64-bit.
- Package cũ và package mới phải có **cùng độ dài theo byte** khi viết dưới dạng JNI path.
- Luôn giữ một bản `libso.so` gốc để khôi phục khi cần.

Ví dụ đang dùng trong module:

```text
com/artifex/solib
com/hwa/excelllll
```

Hai chuỗi trên đều dài 17 byte.

> Lưu ý: khi truyền vào script, package phải dùng dấu `/`, không dùng dấu `.`.

## Cách chạy

Chạy từ thư mục gốc project:

```powershell
python excel_module/tools/rename_jni_package.py `
  excel_module/src/main/jniLibs/arm64-v8a/libso.so `
  com/artifex/solib `
  com/hwa/excelllll
```

Patch tiếp bản `armeabi-v7a`:

```powershell
python excel_module/tools/rename_jni_package.py `
  excel_module/src/main/jniLibs/armeabi-v7a/libso.so `
  com/artifex/solib `
  com/hwa/excelllll
```

Cú pháp tổng quát:

```text
python rename_jni_package.py <file.so> <jni-package-cũ> <jni-package-mới>
```

Khi thành công, script in kết quả tương tự:

```text
libso.so: renamed 241 references and rebuilt ELF hashes
```

Script sửa trực tiếp file được truyền vào, không tự tạo file output mới.

## Đổi package trong source code

Sau khi patch `.so`, package của các class JNI trong Kotlin/Java cũng phải khớp chính xác.

Ví dụ:

```kotlin
package com.hwa.excelllll

class SOLib {
    private external fun preInitLib()
}
```

Các import, XML custom view và code sử dụng module cũng phải được cập nhật sang package mới.

Nếu build release có bật R8/ProGuard, cần giữ tên các class JNI. Module hiện dùng:

```proguard
-keep class com.hwa.excelllll.** { *; }
-keep interface com.hwa.excelllll.** { *; }
```

## Kiểm tra sau khi patch

Nếu máy có `readelf`, kiểm tra symbol JNI mới:

```powershell
readelf -Ws excel_module/src/main/jniLibs/arm64-v8a/libso.so |
  Select-String "Java_com_hwa_excelllll_SOLib_preInitLib"
```

Kết quả phải chứa symbol:

```text
Java_com_hwa_excelllll_SOLib_preInitLib
```

Kiểm tra package JNI cũ không còn trong file:

```powershell
rg -a "com/artifex/solib|Java_com_artifex_solib" `
  excel_module/src/main/jniLibs/arm64-v8a/libso.so
```

Lệnh trên không được trả về kết quả.

## Build và kiểm tra trên thiết bị

Rebuild native packaging và APK, nên dùng `--rerun-tasks` để tránh Gradle lấy lại output cũ từ cache:

```powershell
.\gradlew.bat --no-daemon `
  :excel_module:mergeDebugNativeLibs `
  :excel_module:bundleDebugAar `
  :app:assembleDebug `
  --rerun-tasks
```

Cài APK và theo dõi lỗi JNI:

```powershell
adb install -r <duong-dan-apk>
adb logcat -c
adb shell monkey -p com.example.testBaseApp `
  -c android.intent.category.LAUNCHER 1
adb logcat -d | Select-String "UnsatisfiedLinkError|FATAL EXCEPTION"
```

Nếu không có `UnsatisfiedLinkError` và process ứng dụng vẫn chạy thì native method đã được resolve thành công.

## Các lỗi thường gặp

### Package không cùng độ dài

Script sẽ dừng với lỗi:

```text
JNI package paths must have equal byte length
```

Không được chèn hoặc xóa byte trực tiếp trong `.so`, vì việc đó làm thay đổi offset và có thể phá cấu trúc ELF. Hãy chọn package mới có cùng độ dài hoặc rebuild thư viện từ source native.

### Không tìm thấy package cũ

Script sẽ báo:

```text
No references to ... found
```

Nguyên nhân thường gặp:

- File đã được patch trước đó.
- Truyền sai package cũ.
- Thư viện không chứa JNI package đó.

Không chạy lại script với package cũ trên một file đã patch. Muốn đổi tiếp, dùng package hiện tại làm đối số package cũ hoặc khôi phục file `.so` gốc.

### Build thành công nhưng runtime báo `UnsatisfiedLinkError`

Kiểm tra lần lượt:

1. Package Kotlin/Java có khớp package mới hay không.
2. Symbol `Java_com_...` mới có xuất hiện trong `readelf -Ws` hay không.
3. APK có đóng gói đúng file `.so` vừa patch hay đang dùng Gradle cache.
4. ABI của thiết bị có tồn tại trong `src/main/jniLibs` hay không.
5. R8/ProGuard có đổi tên class hoặc native method hay không.

## Phạm vi của script

Script xử lý việc đổi một prefix package JNI có cùng độ dài và rebuild bảng hash ELF. Script không:

- Đổi package trong Kotlin/Java/XML.
- Tự sửa cấu hình Gradle namespace.
- Tự thêm rule ProGuard.
- Hỗ trợ đổi sang package có độ dài khác.
- Thay thế việc test APK thật trên thiết bị.
