package com.hwa.excelllll.solib;

import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class m extends InputStream {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final boolean f4243a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SOSecureFS f4244b;
    private Object c;

    public m(String str) {
        SOSecureFS sOSecureFSA = k.a();
        this.f4244b = sOSecureFSA;
        if (sOSecureFSA != null && sOSecureFSA.isSecurePath(str)) {
            this.c = this.f4244b.getFileHandleForReading(str);
            return;
        }
        try {
            this.c = new FileInputStream(str);
        } catch (FileNotFoundException | SecurityException e) {
            this.c = null;
            e.printStackTrace();
        }
    }

    @Override // java.io.InputStream
    public int available() {
        String str;
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            try {
                return ((FileInputStream) obj).available();
            } catch (IOException unused) {
                str = "Available: Error in FileInputStream";
            }
        } else {
            int fileLength = (int) this.f4244b.getFileLength(obj);
            if (fileLength != -1) {
                return fileLength;
            }
            str = "Available: Error in SecureFS";
        }
        Log.e("SOInputStream", str);
        return 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        String str;
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            try {
                ((FileInputStream) obj).close();
                return;
            } catch (IOException unused) {
                str = "Error: closing FileInputStream";
            }
        } else if (this.f4244b.closeFile(obj)) {
            return;
        } else {
            str = "Error: closing SecureFS";
        }
        Log.e("SOInputStream", str);
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            ((FileInputStream) obj).mark(i);
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            return ((FileInputStream) obj).markSupported();
        }
        return false;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        String str;
        if (bArr == null) {
            return -1;
        }
        if (i2 == 0) {
            return 0;
        }
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            try {
                return ((FileInputStream) obj).read(bArr, i, i2);
            } catch (IOException unused) {
                str = "Error: reading offset byte array from FileInputStream";
            }
        } else {
            byte[] bArr2 = new byte[i2];
            int fromFile = this.f4244b.readFromFile(obj, bArr2);
            if (fromFile != -1 && fromFile != 0) {
                System.arraycopy(bArr2, 0, bArr, i, fromFile);
                return fromFile;
            }
            str = "Error: reading offset byte array from SecureFS";
        }
        Log.e("SOInputStream", str);
        return -1;
    }

    @Override // java.io.InputStream
    public void reset() {
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            try {
                ((FileInputStream) obj).reset();
            } catch (IOException unused) {
                Log.e("SOInputStream", "Error: resetting FileInputStream");
            }
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) {
        return 0L;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        String str;
        if (bArr == null) {
            return -1;
        }
        if (bArr.length == 0) {
            return 0;
        }
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            try {
                return ((FileInputStream) obj).read(bArr);
            } catch (IOException unused) {
                str = "Error: reading byte array from FileInputStream";
            } catch (NullPointerException unused2) {
                str = "Error: reading byte array from FileInputStream - NullPointerException";
            }
        } else {
            int fromFile = this.f4244b.readFromFile(obj, bArr);
            if (fromFile != -1 && fromFile != 0) {
                return fromFile;
            }
            str = "Error: reading byte array from SecureFS";
        }
        Log.e("SOInputStream", str);
        return -1;
    }

    @Override // java.io.InputStream
    public int read() {
        String str;
        Object obj = this.c;
        if (obj instanceof FileInputStream) {
            try {
                return ((FileInputStream) obj).read();
            } catch (IOException unused) {
                str = "Error: reading byte from FileInputStream";
            }
        } else {
            byte[] bArr = new byte[1];
            int fromFile = this.f4244b.readFromFile(obj, bArr);
            if (fromFile != -1 && fromFile != 0) {
                return bArr[0];
            }
            str = "Error: reading byte from SecureFS";
        }
        Log.e("SOInputStream", str);
        return -1;
    }
}
