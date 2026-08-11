package com.hwa.excelllll.solib;

import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class n extends OutputStream {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final boolean f4245a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SOSecureFS f4246b;
    private Object c;

    public n(String str) {
        SOSecureFS sOSecureFSA = k.a();
        this.f4246b = sOSecureFSA;
        if (sOSecureFSA != null && sOSecureFSA.isSecurePath(str)) {
            this.c = this.f4246b.getFileHandleForWriting(str);
            return;
        }
        try {
            this.c = new FileOutputStream(str);
        } catch (FileNotFoundException | SecurityException e) {
            this.c = null;
            e.printStackTrace();
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Object obj = this.c;
        if (obj != null && (obj instanceof FileOutputStream)) {
            try {
                ((FileOutputStream) obj).close();
            } catch (IOException unused) {
                Log.e("SOOutputStream", "Error: closing FileOutputStream");
            }
        } else {
            SOSecureFS sOSecureFS = this.f4246b;
            if (sOSecureFS == null || obj == null) {
                return;
            }
            sOSecureFS.closeFile(obj);
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() {
        String str;
        Object obj = this.c;
        if (obj instanceof FileOutputStream) {
            try {
                ((FileOutputStream) obj).flush();
                return;
            } catch (IOException unused) {
                str = "Error: flushing FileOutputStream";
            }
        } else if (this.f4246b.syncFile(obj)) {
            return;
        } else {
            str = "Error: flushing SecureFS";
        }
        Log.e("SOOutputStream", str);
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        String str;
        Object obj = this.c;
        if (obj instanceof FileOutputStream) {
            try {
                ((FileOutputStream) obj).write(i);
                return;
            } catch (IOException unused) {
                str = "Error: writing byte to FileOutputStream";
            }
        } else if (this.f4246b.writeToFile(obj, new byte[]{(byte) (i & 255)}) != -1) {
            return;
        } else {
            str = "Error: writing byte to SecureFS";
        }
        Log.e("SOOutputStream", str);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        String str;
        Object obj = this.c;
        if (obj instanceof FileOutputStream) {
            try {
                ((FileOutputStream) obj).write(bArr);
                return;
            } catch (IOException unused) {
                str = "Error: writing byte array to FileOutputStream";
            }
        } else if (this.f4246b.writeToFile(obj, bArr) != -1) {
            return;
        } else {
            str = "Error: writing byte array to SecureFS";
        }
        Log.e("SOOutputStream", str);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        String str;
        Object obj = this.c;
        if (obj instanceof FileOutputStream) {
            try {
                ((FileOutputStream) obj).write(bArr, i, i2);
                return;
            } catch (IOException unused) {
                str = "Error: writing offset byte array to FileOutputStream";
            }
        } else {
            if (this.f4246b.writeToFile(this.c, Arrays.copyOfRange(bArr, i, i2 + i)) != -1) {
                return;
            } else {
                str = "Error: writing offset byte array to SecureFS";
            }
        }
        Log.e("SOOutputStream", str);
    }
}
