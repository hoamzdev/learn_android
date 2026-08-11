package com.hwa.excelllll;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hwa.excelllll.excelsolib.ArtifexEngine;
import com.hwa.excelllll.excelsolib.ArtifexFileUtils;
import com.hwa.excelllll.excelsolib.ClipboardHandler;
import com.hwa.excelllll.excelsolib.DocLoadListener;
import com.hwa.excelllll.excelsolib.SOBitmap;
import com.hwa.excelllll.excelsolib.SODoc;
import com.hwa.excelllll.excelsolib.SODocLoadListenerInternal;
import com.hwa.excelllll.excelsolib.SOSecureFS;

import java.util.Locale;

public class SOLib extends ArtifexEngine {
    private static SOLib instance;
    private static SOSecureFS secureFS;
    private Activity activity;
    private Thread stdoutLogThread;
    private Thread stderrLogThread;
    private long internalHandle;

    static {
        Log.w("SOLib", "loading shared library");
        System.loadLibrary("so");
        secureFS = null;
    }

    private SOLib(Activity activity) {
        String errorMessage;
        SOSecureFS currentSecureFS = secureFS;
        try {
            if (currentSecureFS != null) {
                try {
                    initSecureFS(currentSecureFS);
                } catch (Exception exception) {
                    Log.i("SOLib", "SecureFS implementation unavailable", exception);
                }
            } else {
                Log.i("SOLib", "SecureFS implementation unavailable");
            }
            preInitLib();
            g();

            this.activity = activity;
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();
            if (!country.isEmpty()) {
                language = language + "-" + country;
            }
            long initResult = initLib(language);
            this.internalHandle = initResult;
            if (initResult == 0) {
                Log.e("ExcelCore", "SOLib initialisation failed");
                throw null;
            }
            String tempDirPath = new java.io.File(ArtifexFileUtils.getTempPath(activity), "tmp").getPath();
            if (ArtifexFileUtils.fileExists(tempDirPath)) {
                ArtifexFileUtils.removeDirectory(tempDirPath);
            }
            ArtifexFileUtils.createDirectory(tempDirPath);
            if (setTempPath(tempDirPath) != 0) {
                Log.e("ExcelCore", "SOLib error in setTempPath");
                throw null;
            }
            if (installFonts("/system/fonts/") == 0) {
                return;
            }
            Log.e("ExcelCore", "SOLib error in installFonts");
            throw null;
        } catch (ExceptionInInitializerError unused2) {
            errorMessage = "SOLib() experienced unexpected exception [ExceptionInInitializerError]";
            Log.e("SOLib", errorMessage);
        } catch (LinkageError unused3) {
            errorMessage = "SOLib() experienced unexpected exception [LinkageError]";
            Log.e("SOLib", errorMessage);
        } catch (SecurityException unused4) {
            errorMessage = "SOLib() experienced unexpected exception [SecurityException]";
            Log.e("SOLib", errorMessage);
        }
    }

    public static SOLib a(Activity activity) {
        synchronized (SOLib.class) {
        }
        if (instance == null) {
            Log.w("SOLib", "creating new SOLib");
            instance = new SOLib(activity);
            if (ArtifexEngine.clipboardHandler == null) {
                ArtifexEngine.initializeClipboard(activity);
            }
        }
        return instance;
    }

    public static ClipboardHandler e() {
        return ArtifexEngine.clipboardHandler;
    }

    public static SOSecureFS f() {
        return secureFS;
    }

    private native void finLib();

    private void g() {
        Thread threadStdout = new Thread() { // from class: com.hwa.excel.excelsolib.SOLib.1
            class RunnableC00261 implements Runnable {
                final /* synthetic */ int progressPercent;
                final /* synthetic */ boolean isComplete;

                public RunnableC00261(int progressPercent, boolean isComplete) {
                    this.progressPercent = progressPercent;
                    this.isComplete = isComplete;
                }

                @Override // java.lang.Runnable
                public void run() {
                    Log.d("SOLib", "Load progress: " + this.progressPercent);
                    if (this.isComplete) {
                        Log.d("SOLib", "Load complete");
                    }
                }
            }
            class AnonymousClass2 implements Runnable {
                final /* synthetic */ int viewWidth;
                final /* synthetic */ int viewHeight;

                public AnonymousClass2(int viewWidth, int viewHeight) {
                    this.viewWidth = viewWidth;
                    this.viewHeight = viewHeight;
                }

                @Override // java.lang.Runnable
                public void run() {
                    Log.d("SOLib", "View size: " + this.viewWidth + "x" + this.viewHeight);
                }
            }
            class AnonymousClass3 implements Runnable {
                final /* synthetic */ int pageIndex;
                final /* synthetic */ int totalPages;

                public AnonymousClass3(int pageIndex, int totalPages) {
                    this.pageIndex = pageIndex;
                    this.totalPages = totalPages;
                }

                @Override // java.lang.Runnable
                public void run() {
                    Log.d("SOLib", "Page progress: " + this.pageIndex + "/" + this.totalPages);
                }
            }
            class AnonymousClass4 implements Runnable {
                public AnonymousClass4() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    Log.d("SOLib", "Layout completed");
                }
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SOLib.this.logStdout();
            }
        };
        this.stdoutLogThread = threadStdout;
        threadStdout.start();
        Thread threadStderr = new Thread() { // from class: com.hwa.excel.excelsolib.SOLib.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SOLib.this.logStderr();
            }
        };
        this.stderrLogThread = threadStderr;
        threadStderr.start();
    }

    private void h() throws InterruptedException {
        stopLoggingOutputInternal();
        try {
            Thread threadStdout = this.stdoutLogThread;
            if (threadStdout != null) {
                threadStdout.join();
            }
            Thread threadStderr = this.stderrLogThread;
            if (threadStderr != null) {
                threadStderr.join();
            }
        } catch (Exception exception) {
            Thread.currentThread().interrupt();
        }
    }

    private native long initLib(String language);
    public native void logStderr();
    public native void logStdout();

    private native void preInitLib();

    private native int setTempPath(String tempPath);

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

    public native String[] getFormulae(String category);

    public native String[] getFormulaeCategories();

    public native String[] getVersionInfo();

    public native void initSecureFS(SOSecureFS secureFS);

    public native int installFonts(String fontPath);

    public native boolean isAnimationEnabled();

    public native boolean isDocTypeDoc(String path);

    public native boolean isDocTypeExcel(String path);

    public native boolean isDocTypeOther(String path);

    public native boolean isDocTypePowerPoint(String path);

    public native boolean isTrackChangesEnabled();

    public native SODoc openDocumentInternal(String path, SODocLoadListenerInternal listener);

    public native void setTrackChangesEnabled(boolean enabled);

    public static final void a(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override // com.hwa.excelllll.excelsolib.ArtifexEngine
    public SOBitmap createBitmap(int width, int height) {
        return new SOBitmap(width, height);
    }

    @Override // com.hwa.excelllll.excelsolib.ArtifexEngine
    public SODoc openDocument(String path, final DocLoadListener listener, Context context) {
        SODocLoadListenerInternal sODocLoadListenerInternal = new SODocLoadListenerInternal() { // from class: com.hwa.excel.excelsolib.SOLib.3
            private SODoc loadedDoc = null;

            @Override // com.hwa.excelllll.excelsolib.SODocLoadListenerInternal
            public void error(final int errorId, final int errorDetail) {
                SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SOLib.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onLoadError(errorId, errorDetail);
                    }
                });
            }

            @Override // com.hwa.excelllll.excelsolib.SODocLoadListenerInternal
            public void onLayoutCompleted() {
                SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SOLib.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onLoadCancelled();
                    }
                });
            }

            @Override // com.hwa.excelllll.excelsolib.SODocLoadListenerInternal
            public void onSelectionChanged(final int startSelection, final int endSelection) {
                SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SOLib.3.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (loadedDoc != null) {
                            loadedDoc.setTrackedPageCount(startSelection);
                            loadedDoc.setActivePageIndex(endSelection);
                        }
                        listener.onSelectionChanged(startSelection, endSelection);
                    }
                });
            }

            @Override // com.hwa.excelllll.excelsolib.SODocLoadListenerInternal
            public void progress(final int progressPercent, final boolean isComplete) {
                SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SOLib.3.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (loadedDoc != null) {
                            loadedDoc.setDocType(progressPercent);
                        }
                        listener.onLoadProgress(progressPercent);
                        if (isComplete) {
                            listener.onLoadComplete();
                        }
                    }
                });
            }

            @Override // com.hwa.excelllll.excelsolib.SODocLoadListenerInternal
            public void onDocumentLoaded(SODoc doc) {
                this.loadedDoc = doc;
            }
        };
        SODoc openedDoc = openDocumentInternal(path, sODocLoadListenerInternal);
        sODocLoadListenerInternal.onDocumentLoaded(openedDoc);
        return openedDoc;
    }
}
