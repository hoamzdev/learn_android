package com.hwa.excelllll.solib;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.supportv1.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.artifex.mupdf.fitz.PDFWidget;
import defpackage.e6;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static SOSecureFS f4187a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static String f4188b;

    public static boolean a(String str, String str2, boolean z) {
        if (str == null || str2 == null) {
            return false;
        }
        SOSecureFS sOSecureFS = f4187a;
        if (sOSecureFS == null || !(sOSecureFS.isSecurePath(str) || f4187a.isSecurePath(str2))) {
            File file = new File(str2);
            File file2 = new File(str);
            if (!z && file.exists()) {
                return false;
            }
            if (z && file.exists() && !e(str2)) {
                return false;
            }
            return a(file2, file);
        }
        if (!f4187a.isSecurePath(str2)) {
            File file3 = new File(str2);
            if (!z && file3.exists()) {
                return false;
            }
            if (z && file3.exists() && !e(str2)) {
                return false;
            }
        } else {
            if (!z && f4187a.fileExists(str2)) {
                return false;
            }
            if (z && f4187a.fileExists(str2) && !f4187a.deleteFile(str2)) {
                return false;
            }
        }
        return f4187a.copyFile(str, str2);
    }

    public static String b(Context context, Uri uri) {
        StringBuilder sb;
        String str;
        String strC = c(context, uri);
        if (g(strC).equals("")) {
            strC = defpackage.h.j(strC, ".", a(context, uri));
        }
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                String str2 = c(context) + "/shared/" + UUID.randomUUID();
                c(str2);
                String str3 = str2 + "/" + strC;
                if (b(str3)) {
                    e(str3);
                }
                SOSecureFS sOSecureFS = f4187a;
                Object fileOutputStream = (sOSecureFS == null || !sOSecureFS.isSecurePath(str3)) ? new FileOutputStream(str3) : f4187a.getFileHandleForWriting(str3);
                if (fileOutputStream == null) {
                    return "---";
                }
                byte[] bArr = new byte[PDFWidget.PDF_TX_FIELD_IS_MULTILINE];
                while (true) {
                    int i = inputStreamOpenInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    if (fileOutputStream instanceof FileOutputStream) {
                        ((FileOutputStream) fileOutputStream).write(bArr, 0, i);
                    } else {
                        f4187a.writeToFile(fileOutputStream, Arrays.copyOf(bArr, i));
                    }
                }
                if (fileOutputStream instanceof FileOutputStream) {
                    ((FileOutputStream) fileOutputStream).close();
                    return str3;
                }
                f4187a.closeFile(fileOutputStream);
                return str3;
            } catch (IOException e) {
                e = e;
                e.printStackTrace();
                sb = new StringBuilder();
                str = "---IOException ";
                sb.append(str);
                sb.append(e.getMessage());
                return sb.toString();
            } catch (SecurityException e2) {
                e = e2;
                e.printStackTrace();
                sb = new StringBuilder();
                str = "---SecurityException ";
                sb.append(str);
                sb.append(e.getMessage());
                return sb.toString();
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                sb = new StringBuilder();
                str = "---Exception ";
                sb.append(str);
                sb.append(e.getMessage());
                return sb.toString();
            }
        } catch (Exception unused) {
            return "---fileOpen";
        }
    }

    private static String c(Context context, Uri uri) {
        Uri uri2;
        String scheme = uri.getScheme();
        String path = uri.getPath();
        String string = uri.toString();
        if (scheme != null) {
            if (scheme.equalsIgnoreCase("content")) {
                String string2 = null;
                try {
                    uri2 = uri;
                    try {
                        Cursor cursorQuery = context.getContentResolver().query(uri2, new String[]{"_display_name"}, null, null, null);
                        if (cursorQuery != null && cursorQuery.moveToFirst()) {
                            string2 = cursorQuery.getString(cursorQuery.getColumnIndex("_display_name"));
                            cursorQuery.close();
                        }
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                    uri2 = uri;
                }
                if (string2 != null) {
                    return string2;
                }
                String str = (string.contains("attachment") || string.contains("mail")) ? "attachment" : "content";
                String strA = a(context, uri2);
                return (strA == null || strA.length() <= 0) ? str : defpackage.h.j(str, ".", strA);
            }
        }
        return path != null ? new File(path).getName() : "file";
    }

    public static boolean d(Context context) {
        if (f4187a != null) {
            return false;
        }
        boolean z = ContextCompat.a(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        if (ContextCompat.a(context, "android.permission.READ_EXTERNAL_STORAGE") != 0 || !z) {
            return false;
        }
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static boolean e(String str) {
        if (str == null) {
            return false;
        }
        SOSecureFS sOSecureFS = f4187a;
        if (sOSecureFS != null && sOSecureFS.isSecurePath(str)) {
            return f4187a.deleteFile(str);
        }
        try {
            File file = new File(new File(str).getAbsolutePath());
            if (!file.exists()) {
                return true;
            }
            file.delete();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static Object f(String str) {
        if (str == null) {
            return null;
        }
        SOSecureFS sOSecureFS = f4187a;
        if (sOSecureFS != null) {
            return sOSecureFS.getFileHandleForReading(str);
        }
        try {
            return new FileInputStream(str);
        } catch (FileNotFoundException | SecurityException unused) {
            return null;
        }
    }

    public static String g(String str) {
        if (str == null) {
            return "";
        }
        String[] strArrSplit = str.split("\\.");
        return strArrSplit.length <= 1 ? "" : strArrSplit[strArrSplit.length - 1].toLowerCase();
    }

    public static boolean h(String str) {
        return c(str, "pdf") || c(str, "epub") || c(str, "svg") || c(str, "xps") || c(str, "fb2") || c(str, "cbz") || c(str, "xhtml");
    }

    private static boolean i(String str) {
        File file = new File(str);
        try {
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    i(file2.getPath());
                }
            }
            file.delete();
            return true;
        } catch (NullPointerException unused) {
            Log.e("FileUtils", "deleteRecursive() failed  [" + str + "]: Have storage permissions been granted");
            return false;
        } catch (SecurityException unused2) {
            return false;
        }
    }

    public static boolean d(String str) {
        if (str == null) {
            return false;
        }
        SOSecureFS sOSecureFS = f4187a;
        return (sOSecureFS == null || !sOSecureFS.isSecurePath(str)) ? i(str) : f4187a.recursivelyRemoveDirectory(str);
    }

    public static String c(Context context) {
        SOSecureFS sOSecureFS = f4187a;
        return sOSecureFS != null ? sOSecureFS.getTempPath() : context.getFilesDir().toString();
    }

    public static String c(Context context, String str) {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            inputStreamOpen.close();
            return new String(bArr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean c(String str) {
        if (str == null) {
            return false;
        }
        SOSecureFS sOSecureFS = f4187a;
        if (sOSecureFS != null && sOSecureFS.isSecurePath(str)) {
            return f4187a.createDirectory(str);
        }
        try {
            return new File(str).mkdirs();
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean c(String str, String str2) {
        return str.toLowerCase().endsWith("." + str2);
    }

    public static long a(String str) {
        if (str == null) {
            return 0L;
        }
        SOSecureFS sOSecureFS = f4187a;
        return sOSecureFS != null ? sOSecureFS.getFileAttributes(str).lastModified : new File(str).lastModified();
    }

    public static String a(Context context, Uri uri) {
        return a(context, uri, (String) null);
    }

    public static String a(Context context, Uri uri, String str) {
        String scheme = uri.getScheme();
        if (scheme != null && scheme.equalsIgnoreCase("content")) {
            MimeTypeMap singleton = MimeTypeMap.getSingleton();
            String type = context.getContentResolver().getType(uri);
            if (type != null) {
                str = type;
            }
            String extensionFromMimeType = singleton.getExtensionFromMimeType(str);
            if (extensionFromMimeType != null) {
                return extensionFromMimeType;
            }
        }
        return a(uri);
    }

    public static String a(Context context, String str) throws IOException {
        String strC = c(context);
        if (!b(strC) && !c(strC)) {
            throw new IOException();
        }
        String strP = defpackage.h.p(e6.t(strC), File.separator, str);
        if (b(strP)) {
            e(strP);
        }
        Object fileHandleForWriting = f4187a.getFileHandleForWriting(strP);
        if (fileHandleForWriting == null) {
            throw new IOException();
        }
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            try {
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStreamOpen.read(bArr);
                        if (i <= 0) {
                            f4187a.closeFile(fileHandleForWriting);
                            inputStreamOpen.close();
                            return strP;
                        }
                        f4187a.writeToFile(fileHandleForWriting, Arrays.copyOf(bArr, i));
                    }
                } catch (Throwable th) {
                    f4187a.closeFile(fileHandleForWriting);
                    throw th;
                }
            } catch (Throwable th2) {
                inputStreamOpen.close();
                throw th2;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(Uri uri) {
        String name = new File(uri.getPath()).getName();
        int iLastIndexOf = name.lastIndexOf(46);
        if (iLastIndexOf > 0) {
            return name.substring(iLastIndexOf + 1);
        }
        return null;
    }

    public static void a(Context context) {
        String str;
        try {
            SOSecureFS sOSecureFSA = k.a();
            f4187a = sOSecureFSA;
            if (sOSecureFSA != null) {
            } else {
                throw new ClassNotFoundException();
            }
        } catch (ClassNotFoundException unused) {
            Log.i("FileUtils", "SecureFS implementation unavailable");
        } catch (ExceptionInInitializerError unused2) {
            str = "init() experienced unexpected exception [ExceptionInInitializerError]";
            Log.e("FileUtils", str);
        } catch (LinkageError unused3) {
            str = "init() experienced unexpected exception [LinkageError]";
            Log.e("FileUtils", str);
        } catch (SecurityException unused4) {
            str = "init() experienced unexpected exception [SecurityException]";
            Log.e("FileUtils", str);
        }
    }

    public static boolean a(File file, File file2) {
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream3 = null;
        BufferedOutputStream bufferedOutputStream4 = null;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                byte[] bArr = new byte[PDFWidget.PDF_BTN_FIELD_IS_RADIO];
                BufferedInputStream bufferedInputStream3 = new BufferedInputStream(new FileInputStream(file));
                try {
                    BufferedOutputStream bufferedOutputStream5 = new BufferedOutputStream(new FileOutputStream(file2, false));
                    int i = 0;
                    while (i != -1) {
                        try {
                            i = bufferedInputStream3.read(bArr);
                            if (i > 0) {
                                bufferedOutputStream5.write(bArr, 0, i);
                            }
                        } catch (Exception unused) {
                            bufferedOutputStream3 = bufferedOutputStream5;
                            bufferedOutputStream2 = bufferedOutputStream3;
                            bufferedInputStream2 = bufferedInputStream3;
                            if (bufferedInputStream2 != null) {
                                bufferedInputStream2.close();
                            }
                            if (bufferedOutputStream2 != null) {
                                bufferedOutputStream2.close();
                            }
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedOutputStream4 = bufferedOutputStream5;
                            bufferedOutputStream = bufferedOutputStream4;
                            bufferedInputStream = bufferedInputStream3;
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }
                            if (bufferedOutputStream == null) {
                                throw th;
                            }
                            bufferedOutputStream.close();
                            throw th;
                        }
                    }
                    bufferedInputStream3.close();
                    bufferedOutputStream5.close();
                    return true;
                } catch (Exception unused2) {
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception unused3) {
                bufferedOutputStream2 = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedOutputStream = null;
            }
        } catch (FileNotFoundException | IOException unused4) {
            return false;
        }
    }

    public static boolean a(Object obj) {
        SOSecureFS sOSecureFS = f4187a;
        if (sOSecureFS != null) {
            return sOSecureFS.closeFile(obj);
        }
        if (obj != null) {
            try {
                ((FileInputStream) obj).close();
            } catch (IOException unused) {
                return false;
            }
        }
        return obj != null;
    }

    public static boolean a(String str, String str2) {
        if (str == null || str2 == null) {
            return false;
        }
        SOSecureFS sOSecureFS = f4187a;
        return sOSecureFS != null ? sOSecureFS.renameFile(str, str2) : new File(str).renameTo(new File(str2));
    }

    public static int a(Object obj, byte[] bArr) {
        if (obj == null) {
            return -1;
        }
        SOSecureFS sOSecureFS = f4187a;
        if (sOSecureFS != null) {
            return sOSecureFS.readFromFile(obj, bArr);
        }
        try {
            return ((FileInputStream) obj).read(bArr);
        } catch (IOException unused) {
            return -1;
        }
    }

    public static File b(Context context) {
        String str = f4188b;
        if (str == null) {
            return new File(Environment.getExternalStorageDirectory(), "Documents");
        }
        if (!b(str)) {
            c(f4188b);
        }
        return new File(f4188b);
    }

    public static String b(Context context, String str) {
        if (f4187a != null) {
            try {
                return a(context, str);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        File file = new File(context.getExternalCacheDir(), str);
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStreamOpen.read(bArr);
                        if (i <= 0) {
                            fileOutputStream.close();
                            inputStreamOpen.close();
                            return file.getAbsolutePath();
                        }
                        fileOutputStream.write(bArr, 0, i);
                    }
                } catch (Throwable th) {
                    fileOutputStream.close();
                    throw th;
                }
            } catch (Throwable th2) {
                inputStreamOpen.close();
                throw th2;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean b(String str) {
        if (str == null) {
            return false;
        }
        SOSecureFS sOSecureFS = f4187a;
        return sOSecureFS != null ? sOSecureFS.fileExists(str) : new File(str).exists();
    }

    public static boolean b(String str, String str2) {
        if (!b(str)) {
            return false;
        }
        boolean zB = b(str2);
        String strD = defpackage.h.D(str2, "xxx");
        if (b(strD)) {
            return false;
        }
        if (zB && !a(str2, strD)) {
            return false;
        }
        if (a(str, str2, true)) {
            e(strD);
            return true;
        }
        if (zB) {
            a(strD, str2);
        }
        return false;
    }
}
