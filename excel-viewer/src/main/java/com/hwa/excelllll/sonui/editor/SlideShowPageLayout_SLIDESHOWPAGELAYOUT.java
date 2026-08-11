package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOLib;
import com.artifex.solib.SOPage;
import com.artifex.solib.o;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SlideShowPageLayout extends RelativeLayout implements AnimatableView, SlideShowConductorViewManager {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private SODoc f4825a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SlideShowPageView f4826b;
    private SOBitmap c;
    private boolean d;
    private SlideShowConductor e;
    private LayerBitmapManager f;
    private SlideAnimationsListener g;
    private ArrayList<AnimationLayerView> h;
    private int i;
    private int j;
    private SOLib k;

    public SlideShowPageLayout(SODoc sODoc, LayerBitmapManager layerBitmapManager, Context context, SOLib sOLib) {
        super(context);
        this.d = false;
        this.f = null;
        this.g = null;
        this.h = new ArrayList<>();
        this.i = 0;
        this.j = 0;
        this.k = sOLib;
        setLayoutParams(new LayoutParams(-1, -1));
        this.f4825a = sODoc;
        Point realScreenSize = Utilities.getRealScreenSize(getContext());
        int iMax = Math.max(realScreenSize.x, realScreenSize.y);
        int i = (iMax * 120) / 100;
        NUIDocView.OVERSIZE_MARGIN = (i - iMax) / 2;
        this.c = new SOBitmap(i, i);
        SlideShowPageView slideShowPageView = new SlideShowPageView(getContext(), this.f4825a);
        this.f4826b = slideShowPageView;
        addView(slideShowPageView);
        this.f = layerBitmapManager;
    }

    private void a(int i, int i2) {
        this.f4826b.resize(i, i2);
        Point size = this.f4826b.getSize();
        LayoutParams layoutParams = (LayoutParams) this.f4826b.getLayoutParams();
        layoutParams.width = size.x;
        layoutParams.height = size.y;
        layoutParams.addRule(15, -1);
        layoutParams.addRule(14, -1);
        this.f4826b.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.width = size.x;
        layoutParams2.height = size.y;
        layoutParams2.addRule(15, -1);
        layoutParams2.addRule(14, -1);
        setLayoutParams(layoutParams2);
        if (this.h.isEmpty()) {
            return;
        }
        for (int i3 = 0; i3 < this.h.size(); i3++) {
            this.h.get(i3).resize(i, i2);
        }
    }

    public static /* synthetic */ int c(SlideShowPageLayout slideShowPageLayout) {
        int i = slideShowPageLayout.j - 1;
        slideShowPageLayout.j = i;
        return i;
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void add(SlideShowConductorView slideShowConductorView) {
        AnimationLayerView animationLayerView = (AnimationLayerView) slideShowConductorView;
        if (animationLayerView.getParent() != null) {
            return;
        }
        addView(animationLayerView);
        invalidate();
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void animationsCompleted() {
        SlideAnimationsListener slideAnimationsListener = this.g;
        if (slideAnimationsListener != null) {
            slideAnimationsListener.animationsCompleted(getPageNumber());
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void animationsRunning() {
        SlideAnimationsListener slideAnimationsListener = this.g;
        if (slideAnimationsListener != null) {
            slideAnimationsListener.animating(getPageNumber());
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void animationsStarted() {
        SlideAnimationsListener slideAnimationsListener = this.g;
        if (slideAnimationsListener != null) {
            slideAnimationsListener.animationsStarted(getPageNumber());
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void animationsWaiting() {
        SlideAnimationsListener slideAnimationsListener = this.g;
        if (slideAnimationsListener != null) {
            slideAnimationsListener.animationsWaiting(getPageNumber());
        }
    }

    public void clearUpAnimTiles(boolean z) {
        synchronized (this) {
            try {
                if (!this.h.isEmpty()) {
                    for (int size = this.h.size() - 1; size >= 0; size--) {
                        AnimationLayerView animationLayerView = this.h.get(size);
                        removeView(animationLayerView);
                        animationLayerView.dispose(z);
                        this.h.remove(animationLayerView);
                    }
                    invalidate();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void destroyLayer(SlideShowConductorView slideShowConductorView) {
        AnimationLayerView animationLayerView = (AnimationLayerView) slideShowConductorView;
        removeView(animationLayerView);
        animationLayerView.dispose(true);
        this.h.remove(animationLayerView);
    }

    public void endRenderPass() {
        SlideShowPageView slideShowPageView;
        if (this.d || (slideShowPageView = this.f4826b) == null) {
            return;
        }
        slideShowPageView.endRenderPass();
        if (this.h.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.h.size(); i++) {
            AnimationLayerView animationLayerView = this.h.get(i);
            if (animationLayerView.getParent() != null) {
                animationLayerView.endRenderPass();
            }
        }
    }

    public void finish() {
        SlideShowConductor slideShowConductor = this.e;
        if (slideShowConductor != null) {
            slideShowConductor.stop();
        }
        this.e = null;
        SOBitmap sOBitmap = this.c;
        if (sOBitmap != null && sOBitmap.a() != null) {
            this.c.a().recycle();
        }
        this.c = null;
        Runtime.getRuntime().gc();
        SlideShowPageView slideShowPageView = this.f4826b;
        if (slideShowPageView != null) {
            slideShowPageView.finish();
        }
        removeView(this.f4826b);
        this.f4826b = null;
        clearUpAnimTiles(false);
        this.h = null;
        this.f = null;
        this.f4825a = null;
        this.k = null;
        this.g = null;
        this.d = true;
    }

    @Override // com.artifex.sonui.editor.AnimatableView
    public Path getClipPath() {
        SlideShowPageView slideShowPageView;
        if (this.d || (slideShowPageView = this.f4826b) == null) {
            return null;
        }
        Path clipPath = slideShowPageView.getClipPath();
        if (clipPath != null) {
            clipPath.offset(this.f4826b.getX(), this.f4826b.getY());
        }
        return clipPath;
    }

    public SOPage getPage() {
        SlideShowPageView slideShowPageView;
        if (this.d || (slideShowPageView = this.f4826b) == null) {
            return null;
        }
        return slideShowPageView.getPage();
    }

    public int getPageNumber() {
        SlideShowPageView slideShowPageView;
        if (this.d || (slideShowPageView = this.f4826b) == null) {
            return 0;
        }
        return slideShowPageView.getPageNumber();
    }

    public double getZoomScale() {
        SlideShowPageView slideShowPageView;
        if (this.d || (slideShowPageView = this.f4826b) == null) {
            return 1.0d;
        }
        return slideShowPageView.getZoomScale();
    }

    @Override // android.view.View, com.artifex.sonui.editor.AnimatableView
    public void invalidate() {
        super.invalidate();
        SlideShowPageView slideShowPageView = this.f4826b;
        if (slideShowPageView != null) {
            slideShowPageView.invalidate();
        }
        ArrayList<AnimationLayerView> arrayList = this.h;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.h.size(); i++) {
            AnimationLayerView animationLayerView = this.h.get(i);
            if (animationLayerView.getParent() != null) {
                animationLayerView.invalidate();
            }
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public SlideShowConductorView newLayer(SODoc sODoc, SOPage sOPage, int i, PointF pointF, RectF rectF) {
        AnimationLayerView animationLayerView = new AnimationLayerView(getContext(), sODoc, sOPage, i, pointF, rectF, this.f);
        animationLayerView.resize(this.f4826b.getWidth(), this.f4826b.getHeight());
        this.h.add(animationLayerView);
        return animationLayerView;
    }

    public boolean nextAnim() {
        SlideShowConductor slideShowConductor = this.e;
        return slideShowConductor != null && slideShowConductor.sendEvent(0);
    }

    public boolean prevAnim() {
        return false;
    }

    @Override // com.artifex.sonui.editor.SlideShowConductorViewManager
    public void remove(SlideShowConductorView slideShowConductorView) {
        removeView((AnimationLayerView) slideShowConductorView);
        invalidate();
    }

    public void render(final o oVar) {
        if (this.d || this.f4826b == null) {
            return;
        }
        if (this.h.isEmpty()) {
            this.j = 1;
        } else {
            this.j = this.h.size() + 1;
        }
        this.f4826b.render(this.c, new o() { // from class: com.artifex.sonui.editor.SlideShowPageLayout.3
            @Override // com.artifex.solib.o
            public void a(int i) {
                if (SlideShowPageLayout.c(SlideShowPageLayout.this) == 0) {
                    oVar.a(i);
                }
            }
        });
        if (this.h.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.h.size(); i++) {
            AnimationLayerView animationLayerView = this.h.get(i);
            if (animationLayerView.getParent() == null) {
                int i2 = this.j - 1;
                this.j = i2;
                if (i2 == 0) {
                    oVar.a(0);
                }
            } else {
                animationLayerView.render(new o() { // from class: com.artifex.sonui.editor.SlideShowPageLayout.4
                    @Override // com.artifex.solib.o
                    public void a(int i3) {
                        if (SlideShowPageLayout.c(SlideShowPageLayout.this) == 0) {
                            oVar.a(i3);
                        }
                    }
                });
            }
        }
    }

    public void resize(int i, int i2, boolean z) {
        if (this.d) {
            return;
        }
        a(i, i2);
        if (z) {
            this.f4826b.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.SlideShowPageLayout.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    SlideShowPageLayout.this.f4826b.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    SlideShowPageLayout.this.f4826b.startRenderPass();
                    SlideShowPageLayout.this.f4826b.render(SlideShowPageLayout.this.c, new o() { // from class: com.artifex.sonui.editor.SlideShowPageLayout.1.1
                        @Override // com.artifex.solib.o
                        public void a(int i3) {
                            if (i3 == 0) {
                                SlideShowPageLayout.this.f4826b.endRenderPass();
                                SlideShowPageLayout.this.f4826b.invalidate();
                            }
                        }
                    });
                }
            });
            if (this.h.isEmpty()) {
                return;
            }
            for (int i3 = 0; i3 < this.h.size(); i3++) {
                final AnimationLayerView animationLayerView = this.h.get(i3);
                if (animationLayerView.getParent() != null) {
                    animationLayerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.SlideShowPageLayout.2
                        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                        public void onGlobalLayout() {
                            animationLayerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            animationLayerView.startRenderPass();
                            animationLayerView.render(new o() { // from class: com.artifex.sonui.editor.SlideShowPageLayout.2.1
                                @Override // com.artifex.solib.o
                                public void a(int i4) {
                                    if (i4 == 0) {
                                        animationLayerView.endRenderPass();
                                        animationLayerView.invalidate();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    @Override // com.artifex.sonui.editor.AnimatableView
    public void setClipPath(Path path) {
        Path path2;
        Path path3;
        if (this.d || this.f4826b == null) {
            return;
        }
        if (path != null) {
            path2 = new Path(path);
            path2.offset(-this.f4826b.getX(), -this.f4826b.getY());
        } else {
            path2 = null;
        }
        this.f4826b.setClipPath(path2);
        if (this.h.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.h.size(); i++) {
            AnimationLayerView animationLayerView = this.h.get(i);
            if (animationLayerView.getParent() != null) {
                if (path != null) {
                    path3 = new Path(path);
                    path3.offset(-animationLayerView.getX(), -animationLayerView.getY());
                } else {
                    path3 = null;
                }
                animationLayerView.setClipPath(path3);
            }
        }
    }

    public void setListener(SlideAnimationsListener slideAnimationsListener) {
        this.g = slideAnimationsListener;
    }

    public void setupPage(int i, int i2, int i3) {
        if (this.d) {
            return;
        }
        clearUpAnimTiles(true);
        SlideShowPageView slideShowPageView = this.f4826b;
        if (slideShowPageView != null) {
            slideShowPageView.changePage(i);
            a(i2, i3);
            if (this.k.isAnimationEnabled()) {
                SlideShowConductor slideShowConductor = new SlideShowConductor(this.f4825a, this.f4826b.getPage(), this);
                this.e = slideShowConductor;
                slideShowConductor.start();
                if (this.e.getRunning()) {
                    this.f4826b.setLayer(-1);
                }
            }
        }
    }

    public void startRenderPass() {
        SlideShowPageView slideShowPageView;
        if (this.d || (slideShowPageView = this.f4826b) == null) {
            return;
        }
        slideShowPageView.startRenderPass();
        if (this.h.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.h.size(); i++) {
            AnimationLayerView animationLayerView = this.h.get(i);
            if (animationLayerView.getParent() != null) {
                animationLayerView.startRenderPass();
            }
        }
    }
}
