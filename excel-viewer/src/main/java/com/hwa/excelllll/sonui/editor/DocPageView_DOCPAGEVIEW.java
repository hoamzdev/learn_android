package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOHyperlink;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOPageListener;
import com.artifex.solib.SORender;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.o;
import defpackage.d8;
import defpackage.e6;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class DocPageView extends View implements SOPageListener, AnimatableView {
    private static Paint F;
    private final Rect A;
    private boolean B;
    private boolean C;
    private Bitmap D;
    private Point E;
    private DocView G;
    private final Rect H;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Path f4558a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SODoc f4559b;
    private int c;
    private SORender d;
    private boolean e;
    private SODataLeakHandlers f;
    private final Rect g;
    private final Rect h;
    private final int[] i;
    private SOBitmap j;
    private final Rect k;
    private float l;
    private SOBitmap m;
    protected int mLayer;
    protected SOPage mPage;
    protected Rect mPageRect;
    protected PointF mRenderOrigin;
    protected float mScale;
    protected Point mSize;
    protected double mZoom;
    private final Rect n;
    private float o;
    private int p;
    private SOBitmap q;
    private final Rect r;
    private float s;
    private int t;
    private final Paint u;
    private final Rect v;
    private final Rect w;
    private final Paint x;
    private final Paint y;
    private final Paint z;

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public interface ExternalLinkListener {
        void handleExternalLink(int i, Rect rect);
    }

    public DocPageView(Context context, SODoc sODoc) {
        super(context);
        this.A = new Rect();
        this.H = new Rect();
        this.g = new Rect();
        this.h = new Rect();
        this.i = new int[2];
        this.k = new Rect();
        this.n = new Rect();
        this.r = new Rect();
        this.v = new Rect();
        this.w = new Rect();
        this.mLayer = -2;
        this.mPageRect = new Rect();
        this.mRenderOrigin = new PointF();
        this.mScale = 1.0f;
        this.mZoom = 1.0d;
        this.f4558a = null;
        this.B = false;
        this.C = true;
        this.D = null;
        this.E = null;
        this.G = null;
        this.c = -1;
        this.d = null;
        this.e = false;
        this.j = null;
        this.m = null;
        this.p = -1;
        this.q = null;
        this.t = -1;
        setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.f4559b = sODoc;
        this.u = new Paint();
        Paint paint = new Paint();
        this.x = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.t);
        Paint paint2 = new Paint();
        this.z = paint2;
        paint2.setColor(getContext().getColor(R.color.sodk_editor_page_border_color));
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint2.setStrokeWidth(Utilities.convertDpToPixel(2.0f));
        Paint paint3 = new Paint();
        this.y = paint3;
        setSelectedBorderColor(getContext().getColor(R.color.sodk_editor_selected_page_border_color));
        paint3.setStyle(style);
        paint3.setStrokeWidth(Utilities.convertDpToPixel(context.getResources().getInteger(R.integer.sodk_editor_selected_page_border_width)));
        if (F == null) {
            Paint paint4 = new Paint();
            F = paint4;
            paint4.setAntiAlias(true);
            F.setFilterBitmap(true);
            F.setDither(true);
        }
        getDataLeakHandlers();
    }

    private void a(SOBitmap sOBitmap, final o oVar, Rect rect, Rect rect2) {
        try {
            this.g.set(rect2);
            Rect rect3 = this.g;
            int i = NUIDocView.OVERSIZE_MARGIN;
            rect3.offset(i, i);
            this.h.set(rect);
            setPageRect();
            int i2 = 0;
            int iMin = Math.min(Math.max(this.g.top - this.mPageRect.top, 0), NUIDocView.OVERSIZE_MARGIN);
            int iMin2 = Math.min(Math.max(this.mPageRect.bottom - this.g.bottom, 0), NUIDocView.OVERSIZE_MARGIN);
            int iMin3 = Math.min(Math.max(this.g.left - this.mPageRect.left, 0), NUIDocView.OVERSIZE_MARGIN);
            int iMin4 = Math.min(Math.max(this.mPageRect.right - this.g.right, 0), NUIDocView.OVERSIZE_MARGIN);
            if (!b()) {
                i2 = iMin3;
            } else if (!(getParent() instanceof DocListPagesView)) {
                iMin4 = 0;
                i2 = iMin3;
            }
            Rect rect4 = this.g;
            rect4.top -= iMin;
            rect4.bottom += iMin2;
            rect4.left -= i2;
            rect4.right += iMin4;
            Rect rect5 = this.h;
            rect5.top -= iMin;
            rect5.bottom += iMin2;
            rect5.left -= i2;
            rect5.right += iMin4;
            int i3 = rect4.left;
            if (i3 < 0) {
                int i4 = -i3;
                rect4.left = i3 + i4;
                rect5.left += i4;
            }
            if (rect4.right > sOBitmap.c()) {
                int iC = this.g.right - sOBitmap.c();
                this.g.right -= iC;
                this.h.right -= iC;
            }
            Rect rect6 = this.g;
            int i5 = rect6.top;
            if (i5 < 0) {
                int i6 = -i5;
                rect6.top = i5 + i6;
                this.h.top += i6;
            }
            if (rect6.bottom > sOBitmap.d()) {
                int iD = this.g.bottom - sOBitmap.d();
                this.g.bottom -= iD;
                this.h.bottom -= iD;
            }
            this.k.set(this.h);
            this.l = this.mScale;
            Rect rect7 = this.g;
            this.j = new SOBitmap(sOBitmap, rect7.left, rect7.top, rect7.right, rect7.bottom);
            setOrigin();
            SOPage sOPage = this.mPage;
            int i7 = this.mLayer;
            double d = this.mScale;
            double d2 = this.mZoom;
            Double.isNaN(d);
            Double.isNaN(d);
            Double.isNaN(d);
            double d3 = d2 * d;
            PointF pointF = this.mRenderOrigin;
            this.d = sOPage.a(i7, d3, pointF.x, pointF.y, this.j, null, new o() { // from class: com.artifex.sonui.editor.DocPageView.1
                @Override // com.artifex.solib.o
                public void a(int i8) {
                    if (DocPageView.this.e) {
                        return;
                    }
                    DocPageView.this.stopRender();
                    DocPageView docPageView = DocPageView.this;
                    if (i8 == 0) {
                        docPageView.m = docPageView.j;
                        DocPageView.this.n.set(DocPageView.this.k);
                        DocPageView docPageView2 = DocPageView.this;
                        docPageView2.o = docPageView2.l;
                        DocPageView docPageView3 = DocPageView.this;
                        docPageView3.p = docPageView3.a(docPageView3.m);
                    } else {
                        System.out.printf("render error %d for page %d  %n", Integer.valueOf(i8), Integer.valueOf(docPageView.c));
                    }
                    oVar.a(i8);
                }
            }, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean b() {
        DocView docView = getDocView();
        return docView != null && docView.pagesShowing();
    }

    private void getDataLeakHandlers() {
        try {
            SODataLeakHandlers dataLeakHandlers = Utilities.getDataLeakHandlers();
            this.f = dataLeakHandlers;
            if (dataLeakHandlers != null) {
            } else {
                throw new ClassNotFoundException();
            }
        } catch (ClassNotFoundException | ExceptionInInitializerError | LinkageError | SecurityException unused) {
        }
    }

    public boolean canDoubleTap(int i, int i2) {
        return true;
    }

    public void changePage(int i) {
        if (isFinished() || !this.C || this.f4559b == null) {
            return;
        }
        if (i != this.c || this.mPage == null) {
            this.c = i;
            dropPage();
            SOPage page = this.f4559b.getPage(this.c, this);
            this.mPage = page;
            this.f4559b.a(page);
        }
    }

    public void clearContent() {
        this.q = null;
        invalidate();
    }

    public void dropPage() {
        SOPage sOPage = this.mPage;
        if (sOPage != null) {
            this.f4559b.b(sOPage);
            this.mPage.m();
        }
        this.mPage = null;
    }

    public void endRenderPass() {
        this.q = this.m;
        this.r.set(this.n);
        this.s = this.o;
        this.t = this.p;
    }

    public void finish() {
        this.e = true;
        stopRender();
        SOPage sOPage = this.mPage;
        if (sOPage != null) {
            sOPage.n();
            this.mPage = null;
        }
        this.q = null;
        this.m = null;
        this.j = null;
        this.f4559b = null;
    }

    public Rect getChildRect() {
        return this.H;
    }

    @Override // com.artifex.sonui.editor.AnimatableView
    public Path getClipPath() {
        return this.f4558a;
    }

    public SODoc getDoc() {
        return this.f4559b;
    }

    public DocView getDocView() {
        return this.G;
    }

    public double getFactor() {
        double d = this.mZoom;
        double d2 = this.mScale;
        Double.isNaN(d2);
        return e6.a(d2, d2, d, d2);
    }

    public float[] getHorizontalRuler() {
        if (this.e) {
            return null;
        }
        return this.mPage.getHorizontalRuler();
    }

    public SOPage getPage() {
        return this.mPage;
    }

    public int getPageNumber() {
        return this.c;
    }

    public int getReflowWidth() {
        return this.mPage.sizeAtZoom(1.0d).x;
    }

    public SOSelectionLimits getSelectionLimits() {
        SOPage sOPage = this.mPage;
        if (sOPage == null) {
            return null;
        }
        return sOPage.selectionLimits();
    }

    public Point getSize() {
        return this.mSize;
    }

    public int getUnscaledHeight() {
        return (a() ? this.mPage.sizeAtZoom(this.mZoom) : this.mSize).y;
    }

    public int getUnscaledWidth() {
        return (a() ? this.mPage.sizeAtZoom(this.mZoom) : this.mSize).x;
    }

    public float[] getVerticalRuler() {
        if (this.e) {
            return null;
        }
        SOPage sOPage = this.mPage;
        if (sOPage != null) {
            return sOPage.getVerticalRuler();
        }
        Log.e("DocPageView", "getVerticalRuler() called but mPage is null");
        return null;
    }

    public double getZoomScale() {
        double d = this.mZoom;
        double d2 = this.mScale;
        Double.isNaN(d2);
        return e6.a(d2, d2, d, d2);
    }

    public boolean handleFullscreenTap(int i, int i2) {
        return tryHyperlink(new Point(i, i2), null);
    }

    public boolean isCurrent() {
        return this.B;
    }

    public boolean isFinished() {
        return this.e;
    }

    public boolean isValid() {
        return this.C;
    }

    public void onDoubleTap(int i, int i2) {
        Point pointScreenToPage = screenToPage(i, i2);
        this.mPage.select(2, pointScreenToPage.x, pointScreenToPage.y);
        NUIDocView.currentNUIDocView().showUI(true);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Rect rect;
        if (this.e || !isShown() || this.mPage == null) {
            return;
        }
        Bitmap bitmap = this.D;
        if (bitmap != null) {
            Rect rect2 = new Rect(0, 0, bitmap.getWidth(), this.D.getHeight());
            Point screenSize = Utilities.getScreenSize(getContext());
            Point point = this.E;
            if (point == null || (point.x == screenSize.x && point.y == screenSize.y)) {
                rect = this.r;
            } else {
                rect = new Rect();
                getLocalVisibleRect(rect);
            }
            canvas.drawBitmap(this.D, rect2, rect, this.u);
            return;
        }
        if (this.C) {
            this.x.setColor(this.t);
            Rect rect3 = new Rect();
            getLocalVisibleRect(rect3);
            canvas.drawRect(rect3, this.x);
            if (this.f4558a != null) {
                canvas.save();
            }
            SOBitmap sOBitmap = this.q;
            if (sOBitmap == null || sOBitmap.a().isRecycled()) {
                return;
            }
            this.v.set(sOBitmap.b());
            this.w.set(this.r);
            float f = this.s;
            float f2 = this.mScale;
            if (f != f2) {
                Rect rect4 = this.w;
                rect4.left = (int) ((f2 / f) * rect4.left);
                rect4.top = (int) ((f2 / f) * rect4.top);
                rect4.right = (int) ((f2 / f) * rect4.right);
                rect4.bottom = (int) ((f2 / f) * rect4.bottom);
            }
            Path path = this.f4558a;
            if (path != null) {
                canvas.clipPath(path);
            }
            canvas.drawBitmap(sOBitmap.a(), this.v, this.w, this.u);
            this.A.set(0, 0, getWidth(), getHeight());
            canvas.drawRect(this.A, this.B ? this.y : this.z);
            if (this.f4558a != null) {
                canvas.restore();
            }
        }
    }

    public void onReflowScale(DocPageView docPageView) {
        this.mZoom = docPageView.mZoom;
        this.mScale = docPageView.mScale;
        Point point = this.mSize;
        Point point2 = docPageView.mSize;
        point.x = point2.x;
        point.y = point2.y;
        requestLayout();
    }

    public boolean onSingleTap(int i, int i2, boolean z, ExternalLinkListener externalLinkListener) {
        Point pointScreenToPage = screenToPage(i, i2);
        if (tryHyperlink(pointScreenToPage, externalLinkListener)) {
            return true;
        }
        if (!z) {
            return false;
        }
        getDoc().clearSelection();
        this.mPage.select(3, pointScreenToPage.x, pointScreenToPage.y);
        return false;
    }

    public Rect pageToScreen(RectF rectF) {
        double factor = getFactor();
        double d = rectF.left;
        Double.isNaN(d);
        int iA = (int) e6.a(d, d, d, factor);
        double d2 = rectF.top;
        Double.isNaN(d2);
        int iA2 = (int) e6.a(d2, d2, d2, factor);
        double d3 = rectF.right;
        Double.isNaN(d3);
        int iA3 = (int) e6.a(d3, d3, d3, factor);
        double d4 = rectF.bottom;
        Double.isNaN(d4);
        int iA4 = (int) e6.a(d4, d4, d4, factor);
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(iA + i, iA2 + i2, iA3 + i, iA4 + i2);
    }

    public Rect pageToView(RectF rectF) {
        double factor = getFactor();
        Rect rect = new Rect();
        double d = rectF.left;
        Double.isNaN(d);
        rect.left = (int) Math.round(e6.a(d, d, d, factor));
        double d2 = rectF.top;
        Double.isNaN(d2);
        rect.top = (int) Math.round(e6.a(d2, d2, d2, factor));
        double d3 = rectF.right;
        Double.isNaN(d3);
        rect.right = (int) Math.round(e6.a(d3, d3, d3, factor));
        double d4 = rectF.bottom;
        Double.isNaN(d4);
        rect.bottom = (int) Math.round(e6.a(d4, d4, d4, factor));
        return rect;
    }

    public void render(SOBitmap sOBitmap, o oVar) {
        try {
            if (this.e) {
                return;
            }
            Rect rect = new Rect();
            if (!getLocalVisibleRect(rect)) {
                oVar.a(0);
                return;
            }
            Rect rect2 = new Rect();
            if (getGlobalVisibleRect(rect2)) {
                a(sOBitmap, oVar, rect, rect2);
            } else {
                oVar.a(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resize(int i, int i2) {
        SOPage sOPage = this.mPage;
        if (sOPage != null) {
            PointF pointFZoomToFitRect = sOPage.zoomToFitRect(i, i2);
            double dMax = Math.max(pointFZoomToFitRect.x, pointFZoomToFitRect.y);
            this.mZoom = dMax;
            this.mSize = this.mPage.sizeAtZoom(dMax);
        }
    }

    public Rect screenRect() {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        Rect rect = new Rect();
        int i = iArr[0];
        rect.set(i, iArr[1], getChildRect().width() + i, getChildRect().height() + iArr[1]);
        return rect;
    }

    public Point screenToPage(int i, int i2) {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int[] iArrScreenToWindow = Utilities.screenToWindow(iArr, getContext());
        int i3 = iArrScreenToWindow[0];
        int i4 = iArrScreenToWindow[1];
        double factor = getFactor();
        double d = i - i3;
        Double.isNaN(d);
        int iZ = (int) e6.z(d, d, d, factor);
        double d2 = i2 - i4;
        Double.isNaN(d2);
        Double.isNaN(d2);
        Double.isNaN(d2);
        return new Point(iZ, (int) (d2 / factor));
    }

    public void selectTopLeft() {
        this.mPage.select(2, 0.0d, 0.0d);
    }

    public SOSelectionLimits selectionLimits() {
        if (this.e) {
            return null;
        }
        return this.mPage.selectionLimits();
    }

    public void setCaret(int i, int i2) {
        Point pointScreenToPage = screenToPage(i, i2);
        SOHyperlink sOHyperlinkObjectAtPoint = this.mPage.objectAtPoint(pointScreenToPage.x, pointScreenToPage.y);
        if ((sOHyperlinkObjectAtPoint == null || sOHyperlinkObjectAtPoint.url == null) && sOHyperlinkObjectAtPoint.pageNum == -1) {
            this.mPage.select(3, pointScreenToPage.x, pointScreenToPage.y);
        }
    }

    public void setChildRect(Rect rect) {
        this.H.set(rect);
    }

    @Override // com.artifex.sonui.editor.AnimatableView
    public void setClipPath(Path path) {
        this.f4558a = path;
    }

    public void setCurrent(boolean z) {
        if (z != this.B) {
            this.B = z;
            invalidate();
        }
    }

    public void setDocView(DocView docView) {
        this.G = docView;
    }

    public void setLayer(int i) {
        this.mLayer = i;
    }

    public void setNewScale(float f) {
        this.mScale = f;
    }

    public void setOrigin() {
        PointF pointF = this.mRenderOrigin;
        Rect rect = this.h;
        pointF.set(-rect.left, -rect.top);
    }

    public void setPageRect() {
        getLocationOnScreen(this.i);
        Rect rect = this.mPageRect;
        int[] iArr = this.i;
        int i = iArr[0];
        rect.set(i, iArr[1], getChildRect().width() + i, getChildRect().height() + this.i[1]);
        Rect rect2 = this.mPageRect;
        int i2 = NUIDocView.OVERSIZE_MARGIN;
        rect2.offset(i2, i2);
    }

    public void setSelectedBorderColor(int i) {
        this.y.setColor(i);
    }

    public void setSelectionEnd(Point point) {
        Point pointScreenToPage = screenToPage(point);
        this.mPage.a(1, new PointF(pointScreenToPage.x, pointScreenToPage.y));
    }

    public void setSelectionStart(Point point) {
        Point pointScreenToPage = screenToPage(point);
        this.mPage.a(0, new PointF(pointScreenToPage.x, pointScreenToPage.y));
    }

    public void setValid(boolean z) {
        SOBitmap sOBitmap;
        if (z != this.C) {
            this.C = z;
            if (z) {
                Bitmap bitmap = this.D;
                if (bitmap != null) {
                    bitmap.recycle();
                }
                this.D = null;
            } else {
                if (isShown() && (sOBitmap = this.q) != null && !sOBitmap.a().isRecycled()) {
                    this.E = Utilities.getScreenSize(getContext());
                    int iC = this.q.c() / 2;
                    int iD = this.q.d() / 2;
                    Rect rect = new Rect(0, 0, iC, iD);
                    this.D = Bitmap.createBitmap(iC, iD, Bitmap.Config.ARGB_8888);
                    new Canvas(this.D).drawBitmap(this.q.a(), this.q.b(), rect, F);
                }
                this.q = null;
                this.m = null;
                this.j = null;
            }
            invalidate();
        }
    }

    public void setupPage(int i, int i2, int i3) {
        if (isFinished() || !this.C) {
            return;
        }
        changePage(i);
        resize(i2, 1);
    }

    public boolean sizeViewToPage() {
        Point point;
        SOPage sOPage = this.mPage;
        if (sOPage == null || (point = this.mSize) == null) {
            return false;
        }
        int i = point.x;
        int i2 = point.y;
        Point pointSizeAtZoom = sOPage.sizeAtZoom(this.mZoom);
        this.mSize = pointSizeAtZoom;
        if (pointSizeAtZoom == null) {
            return false;
        }
        return (pointSizeAtZoom.x == i && pointSizeAtZoom.y == i2) ? false : true;
    }

    public void stopRender() {
        SORender sORender = this.d;
        if (sORender != null) {
            sORender.abort();
            this.d.destroy();
            this.d = null;
        }
    }

    public boolean tryHyperlink(Point point, ExternalLinkListener externalLinkListener) {
        SOHyperlink sOHyperlinkObjectAtPoint = this.mPage.objectAtPoint(point.x, point.y);
        if (sOHyperlinkObjectAtPoint != null) {
            if (sOHyperlinkObjectAtPoint.url != null) {
                if (ConfigOptions.a().o()) {
                    SODataLeakHandlers sODataLeakHandlers = this.f;
                    if (sODataLeakHandlers == null) {
                        d8.a();
                        return false;
                    }
                    try {
                        sODataLeakHandlers.launchUrlHandler(sOHyperlinkObjectAtPoint.url);
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
            int i = sOHyperlinkObjectAtPoint.pageNum;
            if (i != -1) {
                if (externalLinkListener != null) {
                    externalLinkListener.handleExternalLink(i, sOHyperlinkObjectAtPoint.bbox);
                }
                return true;
            }
        }
        return false;
    }

    public void update(RectF rectF) {
        if (this.e || !isShown()) {
            return;
        }
        ((Activity) getContext()).runOnUiThread(new Runnable(this) { // from class: com.artifex.sonui.editor.DocPageView.2
            @Override // java.lang.Runnable
            public void run() {
                NUIDocView nUIDocViewCurrentNUIDocView = NUIDocView.currentNUIDocView();
                if (nUIDocViewCurrentNUIDocView != null) {
                    nUIDocViewCurrentNUIDocView.triggerRender();
                }
            }
        });
    }

    public Point viewToPage(int i, int i2) {
        double factor = getFactor();
        double d = i;
        Double.isNaN(d);
        int iZ = (int) e6.z(d, d, d, factor);
        double d2 = i2;
        Double.isNaN(d2);
        Double.isNaN(d2);
        Double.isNaN(d2);
        return new Point(iZ, (int) (d2 / factor));
    }

    public void startRenderPass() {
    }

    public void onFullscreen(boolean z) {
    }

    public int viewToPage(int i) {
        double factor = getFactor();
        double d = i;
        Double.isNaN(d);
        return (int) e6.z(d, d, d, factor);
    }

    public Point screenToPage(Point point) {
        return screenToPage(point.x, point.y);
    }

    public PointF screenToPage(PointF pointF) {
        return new PointF(a(pointF.x, pointF.y));
    }

    public Point pageToScreen(Point point) {
        double factor = getFactor();
        double d = point.x;
        Double.isNaN(d);
        int iA = (int) e6.a(d, d, d, factor);
        double d2 = point.y;
        Double.isNaN(d2);
        int iA2 = (int) e6.a(d2, d2, d2, factor);
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        return new Point(iA + iArr[0], iA2 + iArr[1]);
    }

    public Point pageToView(int i, int i2) {
        return new Point(pageToView(i), pageToView(i2));
    }

    public int pageToView(int i) {
        double factor = getFactor();
        double d = i;
        Double.isNaN(d);
        return (int) e6.a(d, d, d, factor);
    }

    public void pageToView(PointF pointF, PointF pointF2) {
        float factor = (float) getFactor();
        pointF2.set(pointF.x * factor, pointF.y * factor);
    }

    public void pageToView(Rect rect, Rect rect2) {
        double factor = getFactor();
        double d = rect.left;
        Double.isNaN(d);
        int iA = (int) e6.a(d, d, d, factor);
        double d2 = rect.top;
        Double.isNaN(d2);
        int iA2 = (int) e6.a(d2, d2, d2, factor);
        double d3 = rect.right;
        Double.isNaN(d3);
        int iA3 = (int) e6.a(d3, d3, d3, factor);
        double d4 = rect.bottom;
        Double.isNaN(d4);
        rect2.set(iA, iA2, iA3, (int) e6.a(d4, d4, d4, factor));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(SOBitmap sOBitmap) {
        if (!this.C) {
            return 0;
        }
        if (sOBitmap != null && sOBitmap.b() != null && sOBitmap.a() != null) {
            Bitmap bitmapA = sOBitmap.a();
            if (bitmapA.isRecycled()) {
                Log.w("DocPageView", "Bitmap is recycled. Skipping getPixel.");
                return -1;
            }
            int i = sOBitmap.b().left + 5;
            int i2 = sOBitmap.b().top + 5;
            int i3 = sOBitmap.b().right - 5;
            int i4 = sOBitmap.b().bottom - 5;
            try {
                return a(new int[]{bitmapA.getPixel(i, i2), bitmapA.getPixel(i3, i2), bitmapA.getPixel(i, i4), bitmapA.getPixel(i3, i4)});
            } catch (Exception e) {
                Log.e("DocPageView", "Error while reading pixel", e);
            }
        }
        return -1;
    }

    private int a(int[] iArr) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 : iArr) {
            i2 += (i5 >> 16) & 255;
            i3 += (i5 >> 8) & 255;
            i4 += i5 & 255;
            i += i5 >>> 24;
        }
        return Color.argb(i / iArr.length, i2 / iArr.length, i3 / iArr.length, i4 / iArr.length);
    }

    private Point a(float f, float f2) {
        return screenToPage((int) f, (int) f2);
    }

    private boolean a() {
        DocView docView = getDocView();
        return docView != null && docView.getReflowMode();
    }
}
