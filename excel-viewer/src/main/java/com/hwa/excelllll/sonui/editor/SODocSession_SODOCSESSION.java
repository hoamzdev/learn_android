package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOLib;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOPageListener;
import com.artifex.solib.SORender;
import com.artifex.solib.k;
import com.artifex.solib.l;
import com.artifex.solib.n;
import com.artifex.solib.o;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class SODocSession {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private SODoc f4750a;
    private final k f;
    private final Activity g;
    private SORender n;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private String f4751b = null;
    private boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private SOFileState h = null;
    private int i = 0;
    private boolean j = false;
    private SODocSessionLoadListener k = null;
    private SODocSessionLoadListener l = null;
    private SODocSessionLoadListenerCustom m = null;

    public interface SODocSessionLoadListener {
        void onCancel();

        void onDocComplete();

        void onError(int i, int i2);

        void onLayoutCompleted();

        void onPageLoad(int i);

        void onSelectionChanged(int i, int i2);
    }

    public interface SODocSessionLoadListenerCustom extends SODocSessionLoadListener {
        void onSessionComplete(boolean z);

        void onSessionReject();
    }

    public SODocSession(Activity activity, k kVar) {
        this.g = activity;
        this.f = kVar;
    }

    public void abort() {
        Utilities.dismissCurrentAlert();
        this.c = false;
        this.k = null;
        this.l = null;
        this.m = null;
        SODoc sODoc = this.f4750a;
        if (sODoc != null) {
            sODoc.abortLoad();
        }
    }

    public void createThumbnail(SOFileState sOFileState) {
        final String thumbnail = sOFileState.getThumbnail();
        if (thumbnail == null || thumbnail.isEmpty()) {
            thumbnail = SOFileDatabase.uniqueThumbFilePath();
        }
        sOFileState.setThumbnail(thumbnail);
        sOFileState.deleteThumbnailFile();
        final SOPage page = getDoc().getPage(0, new SOPageListener() { // from class: com.artifex.sonui.editor.SODocSession.2
            @Override // com.artifex.solib.SOPageListener
            public void update(RectF rectF) {
            }
        });
        PointF pointFZoomToFitRect = page.zoomToFitRect((int) this.g.getResources().getDimension(R.dimen.sodk_editor_thumbnail_size), 1);
        double dMax = Math.max(pointFZoomToFitRect.x, pointFZoomToFitRect.y);
        Point pointSizeAtZoom = page.sizeAtZoom(dMax);
        final SOBitmap sOBitmapA = k.a(sOFileState.getInternalPath(), pointSizeAtZoom.x, pointSizeAtZoom.y);
        this.n = page.a(dMax, new PointF(0.0f, 0.0f), sOBitmapA, new o() { // from class: com.artifex.sonui.editor.SODocSession.3
            @Override // com.artifex.solib.o
            public void a(int i) {
                Bitmap bitmapA = sOBitmapA.a();
                n nVar = new n(thumbnail);
                try {
                    bitmapA.compress(Bitmap.CompressFormat.PNG, 80, nVar);
                    nVar.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SODocSession.this.n.destroy();
                page.m();
            }
        });
    }

    public void destroy() {
        Utilities.dismissCurrentAlert();
        this.c = false;
        this.k = null;
        this.l = null;
        this.m = null;
        SODoc sODoc = this.f4750a;
        if (sODoc != null) {
            sODoc.k();
            this.f4750a = null;
        }
    }

    public void endSession(boolean z) {
        SODocSessionLoadListenerCustom sODocSessionLoadListenerCustom = this.m;
        if (sODocSessionLoadListenerCustom != null) {
            sODocSessionLoadListenerCustom.onSessionComplete(z);
            this.m = null;
        }
        abort();
    }

    public SODoc getDoc() {
        return this.f4750a;
    }

    public SOFileState getFileState() {
        return this.h;
    }

    public String getUserPath() {
        return this.f4751b;
    }

    public boolean isCancelled() {
        return this.d;
    }

    public boolean isOpen() {
        return this.c;
    }

    public void open(String str) {
        this.f4751b = str;
        this.i = 0;
        this.j = false;
        if (this.f instanceof SOLib) {
            ((SOLib) this.f).setTrackChangesEnabled(ConfigOptions.a().w());
        }
        this.c = true;
        this.f4750a = this.f.a(str, new l() { // from class: com.artifex.sonui.editor.SODocSession.1
            @Override // com.artifex.solib.l
            public void a() {
                if (!SODocSession.this.c || SODocSession.this.d || SODocSession.this.e) {
                    return;
                }
                SODocSession.this.j = true;
                if (SODocSession.this.k != null && SODocSession.this.c) {
                    SODocSession.this.k.onDocComplete();
                }
                if (SODocSession.this.l != null && SODocSession.this.c) {
                    SODocSession.this.l.onDocComplete();
                }
                if (SODocSession.this.m == null || !SODocSession.this.c) {
                    return;
                }
                SODocSession.this.m.onDocComplete();
            }

            @Override // com.artifex.solib.l
            public void b() {
                if (SODocSession.this.c) {
                    if (SODocSession.this.k != null && SODocSession.this.c) {
                        SODocSession.this.k.onLayoutCompleted();
                    }
                    if (SODocSession.this.l != null && SODocSession.this.c) {
                        SODocSession.this.l.onLayoutCompleted();
                    }
                    if (SODocSession.this.m == null || !SODocSession.this.c) {
                        return;
                    }
                    SODocSession.this.m.onLayoutCompleted();
                }
            }

            @Override // com.artifex.solib.l
            public void b(int i, int i2) {
                if (SODocSession.this.c) {
                    if (SODocSession.this.k != null && SODocSession.this.c) {
                        SODocSession.this.k.onSelectionChanged(i, i2);
                    }
                    if (SODocSession.this.l != null && SODocSession.this.c) {
                        SODocSession.this.l.onSelectionChanged(i, i2);
                    }
                    if (SODocSession.this.m == null || !SODocSession.this.c) {
                        return;
                    }
                    SODocSession.this.m.onSelectionChanged(i, i2);
                }
            }

            @Override // com.artifex.solib.l
            public void a(int i) {
                if (!SODocSession.this.c || SODocSession.this.d) {
                    return;
                }
                SODocSession sODocSession = SODocSession.this;
                sODocSession.i = Math.max(i, sODocSession.i);
                if (SODocSession.this.k != null && SODocSession.this.c) {
                    SODocSession.this.k.onPageLoad(i);
                }
                if (SODocSession.this.l != null && SODocSession.this.c) {
                    SODocSession.this.l.onPageLoad(i);
                }
                if (SODocSession.this.m == null || !SODocSession.this.c) {
                    return;
                }
                SODocSession.this.m.onPageLoad(i);
            }

            @Override // com.artifex.solib.l
            public void a(int i, int i2) {
                SODocSession sODocSession = SODocSession.this;
                if (i == 4096) {
                    Utilities.passwordDialog(sODocSession.g, new Utilities.passwordDialogListener() { // from class: com.artifex.sonui.editor.SODocSession.1.1
                        @Override // com.artifex.sonui.editor.Utilities.passwordDialogListener
                        public void onCancel() {
                            SODocSession.this.f4750a.abortLoad();
                            if (SODocSession.this.k != null && SODocSession.this.c) {
                                SODocSession.this.k.onCancel();
                            }
                            if (SODocSession.this.l != null && SODocSession.this.c) {
                                SODocSession.this.l.onCancel();
                            }
                            if (SODocSession.this.m != null && SODocSession.this.c) {
                                SODocSession.this.m.onCancel();
                            }
                            SODocSession.this.d = true;
                        }

                        @Override // com.artifex.sonui.editor.Utilities.passwordDialogListener
                        public void onOK(String str2) {
                            SODocSession.this.f4750a.providePassword(str2);
                        }
                    });
                    return;
                }
                sODocSession.e = true;
                if (SODocSession.this.k != null && SODocSession.this.c) {
                    SODocSession.this.k.onError(i, i2);
                }
                if (SODocSession.this.l != null && SODocSession.this.c) {
                    SODocSession.this.l.onError(i, i2);
                }
                if (SODocSession.this.m != null && SODocSession.this.c) {
                    SODocSession.this.m.onError(i, i2);
                }
                SODocSession.this.c = false;
            }
        }, this.g);
    }

    public void setFileState(SOFileState sOFileState) {
        this.h = sOFileState;
    }

    public void setSODocSessionLoadListener(SODocSessionLoadListener sODocSessionLoadListener) {
        this.k = sODocSessionLoadListener;
        if (sODocSessionLoadListener != null) {
            int i = this.i;
            if (i > 0) {
                sODocSessionLoadListener.onPageLoad(i);
            }
            if (this.j) {
                this.k.onDocComplete();
            }
        }
        SODocSessionLoadListenerCustom sessionLoadListener = Utilities.getSessionLoadListener();
        this.m = sessionLoadListener;
        if (sessionLoadListener != null) {
            int i2 = this.i;
            if (i2 > 0) {
                sessionLoadListener.onPageLoad(i2);
            }
            if (this.j) {
                this.m.onDocComplete();
            }
        }
    }

    public void setSODocSessionLoadListener2(SODocSessionLoadListener sODocSessionLoadListener) {
        this.l = sODocSessionLoadListener;
    }
}
