package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;
import com.artifex.solib.SORender;
import com.artifex.solib.o;
import defpackage.a7;
import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class AnimationLayerView extends View implements AnimatableView, SlideShowConductorView {
    private static Paint E;
    private final Paint A;
    private boolean B;
    private Bitmap C;
    private Point D;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Path f4506a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SODoc f4507b;
    private LayerBitmapManager c;
    private int d;
    private int e;
    private SOBitmap f;
    private SOBitmap g;
    private SOBitmap h;
    private SORender i;
    private boolean j;
    private Rect k;
    private final PointF l;
    private SOBitmap m;
    public final int mLayerId;
    protected SOPage mPage;
    protected PointF mPosition;
    protected Point mSize;
    protected double mZoomScale;
    private final Rect n;
    private double o;
    private SOBitmap p;
    private final Rect q;
    private double r;
    private SOBitmap s;
    private final Rect t;
    private double u;
    private int v;
    private final Paint w;
    private ColorMatrix x;
    private final Rect y;
    private final Rect z;

    public AnimationLayerView(Context context, SODoc sODoc, SOPage sOPage, int i, PointF pointF, RectF rectF, LayerBitmapManager layerBitmapManager) {
        super(context);
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = false;
        this.mZoomScale = 1.0d;
        this.m = null;
        this.n = new Rect();
        this.p = null;
        this.q = new Rect();
        this.s = null;
        this.t = new Rect();
        this.v = 0;
        this.x = new ColorMatrix();
        this.y = new Rect();
        this.z = new Rect();
        this.B = true;
        this.C = null;
        this.D = null;
        this.f4506a = null;
        if (sODoc == null || sOPage == null || pointF == null || rectF == null || layerBitmapManager == null) {
            a7.z("Constructor parameter(s) unexpectedly null");
            throw null;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        this.f4507b = sODoc;
        this.mPage = sOPage;
        this.mLayerId = i;
        this.c = layerBitmapManager;
        this.w = new Paint();
        Paint paint = new Paint();
        this.A = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.v);
        if (E == null) {
            Paint paint2 = new Paint();
            E = paint2;
            paint2.setAntiAlias(true);
            E.setFilterBitmap(true);
            E.setDither(true);
        }
        this.k = new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        this.l = pointF;
        Point point = new Point((int) rectF.width(), (int) rectF.height());
        this.mSize = point;
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        setLayoutParams(layoutParams);
        PointF pointF2 = new PointF(-pointF.x, -pointF.y);
        this.mPosition = pointF2;
        setX(pointF2.x);
        setY(this.mPosition.y);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2, final o oVar) {
        synchronized (this) {
            try {
                int i3 = this.e;
                int i4 = i3 * i;
                int i5 = i2 * i3;
                int i6 = i3 + i4;
                int i7 = i3 + i5;
                if (i6 > this.m.b().right) {
                    i6 = this.m.b().right;
                }
                int i8 = i6;
                if (i7 > this.m.b().bottom) {
                    i7 = this.m.b().bottom;
                }
                final SOBitmap sOBitmap = new SOBitmap(this.m, i4, i5, i8, i7);
                SOBitmap sOBitmap2 = this.c.get(this.e, SOBitmap.Type.RGB565);
                if (sOBitmap2 != null) {
                    this.g = new SOBitmap(sOBitmap2, 0, 0, sOBitmap.c(), sOBitmap.d());
                } else {
                    this.g = null;
                }
                SOBitmap sOBitmap3 = this.c.get(this.e, SOBitmap.Type.A8);
                if (sOBitmap3 != null) {
                    this.h = new SOBitmap(sOBitmap3, 0, 0, sOBitmap.c(), sOBitmap.d());
                } else {
                    this.h = null;
                }
                SOBitmap sOBitmap4 = this.g;
                if (sOBitmap4 == null || this.h == null) {
                    if (sOBitmap4 != null) {
                        this.c.addToCache(sOBitmap4);
                        this.g = null;
                    }
                    SOBitmap sOBitmap5 = this.h;
                    if (sOBitmap5 != null) {
                        this.c.addToCache(sOBitmap5);
                        this.h = null;
                    }
                    oVar.a(7);
                } else {
                    double d = this.l.x;
                    double d2 = this.mZoomScale;
                    Double.isNaN(d);
                    double d3 = i4;
                    Double.isNaN(d3);
                    Double.isNaN(d);
                    Double.isNaN(d3);
                    Double.isNaN(d);
                    Double.isNaN(d3);
                    float f = (float) ((d * d2) - d3);
                    double d4 = this.l.y;
                    double d5 = this.mZoomScale;
                    Double.isNaN(d4);
                    double d6 = i5;
                    Double.isNaN(d6);
                    Double.isNaN(d4);
                    Double.isNaN(d6);
                    Double.isNaN(d4);
                    Double.isNaN(d6);
                    PointF pointF = new PointF(f, (float) ((d4 * d5) - d6));
                    this.i = this.mPage.a(this.mLayerId, this.mZoomScale, pointF.x, pointF.y, this.g, this.h, new o() { // from class: com.artifex.sonui.editor.AnimationLayerView.2
                        @Override // com.artifex.solib.o
                        public void a(int i9) {
                            if (AnimationLayerView.this.j) {
                                return;
                            }
                            AnimationLayerView.this.stopRender();
                            AnimationLayerView animationLayerView = AnimationLayerView.this;
                            if (i9 != 0) {
                                System.out.printf("render error %d for page %s\n", Integer.valueOf(i9), animationLayerView.mPage.getPageTitle());
                            } else if (animationLayerView.g != null && AnimationLayerView.this.h != null) {
                                AnimationLayerView.this.g.a(sOBitmap, AnimationLayerView.this.h);
                            }
                            AnimationLayerView.this.c.addToCache(AnimationLayerView.this.g);
                            AnimationLayerView.this.c.addToCache(AnimationLayerView.this.h);
                            AnimationLayerView.this.g = null;
                            AnimationLayerView.this.h = null;
                            oVar.a(i9);
                        }
                    }, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void b() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        Point point = this.mSize;
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        setLayoutParams(layoutParams);
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void applyColorAdjustmentMatrix(ColorMatrix colorMatrix) {
        ColorMatrix colorMatrix2 = new ColorMatrix(this.x);
        colorMatrix2.postConcat(colorMatrix);
        this.w.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
    }

    public void clearContent() {
        this.s = null;
        invalidate();
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void concatColorAdjustmentMatrix(ColorMatrix colorMatrix) {
        this.x.postConcat(colorMatrix);
        this.w.setColorFilter(new ColorMatrixColorFilter(this.x));
    }

    public void dispose(boolean z) {
        this.j = true;
        stopRender();
        SOBitmap sOBitmap = this.f;
        if (sOBitmap != null) {
            sOBitmap.e();
            this.f = null;
        }
        SOBitmap sOBitmap2 = this.g;
        if (sOBitmap2 != null) {
            if (z) {
                this.c.addToCache(sOBitmap2);
            } else {
                sOBitmap2.e();
            }
        }
        this.g = null;
        SOBitmap sOBitmap3 = this.h;
        if (sOBitmap3 != null) {
            if (z) {
                this.c.addToCache(sOBitmap3);
            } else {
                sOBitmap3.e();
            }
        }
        this.h = null;
        this.s = null;
        this.p = null;
        this.m = null;
        this.mPage = null;
        this.f4507b = null;
        this.c = null;
        Runtime.getRuntime().gc();
    }

    public void endRenderPass() {
        this.s = this.p;
        this.t.set(this.q);
        this.u = this.r;
        invalidate();
    }

    @Override // com.artifex.sonui.editor.AnimatableView
    public Path getClipPath() {
        return this.f4506a;
    }

    public SODoc getDoc() {
        return this.f4507b;
    }

    public SOPage getPage() {
        return this.mPage;
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public Point getSize() {
        return this.mSize;
    }

    public double getZoomScale() {
        return this.mZoomScale;
    }

    public boolean isFinished() {
        return this.j;
    }

    public boolean isValid() {
        return this.B;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Rect rect;
        if (!this.j && isShown()) {
            Bitmap bitmap = this.C;
            if (bitmap != null) {
                Rect rect2 = new Rect(0, 0, bitmap.getWidth(), this.C.getHeight());
                Point screenSize = Utilities.getScreenSize(getContext());
                Point point = this.D;
                if (point == null || screenSize == null || (point.x == screenSize.x && point.y == screenSize.y)) {
                    rect = this.t;
                } else {
                    rect = new Rect();
                    getLocalVisibleRect(rect);
                }
                canvas.drawBitmap(this.C, rect2, rect, this.w);
                return;
            }
            if (this.B) {
                this.A.setColor(this.v);
                Rect rect3 = new Rect();
                getLocalVisibleRect(rect3);
                canvas.drawRect(rect3, this.A);
                if (this.f4506a != null) {
                    canvas.save();
                }
                SOBitmap sOBitmap = this.s;
                if (sOBitmap == null || sOBitmap.a().isRecycled()) {
                    return;
                }
                this.y.set(sOBitmap.b());
                this.z.set(this.t);
                double d = this.u;
                double d2 = this.mZoomScale;
                if (d != d2) {
                    Rect rect4 = this.z;
                    double d3 = rect4.left;
                    Double.isNaN(d3);
                    rect4.left = (int) e6.a(d3, d3, d3, d2 / d);
                    Rect rect5 = this.z;
                    double d4 = rect5.top;
                    double d5 = this.mZoomScale / this.u;
                    Double.isNaN(d4);
                    rect5.top = (int) e6.a(d4, d4, d4, d5);
                    Rect rect6 = this.z;
                    double d6 = rect6.right;
                    double d7 = this.mZoomScale / this.u;
                    Double.isNaN(d6);
                    rect6.right = (int) e6.a(d6, d6, d6, d7);
                    Rect rect7 = this.z;
                    double d8 = rect7.bottom;
                    double d9 = this.mZoomScale / this.u;
                    Double.isNaN(d8);
                    rect7.bottom = (int) e6.a(d8, d8, d8, d9);
                }
                Path path = this.f4506a;
                if (path != null) {
                    canvas.clipPath(path);
                }
                canvas.drawBitmap(sOBitmap.a(), this.y, this.z, this.w);
                if (this.f4506a != null) {
                    canvas.restore();
                }
            }
        }
    }

    public void render(o oVar) {
        if (this.j) {
            return;
        }
        if (oVar == null) {
            a7.z("Render listener cannot be null");
            return;
        }
        Rect rect = new Rect();
        Point point = this.mSize;
        rect.set(0, 0, point.x, point.y);
        SOBitmap sOBitmap = this.f;
        if (sOBitmap != null) {
            a(sOBitmap, oVar, rect);
        }
    }

    public void resize(int i, int i2) {
        SOPage sOPage = this.mPage;
        if (sOPage == null) {
            return;
        }
        PointF pointFZoomToFitRect = sOPage.zoomToFitRect(i, i2);
        a(Math.min(pointFZoomToFitRect.x, pointFZoomToFitRect.y));
    }

    @Override // com.artifex.sonui.editor.AnimatableView
    public void setClipPath(Path path) {
        this.f4506a = path;
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setOpacity(float f) {
        setAlpha(f);
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setPosition(PointF pointF) {
        PointF pointF2 = this.mPosition;
        double d = pointF.x;
        double d2 = this.mZoomScale;
        Double.isNaN(d);
        float fA = (float) e6.a(d, d, d, d2);
        double d3 = pointF.y;
        double d4 = this.mZoomScale;
        Double.isNaN(d3);
        pointF2.set(fA, (float) e6.a(d3, d3, d3, d4));
        setX(this.mPosition.x);
        setY(this.mPosition.y);
    }

    @Override // android.view.View, com.artifex.sonui.editor.SlideShowConductorView
    public void setRotation(float f) {
        super.setRotation((float) Math.toDegrees(f));
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setScale(float f, float f2) {
        setScaleX(f);
        setScaleY(f2);
    }

    public void setValid(boolean z) {
        if (z == this.B) {
            return;
        }
        this.B = z;
        if (z) {
            Bitmap bitmap = this.C;
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.C = null;
        } else {
            SOBitmap sOBitmap = this.s;
            if (sOBitmap != null && !sOBitmap.a().isRecycled()) {
                this.D = Utilities.getScreenSize(getContext());
                int iC = this.s.c() / 2;
                int iD = this.s.d() / 2;
                Rect rect = new Rect(0, 0, iC, iD);
                this.C = Bitmap.createBitmap(iC, iD, Bitmap.Config.ARGB_8888);
                new Canvas(this.C).drawBitmap(this.s.a(), this.s.b(), rect, E);
            }
            this.s = null;
            this.p = null;
            this.m = null;
        }
        invalidate();
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setVisibility(boolean z) {
        setVisibility(z ? 0 : 4);
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setZPosition(int i) {
        setZ(i);
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setZoomScale(double d) {
        a(d);
    }

    public void stopRender() {
        SORender sORender = this.i;
        if (sORender != null) {
            sORender.abort();
            this.i.destroy();
            this.i = null;
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void begin() {
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void commit() {
    }

    public void startRenderPass() {
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void setTransform(Matrix matrix) {
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void render() {
        startRenderPass();
        render(new o() { // from class: com.artifex.sonui.editor.AnimationLayerView.1
            @Override // com.artifex.solib.o
            public void a(int i) {
                if (i == 0) {
                    AnimationLayerView.this.endRenderPass();
                }
            }
        });
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorView
    public void dispose() {
        dispose(true);
    }

    public class a implements o {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final o f4511a;
        private int c;
        private int d;
        private int e = 0;
        private int f = 0;

        public a(int i, int i2, o oVar) {
            this.c = i;
            this.d = i2;
            this.f4511a = oVar;
        }

        @Override // com.artifex.solib.o
        public void a(int i) {
            if (i != 0) {
                AnimationLayerView.this.clearContent();
                this.f4511a.a(i);
                return;
            }
            int i2 = this.e + 1;
            this.e = i2;
            if (i2 == this.c) {
                this.f++;
                this.e = 0;
            }
            int i3 = this.f;
            int i4 = this.d;
            AnimationLayerView animationLayerView = AnimationLayerView.this;
            if (i3 != i4) {
                animationLayerView.a(this.e, i3, this);
                return;
            }
            animationLayerView.p = animationLayerView.m;
            AnimationLayerView.this.q.set(AnimationLayerView.this.n);
            AnimationLayerView animationLayerView2 = AnimationLayerView.this;
            animationLayerView2.r = animationLayerView2.o;
            this.f4511a.a(i);
        }

        public void a() {
            AnimationLayerView.this.a(this.e, this.f, this);
        }
    }

    private void a() {
        float f;
        if (this.f != null) {
            return;
        }
        Point realScreenSize = Utilities.getRealScreenSize(getContext());
        int iMax = Math.max(realScreenSize.x, realScreenSize.y);
        this.e = iMax / 12;
        int i = realScreenSize.x;
        int i2 = realScreenSize.y;
        if (iMax == i) {
            f = i;
        } else {
            f = i2;
            i2 = i;
        }
        float f2 = f / i2;
        Point point = this.mSize;
        int iMax2 = (int) (Math.max(point.x, point.y) * f2);
        this.d = iMax2;
        if (iMax2 > iMax) {
            this.d = iMax;
        }
        int i3 = this.d;
        if (i3 <= 0) {
            return;
        }
        this.f = this.c.get(i3, SOBitmap.Type.RGBA8888);
    }

    private void a(double d) {
        double d2 = this.mZoomScale;
        if (d2 != d || this.f == null) {
            double d3 = this.mSize.x;
            Double.isNaN(d3);
            Double.isNaN(d3);
            Double.isNaN(d3);
            double dRound = Math.round((d3 * d) / d2);
            double d4 = this.mSize.y;
            Double.isNaN(d4);
            Double.isNaN(d4);
            Double.isNaN(d4);
            double dRound2 = Math.round((d4 * d) / d2);
            Point point = this.mSize;
            point.x = (int) dRound;
            point.y = (int) dRound2;
            double d5 = this.mPosition.x;
            Double.isNaN(d5);
            Double.isNaN(d5);
            Double.isNaN(d5);
            double dRound3 = Math.round((d5 * d) / d2);
            double d6 = this.mPosition.y;
            Double.isNaN(d6);
            Double.isNaN(d6);
            Double.isNaN(d6);
            double dRound4 = Math.round((d6 * d) / d2);
            PointF pointF = this.mPosition;
            float f = (float) dRound3;
            pointF.x = f;
            float f2 = (float) dRound4;
            pointF.y = f2;
            setX(f);
            setY(f2);
            this.mZoomScale = d;
            a();
            SOBitmap sOBitmap = this.f;
            if (sOBitmap == null) {
                return;
            }
            if (this.mSize.x > sOBitmap.a().getWidth()) {
                this.mSize.x = this.f.a().getWidth();
            }
            if (this.mSize.y > this.f.a().getHeight()) {
                this.mSize.y = this.f.a().getHeight();
            }
            b();
        }
    }

    private void a(SOBitmap sOBitmap, o oVar, Rect rect) {
        double dWidth = rect.width();
        double d = this.e;
        Double.isNaN(dWidth);
        Double.isNaN(d);
        Double.isNaN(dWidth);
        Double.isNaN(d);
        int iCeil = (int) Math.ceil(e6.z(dWidth, d, dWidth, d));
        double dHeight = rect.height();
        double d2 = this.e;
        Double.isNaN(dHeight);
        Double.isNaN(d2);
        Double.isNaN(dHeight);
        Double.isNaN(d2);
        int iCeil2 = (int) Math.ceil(e6.z(dHeight, d2, dHeight, d2));
        this.k.set(rect);
        this.n.set(this.k);
        this.o = this.mZoomScale;
        this.s = null;
        Rect rect2 = this.k;
        this.m = new SOBitmap(sOBitmap, rect2.left, rect2.top, rect2.right, rect2.bottom);
        new a(iCeil, iCeil2, oVar).a();
    }
}
