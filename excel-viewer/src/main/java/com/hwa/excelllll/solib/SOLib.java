package com.hwa.excelllll.solib;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import defpackage.a7;
import defpackage.c8;
import java.util.Locale;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class SOLib extends k {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static SOLib f4173b;
    private static SOSecureFS f;
    private Activity c;
    private Thread d;
    private Thread e;
    private long internal;

    static {
        Log.w("SOLib", "loading shared library");
        System.loadLibrary("so");
        f = null;
    }

    private SOLib(Activity activity) throws ClassNotFoundException {
        String str;
        SOSecureFS sOSecureFS = f;
        try {
            if (sOSecureFS == null) {
                throw new ClassNotFoundException();
            }
            try {
                initSecureFS(sOSecureFS);
            } catch (ClassNotFoundException | Exception unused) {
                Log.i("SOLib", "SecureFS implementation unavailable");
            }
            preInitLib();
            g();
            this.c = activity;
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();
            if (country != null && !country.isEmpty()) {
                language = defpackage.h.j(language, "-", country);
            }
            long jInitLib = initLib(language);
            this.internal = jInitLib;
            if (jInitLib == 0) {
                c8.h("SOLib initialisation failed");
                throw null;
            }
            String strD = defpackage.h.D(a.c(activity), "/tmp/");
            if (a.b(strD)) {
                a.d(strD);
            }
            a.c(strD);
            if (setTempPath(strD) != 0) {
                a7.z("SOLib error in setTempPath");
                throw null;
            }
            if (installFonts("/system/fonts/") == 0) {
                return;
            }
            a7.z("SOLib error in installFonts");
            throw null;
        } catch (ExceptionInInitializerError unused2) {
            str = "SOLib() experienced unexpected exception [ExceptionInInitializerError]";
            Log.e("SOLib", str);
        } catch (LinkageError unused3) {
            str = "SOLib() experienced unexpected exception [LinkageError]";
            Log.e("SOLib", str);
        } catch (SecurityException unused4) {
            str = "SOLib() experienced unexpected exception [SecurityException]";
            Log.e("SOLib", str);
        }
    }

    public static SOLib a(Activity activity) {
        synchronized (SOLib.class) {
        }
        if (f4173b == null) {
            Log.w("SOLib", "creating new SOLib");
            f4173b = new SOLib(activity);
            if (k.f4240a == null) {
                k.a(activity);
            }
        }
        return f4173b;
    }

    public static j e() {
        return k.f4240a;
    }

    public static SOSecureFS f() {
        return f;
    }

    private native void finLib();

    private void g() {
        Thread thread = new Thread() { // from class: com.artifex.solib.SOLib.1

            /* JADX INFO: renamed from: com.artifex.solib.SOLib$1$1, reason: invalid class name and collision with other inner class name */
            class RunnableC00261 implements Runnable {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ int f4174a;

                /* JADX INFO: renamed from: b, reason: collision with root package name */
                final /* synthetic */ boolean f4175b;

                public RunnableC00261(int i, boolean z) {
                    this.f4174a = i;
                    this.f4175b = z;
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (AnonymousClass1.a(AnonymousClass1.this) != null) {
                        AnonymousClass1.a(AnonymousClass1.this).c(this.f4174a);
                    }
                    AnonymousClass1.this.a.a(this.f4174a);
                    if (this.f4175b) {
                        AnonymousClass1.this.a.a();
                    }
                }
            }

            /* JADX INFO: renamed from: com.artifex.solib.SOLib$1$2, reason: invalid class name */
            class AnonymousClass2 implements Runnable {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ int f4176a;

                /* JADX INFO: renamed from: b, reason: collision with root package name */
                final /* synthetic */ int f4177b;

                public AnonymousClass2(int i, int i2) {
                    this.f4176a = i;
                    this.f4177b = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass1.this.a.a(this.f4176a, this.f4177b);
                }
            }

            /* JADX INFO: renamed from: com.artifex.solib.SOLib$1$3, reason: invalid class name */
            class AnonymousClass3 implements Runnable {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ int f4178a;

                /* JADX INFO: renamed from: b, reason: collision with root package name */
                final /* synthetic */ int f4179b;

                public AnonymousClass3(int i, int i2) {
                    this.f4178a = i;
                    this.f4179b = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass1.a(AnonymousClass1.this).d(this.f4178a);
                    AnonymousClass1.a(AnonymousClass1.this).e(this.f4179b);
                    AnonymousClass1.this.a.b(this.f4178a, this.f4179b);
                }
            }

            /* JADX INFO: renamed from: com.artifex.solib.SOLib$1$4, reason: invalid class name */
            class AnonymousClass4 implements Runnable {
                public AnonymousClass4() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass1.this.a.b();
                }
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SOLib.this.logStdout();
            }
        };
        this.d = thread;
        thread.start();
        Thread thread2 = new Thread() { // from class: com.artifex.solib.SOLib.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SOLib.this.logStderr();
            }
        };
        this.e = thread2;
        thread2.start();
    }

    private void h() throws InterruptedException {
        stopLoggingOutputInternal();
        try {
            Thread thread = this.d;
            if (thread != null) {
                thread.join();
            }
            Thread thread2 = this.e;
            if (thread2 != null) {
                thread2.join();
            }
        } catch (InterruptedException | Exception unused) {
        }
    }

    private native long initLib(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void logStderr();

    /* JADX INFO: Access modifiers changed from: private */
    public native void logStdout();

    private native void preInitLib();

    private native int setTempPath(String str);

    private native void stopLoggingOutputInternal();

    public native void finSecureFS();

    public void finalize() throws Throwable {
        try {
            finSecureFS();
            finLib();
            h();
        } finally {
            super.finalize();
        }
    }

    public native String[] getFormulae(String str);

    public native String[] getFormulaeCategories();

    public native String[] getVersionInfo();

    public native void initSecureFS(SOSecureFS sOSecureFS);

    public native int installFonts(String str);

    public native boolean isAnimationEnabled();

    public native boolean isDocTypeDoc(String str);

    public native boolean isDocTypeExcel(String str);

    public native boolean isDocTypeOther(String str);

    public native boolean isDocTypePowerPoint(String str);

    public native boolean isTrackChangesEnabled();

    public native SODoc openDocumentInternal(String str, SODocLoadListenerInternal sODocLoadListenerInternal);

    public native void setTrackChangesEnabled(boolean z);

    public static final void a(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override // com.artifex.solib.k
    public SOBitmap a(int i, int i2) {
        return new SOBitmap(i, i2);
    }

    @Override // com.artifex.solib.k
    public SODoc a(String str, final l lVar, Context context) {
        SODocLoadListenerInternal sODocLoadListenerInternal = new SODocLoadListenerInternal(this) { // from class: com.artifex.solib.SOLib.3
            private SODoc c = null;

            @Override // com.artifex.solib.SODocLoadListenerInternal
            public void error(final int i, final int i2) {
                SOLib.a(new Runnable() { // from class: com.artifex.solib.SOLib.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        lVar.a(i, i2);
                    }
                });
            }

            @Override // com.artifex.solib.SODocLoadListenerInternal
            public void onLayoutCompleted() {
                SOLib.a(new Runnable() { // from class: com.artifex.solib.SOLib.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        lVar.b();
                    }
                });
            }

            @Override // com.artifex.solib.SODocLoadListenerInternal
            public void onSelectionChanged(final int i, final int i2) {
                SOLib.a(new Runnable() { // from class: com.artifex.solib.SOLib.3.3
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass3.this.c.d(i);
                        AnonymousClass3.this.c.e(i2);
                        lVar.b(i, i2);
                    }
                });
            }

            @Override // com.artifex.solib.SODocLoadListenerInternal
            public void progress(final int i, final boolean z) {
                SOLib.a(new Runnable() { // from class: com.artifex.solib.SOLib.3.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AnonymousClass3.this.c != null) {
                            AnonymousClass3.this.c.c(i);
                        }
                        lVar.a(i);
                        if (z) {
                            lVar.a();
                        }
                    }
                });
            }

            @Override // com.artifex.solib.SODocLoadListenerInternal
            public void a(SODoc sODoc) {
                this.c = sODoc;
            }
        };
        SODoc sODocOpenDocumentInternal = openDocumentInternal(str, sODocLoadListenerInternal);
        sODocLoadListenerInternal.a(sODocOpenDocumentInternal);
        return sODocOpenDocumentInternal;
    }
}
