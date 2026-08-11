package com.hwa.excelllll.sonui.editor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOLib;
import com.artifex.solib.o;
import com.artifex.solib.q;
import com.artifex.sonui.editor.b.b;
import com.artifex.sonui.editor.b.c;
import com.artifex.sonui.editor.b.d;
import com.artifex.sonui.editor.b.e;
import com.artifex.sonui.editor.b.f;
import com.artifex.sonui.editor.b.g;
import com.artifex.sonui.editor.b.i;
import com.artifex.sonui.editor.b.j;
import com.artifex.sonui.editor.b.k;
import com.artifex.sonui.editor.b.l;
import com.artifex.sonui.editor.b.m;
import com.artifex.sonui.editor.b.n;
import com.onesignal.core.internal.permissions.PermissionsViewModel;
import defpackage.a7;

/* JADX INFO: loaded from: classes.dex */
public class SlideShowView extends RelativeLayout implements ComponentCallbacks2, MemoryInfoProvider, SlideAnimationsListener {
    private static SODoc e;
    private static SOLib f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f4836a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private String f4837b;
    private SODoc c;
    private SOLib d;
    private SlideShowViewListener g;
    private int h;
    private final SlideShowPageLayout[] i;
    private int j;
    private int k;
    private int l;
    private RelativeLayout m;
    private LayerBitmapManager n;
    private ViewPropertyAnimator o;
    private boolean p;

    public SlideShowView(Context context) {
        super(context);
        this.f4836a = false;
        this.f4837b = "SlideShowView";
        this.g = null;
        this.h = -1;
        this.i = new SlideShowPageLayout[]{null, null};
        this.j = 0;
        this.k = 1;
        this.l = 0;
        this.n = null;
        this.o = null;
        this.p = false;
        a();
    }

    private void a() {
        LayoutInflater.from(getContext()).inflate(R.layout.sodk_editor_slide_show_layout, this);
        this.c = e;
        this.d = f;
        LayerBitmapManager layerBitmapManager = new LayerBitmapManager();
        this.n = layerBitmapManager;
        layerBitmapManager.setMemoryInfoProvider(this);
        if (this.c == null || this.d == null) {
            a7.z("Document Session or Smart Office library is invalid ");
        } else {
            findViewById(R.id.sodk_editor_slideshow_doc_inner_container).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.SlideShowView.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    SlideShowView.this.findViewById(R.id.sodk_editor_slideshow_doc_inner_container).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    SlideShowView.this.b();
                }
            });
        }
    }

    private void b(String str, final View view, final View view2, int i) {
        this.m.bringChildToFront(view);
        int measuredHeight = view.getMeasuredHeight();
        int measuredWidth = view.getMeasuredWidth();
        float f2 = (str.equals("l") || str.equals("lu") || str.equals("ld")) ? -measuredWidth : 0.0f;
        if (str.equals("r") || str.equals("ru") || str.equals("rd")) {
            f2 = measuredWidth;
        }
        float f3 = (str.equals("d") || str.equals("rd") || str.equals("ld")) ? measuredHeight : 0.0f;
        if (str.equals("u") || str.equals("ru") || str.equals("lu")) {
            f3 = -measuredHeight;
        }
        this.l = 1;
        a(view).translationX(f2).translationY(f3).setDuration(i).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.23
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SlideShowView.this.m.bringChildToFront(view2);
                view.setTranslationX(0.0f);
                view.setTranslationY(0.0f);
                SlideShowView.g(SlideShowView.this);
            }
        });
    }

    private void c(final View view, View view2, int i) {
        this.l++;
        view2.setAlpha(0.0f);
        view2.setVisibility(0);
        a(view2).alpha(1.0f).setDuration(i).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.19
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(4);
                SlideShowView.g(SlideShowView.this);
            }
        });
    }

    private void d() {
        SlideShowViewListener slideShowViewListener = this.g;
        if (slideShowViewListener != null) {
            slideShowViewListener.slideStarted(this.h);
        }
        int i = 1 - this.j;
        this.j = i;
        this.k = 1 - this.k;
        this.m.bringChildToFront(this.i[i]);
        this.i[this.j].setVisibility(4);
        this.i[this.j].setupPage(this.h, this.m.getWidth(), this.m.getHeight());
        this.i[this.j].startRenderPass();
        this.i[this.j].render(new o() { // from class: com.artifex.sonui.editor.SlideShowView.21
            @Override // com.artifex.solib.o
            public void a(int i2) {
                if (i2 == 0) {
                    SlideShowView.this.i[SlideShowView.this.j].endRenderPass();
                    SlideShowView.this.e();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void e() {
        SlideShowPageLayout slideShowPageLayout;
        SlideShowPageLayout slideShowPageLayout2;
        Log.i("sonui", String.format("view w,h = %d %d", Integer.valueOf(this.i[this.j].getMeasuredWidth()), Integer.valueOf(this.i[this.j].getMeasuredHeight())));
        q qVarP = this.i[this.j].getPage().p();
        Log.i("sonui", String.format("page %d transition: %s", Integer.valueOf(this.h + 1), qVarP.f4247a));
        this.i[this.k].setAlpha(1.0f);
        this.i[this.k].setVisibility(0);
        this.i[this.k].setClipPath(null);
        this.i[this.j].setAlpha(1.0f);
        this.i[this.j].setVisibility(0);
        this.i[this.j].setClipPath(null);
        String str = qVarP.f4248b;
        switch (str) {
            case "blinds":
                String str2 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr = this.i;
                l(str2, slideShowPageLayoutArr[this.k], slideShowPageLayoutArr[this.j], qVarP.e);
                break;
            case "circle":
                String str3 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr2 = this.i;
                f(str3, slideShowPageLayoutArr2[this.k], slideShowPageLayoutArr2[this.j], qVarP.e);
                break;
            case "strips":
                String str4 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr3 = this.i;
                e(str4, slideShowPageLayoutArr3[this.k], slideShowPageLayoutArr3[this.j], qVarP.e);
                break;
            case "alphafade":
                SlideShowPageLayout[] slideShowPageLayoutArr4 = this.i;
                slideShowPageLayout = slideShowPageLayoutArr4[this.k];
                slideShowPageLayout2 = slideShowPageLayoutArr4[this.j];
                c(slideShowPageLayout, slideShowPageLayout2, qVarP.e);
                break;
            case "cut":
                String str5 = qVarP.c;
                int iHashCode = str5.hashCode();
                if (iHashCode == -1337459126) {
                    if (str5.equals("thrublk")) {
                        SlideShowPageLayout[] slideShowPageLayoutArr5 = this.i;
                        a(slideShowPageLayoutArr5[this.k], slideShowPageLayoutArr5[this.j], qVarP.e);
                    }
                } else if (iHashCode == 96748 && str5.equals("any")) {
                    SlideShowPageLayout[] slideShowPageLayoutArr6 = this.i;
                    a(slideShowPageLayoutArr6[this.k], slideShowPageLayoutArr6[this.j]);
                }
                break;
            case "comb":
                String str6 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr7 = this.i;
                n(str6, slideShowPageLayoutArr7[this.k], slideShowPageLayoutArr7[this.j], qVarP.e);
                break;
            case "fade":
                String str7 = qVarP.c;
                int iHashCode2 = str7.hashCode();
                if (iHashCode2 == -1337459126) {
                    if (str7.equals("thrublk")) {
                        SlideShowPageLayout[] slideShowPageLayoutArr8 = this.i;
                        b(slideShowPageLayoutArr8[this.k], slideShowPageLayoutArr8[this.j], qVarP.e);
                    }
                } else if (iHashCode2 == 96748 && str7.equals("any")) {
                    SlideShowPageLayout[] slideShowPageLayoutArr9 = this.i;
                    slideShowPageLayout = slideShowPageLayoutArr9[this.k];
                    slideShowPageLayout2 = slideShowPageLayoutArr9[this.j];
                    c(slideShowPageLayout, slideShowPageLayout2, qVarP.e);
                }
                break;
            case "plus":
                String str8 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr10 = this.i;
                h(str8, slideShowPageLayoutArr10[this.k], slideShowPageLayoutArr10[this.j], qVarP.e);
                break;
            case "pull":
                String str9 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr11 = this.i;
                b(str9, slideShowPageLayoutArr11[this.k], slideShowPageLayoutArr11[this.j], qVarP.e);
                break;
            case "push":
                String str10 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr12 = this.i;
                j(str10, slideShowPageLayoutArr12[this.k], slideShowPageLayoutArr12[this.j], qVarP.e);
                break;
            case "wipe":
                String str11 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr13 = this.i;
                a(str11, slideShowPageLayoutArr13[this.k], slideShowPageLayoutArr13[this.j], qVarP.e);
                break;
            case "zoom":
                String str12 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr14 = this.i;
                p(str12, slideShowPageLayoutArr14[this.k], slideShowPageLayoutArr14[this.j], qVarP.e);
                break;
            case "cover":
                String str13 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr15 = this.i;
                k(str13, slideShowPageLayoutArr15[this.k], slideShowPageLayoutArr15[this.j], qVarP.e);
                break;
            case "split":
                String str14 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr16 = this.i;
                d(str14, slideShowPageLayoutArr16[this.k], slideShowPageLayoutArr16[this.j], qVarP.e);
                break;
            case "wedge":
                String str15 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr17 = this.i;
                q(str15, slideShowPageLayoutArr17[this.k], slideShowPageLayoutArr17[this.j], qVarP.e);
                break;
            case "wheel":
                String str16 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr18 = this.i;
                c(str16, slideShowPageLayoutArr18[this.k], slideShowPageLayoutArr18[this.j], qVarP.e);
                break;
            case "randombars":
                String str17 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr19 = this.i;
                o(str17, slideShowPageLayoutArr19[this.k], slideShowPageLayoutArr19[this.j], qVarP.e);
                break;
            case "dissolve":
                String str18 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr20 = this.i;
                r(str18, slideShowPageLayoutArr20[this.k], slideShowPageLayoutArr20[this.j], qVarP.e);
                break;
            case "newsflash":
                String str19 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr21 = this.i;
                i(str19, slideShowPageLayoutArr21[this.k], slideShowPageLayoutArr21[this.j], qVarP.e);
                break;
            case "checker":
                String str20 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr22 = this.i;
                m(str20, slideShowPageLayoutArr22[this.k], slideShowPageLayoutArr22[this.j], qVarP.e);
                break;
            case "diamond":
                String str21 = qVarP.c;
                SlideShowPageLayout[] slideShowPageLayoutArr23 = this.i;
                g(str21, slideShowPageLayoutArr23[this.k], slideShowPageLayoutArr23[this.j], qVarP.e);
                break;
            default:
                if (str.equals("blinds")) {
                    String str22 = qVarP.c;
                    SlideShowPageLayout[] slideShowPageLayoutArr24 = this.i;
                    l(str22, slideShowPageLayoutArr24[this.k], slideShowPageLayoutArr24[this.j], qVarP.e);
                    break;
                }
                SlideShowPageLayout[] slideShowPageLayoutArr25 = this.i;
                c(slideShowPageLayoutArr25[this.k], slideShowPageLayoutArr25[this.j], PermissionsViewModel.DELAY_TIME_CALLBACK_CALL);
                break;
        }
    }

    private void f(String str, View view, final View view2, int i) {
        this.l = 1;
        c cVar = new c(str, view, view2, i);
        cVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.27
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(cVar);
    }

    private void g(String str, View view, final View view2, int i) {
        this.l = 1;
        d dVar = new d(str, view, view2, i);
        dVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.2
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(dVar);
    }

    private void h(String str, View view, final View view2, int i) {
        this.l = 1;
        f fVar = new f(str, view, view2, i);
        fVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.3
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(fVar);
    }

    private void i(String str, View view, final View view2, int i) {
        this.l = 1;
        view2.setScaleX(0.0f);
        view2.setScaleY(0.0f);
        view2.setRotation(0.0f);
        view2.setAlpha(0.3f);
        view2.setVisibility(0);
        a(view2).scaleX(1.0f).scaleY(1.0f).rotation(360.0f).alpha(1.0f).setDuration(i).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view2.setRotation(0.0f);
                SlideShowView.g(SlideShowView.this);
            }
        });
    }

    private void j(String str, final View view, final View view2, int i) {
        this.l = 2;
        int measuredHeight = str.equals("d") ? view2.getMeasuredHeight() : 0;
        if (str.equals("u")) {
            measuredHeight = -view2.getMeasuredHeight();
        }
        int measuredWidth = str.equals("l") ? -view2.getMeasuredWidth() : 0;
        if (str.equals("r")) {
            measuredWidth = view2.getMeasuredWidth();
        }
        view2.setTranslationY(-measuredHeight);
        view2.setTranslationX(-measuredWidth);
        view2.setVisibility(0);
        final ViewPropertyAnimator viewPropertyAnimatorAnimate = view2.animate();
        long j = i;
        viewPropertyAnimatorAnimate.translationX(0.0f).translationY(0.0f).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view2.setTranslationY(0.0f);
                view2.setTranslationX(0.0f);
                SlideShowView.g(SlideShowView.this);
                viewPropertyAnimatorAnimate.setListener(null);
                viewPropertyAnimatorAnimate.cancel();
            }
        });
        view.setVisibility(0);
        final ViewPropertyAnimator viewPropertyAnimatorAnimate2 = view.animate();
        viewPropertyAnimatorAnimate2.translationX(measuredWidth).translationY(measuredHeight).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                SlideShowView.g(SlideShowView.this);
                viewPropertyAnimatorAnimate2.setListener(null);
                viewPropertyAnimatorAnimate2.cancel();
            }
        });
    }

    private void k(String str, View view, View view2, int i) {
        this.l = 1;
        int measuredHeight = str.contains("d") ? view2.getMeasuredHeight() : 0;
        if (str.contains("u")) {
            measuredHeight = -view2.getMeasuredHeight();
        }
        int measuredWidth = str.contains("l") ? -view2.getMeasuredWidth() : 0;
        if (str.contains("r")) {
            measuredWidth = view2.getMeasuredWidth();
        }
        view2.setTranslationY(-measuredHeight);
        view2.setTranslationX(-measuredWidth);
        view2.setVisibility(0);
        a(view2).translationX(0.0f).translationY(0.0f).setDuration(i).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SlideShowView.g(SlideShowView.this);
            }
        });
    }

    private void l(String str, View view, final View view2, int i) {
        this.l = 1;
        com.artifex.sonui.editor.b.a aVar = new com.artifex.sonui.editor.b.a(str, view, view2, i);
        aVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.8
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(aVar);
    }

    private void m(String str, View view, final View view2, int i) {
        this.l = 1;
        b bVar = new b(str, view, view2, i);
        bVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.9
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(bVar);
    }

    private void n(String str, View view, final View view2, int i) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        this.l = 2;
        view2.setVisibility(4);
        int measuredWidth = view2.getMeasuredWidth();
        int measuredHeight = view2.getMeasuredHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        int i2 = 0;
        view2.layout(0, 0, measuredWidth, measuredHeight);
        view2.draw(canvas);
        final ClippedImageView clippedImageViewA = a(view2, bitmapCreateBitmap);
        final ClippedImageView clippedImageViewA2 = a(view2, bitmapCreateBitmap);
        Path path = new Path();
        while (true) {
            float f9 = 0.0f;
            if (i2 >= 20) {
                break;
            }
            if (str.equals("horz")) {
                f7 = measuredWidth;
                f8 = ((i2 + 1) * measuredHeight) / 20;
                f9 = (i2 * measuredHeight) / 20;
                f6 = 0.0f;
            } else {
                f6 = (i2 * measuredWidth) / 20;
                f7 = ((i2 + 1) * measuredWidth) / 20;
                f8 = measuredHeight;
            }
            path.addRect(f6, f9, f7, f8, Path.Direction.CW);
            i2 += 2;
        }
        clippedImageViewA.setClipPath(path);
        Path path2 = new Path();
        for (int i3 = 1; i3 < 20; i3 += 2) {
            if (str.equals("horz")) {
                f2 = measuredWidth;
                f3 = ((i3 + 1) * measuredHeight) / 20;
                f5 = (i3 * measuredHeight) / 20;
                f4 = 0.0f;
            } else {
                f2 = ((i3 + 1) * measuredWidth) / 20;
                f3 = measuredHeight;
                f4 = (i3 * measuredWidth) / 20;
                f5 = 0.0f;
            }
            path2.addRect(f4, f5, f2, f3, Path.Direction.CW);
        }
        clippedImageViewA2.setClipPath(path2);
        if (str.equals("horz")) {
            clippedImageViewA.setTranslationX(-measuredWidth);
            clippedImageViewA2.setTranslationX(measuredWidth);
        } else {
            clippedImageViewA.setTranslationY(-measuredHeight);
            clippedImageViewA2.setTranslationY(measuredHeight);
        }
        final ViewPropertyAnimator viewPropertyAnimatorAnimate = clippedImageViewA.animate();
        long j = i;
        viewPropertyAnimatorAnimate.translationX(0.0f).translationY(0.0f).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SlideShowView.g(SlideShowView.this);
                if (SlideShowView.this.l == 0) {
                    view2.setVisibility(0);
                    SlideShowView.this.m.removeView(clippedImageViewA);
                    SlideShowView.this.m.removeView(clippedImageViewA2);
                }
                viewPropertyAnimatorAnimate.cancel();
                viewPropertyAnimatorAnimate.setListener(null);
            }
        });
        final ViewPropertyAnimator viewPropertyAnimatorAnimate2 = clippedImageViewA2.animate();
        viewPropertyAnimatorAnimate2.translationX(0.0f).translationY(0.0f).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.11
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SlideShowView.g(SlideShowView.this);
                if (SlideShowView.this.l == 0) {
                    view2.setVisibility(0);
                    SlideShowView.this.m.removeView(clippedImageViewA);
                    SlideShowView.this.m.removeView(clippedImageViewA2);
                }
                viewPropertyAnimatorAnimate2.cancel();
                viewPropertyAnimatorAnimate2.setListener(null);
            }
        });
    }

    private void o(String str, View view, final View view2, int i) {
        this.l = 1;
        g gVar = new g(str, view, view2, i);
        gVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.13
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(gVar);
    }

    private void p(String str, View view, final View view2, int i) {
        this.l = 1;
        this.m.bringChildToFront(view);
        com.artifex.sonui.editor.b.o oVar = new com.artifex.sonui.editor.b.o(str, view, view2, i);
        oVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.14
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.m.bringChildToFront(view2);
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(oVar);
    }

    private void q(String str, View view, final View view2, int i) {
        this.l = 1;
        l lVar = new l(str, view, view2, i);
        lVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.15
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(lVar);
    }

    private void r(String str, View view, final View view2, int i) {
        this.l = 1;
        e eVar = new e(str, view, view2, i);
        eVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.16
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(eVar);
    }

    public static void setDoc(SODoc sODoc) {
        e = sODoc;
    }

    public static void setLib(SOLib sOLib) {
        f = sOLib;
    }

    @Override // com.artifex.sonui.editor.SlideAnimationsListener
    public void animating(int i) {
        SlideShowViewListener slideShowViewListener = this.g;
        if (slideShowViewListener != null) {
            slideShowViewListener.slideAnimating(i);
        }
    }

    @Override // com.artifex.sonui.editor.SlideAnimationsListener
    public void animationsCompleted(int i) {
        SlideShowViewListener slideShowViewListener = this.g;
        if (slideShowViewListener != null) {
            slideShowViewListener.slideAnimationsCompleted(i);
        }
    }

    @Override // com.artifex.sonui.editor.SlideAnimationsListener
    public void animationsStarted(int i) {
        SlideShowViewListener slideShowViewListener = this.g;
        if (slideShowViewListener != null) {
            slideShowViewListener.slideAnimationsStarted(i);
        }
    }

    @Override // com.artifex.sonui.editor.SlideAnimationsListener
    public void animationsWaiting(int i) {
        SlideShowViewListener slideShowViewListener = this.g;
        if (slideShowViewListener != null) {
            slideShowViewListener.slideAnimationsWaiting(i);
        }
    }

    @Override // com.artifex.sonui.editor.MemoryInfoProvider
    public boolean checkMemoryAvailable(long j) {
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        Runtime runtime = Runtime.getRuntime();
        long jFreeMemory = runtime.freeMemory() + (runtime.maxMemory() - runtime.totalMemory());
        if (this.f4836a) {
            Log.v(this.f4837b, String.format("total memory: %d, low mem:%b, trimming:%b", Long.valueOf(memoryInfo.totalMem), Boolean.valueOf(memoryInfo.lowMemory), Boolean.valueOf(this.p)));
            Log.v(this.f4837b, String.format("Memory left: %d, bytes wanted:%d", Long.valueOf(jFreeMemory), Long.valueOf(j)));
        }
        if (jFreeMemory > memoryInfo.totalMem / 25) {
            this.p = false;
        }
        return !this.p && jFreeMemory > 5242880 && !memoryInfo.lowMemory && jFreeMemory > (j * 5) / 4;
    }

    public void dispose() {
        this.c = null;
        this.d = null;
        this.g = null;
        int i = 0;
        while (true) {
            SlideShowPageLayout[] slideShowPageLayoutArr = this.i;
            if (i >= slideShowPageLayoutArr.length) {
                break;
            }
            SlideShowPageLayout slideShowPageLayout = slideShowPageLayoutArr[i];
            if (slideShowPageLayout != null) {
                slideShowPageLayout.finish();
                this.i[i] = null;
            }
            i++;
        }
        this.m.removeAllViews();
        this.m = null;
        this.n.setMemoryInfoProvider(null);
        this.n.dispose();
        this.n = null;
        ViewPropertyAnimator viewPropertyAnimator = this.o;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.setListener(null);
            this.o.cancel();
            this.o = null;
        }
    }

    public PointF getPageViewOffset() {
        int[] iArr = {0, 0};
        SlideShowPageLayout slideShowPageLayout = this.i[this.j];
        if (slideShowPageLayout != null) {
            slideShowPageLayout.getLocationOnScreen(iArr);
        }
        return new PointF(iArr[0], iArr[1]);
    }

    public double getPageViewZoomScale() {
        SlideShowPageLayout slideShowPageLayout = this.i[this.j];
        if (slideShowPageLayout != null) {
            return slideShowPageLayout.getZoomScale();
        }
        return 1.0d;
    }

    public void goBack() {
        if (this.i[this.j].prevAnim()) {
            return;
        }
        previousSlide();
    }

    public void goForward() {
        if (this.i[this.j].nextAnim()) {
            return;
        }
        nextSlide();
    }

    public void nextSlide() {
        int i;
        if (this.l > 0) {
            return;
        }
        SlideShowViewListener slideShowViewListener = this.g;
        if (slideShowViewListener != null && (i = this.h) >= 0) {
            slideShowViewListener.slideEnded(i);
        }
        int i2 = this.h + 1;
        this.h = i2;
        if (i2 < this.c.r()) {
            d();
            return;
        }
        SlideShowViewListener slideShowViewListener2 = this.g;
        if (slideShowViewListener2 != null) {
            slideShowViewListener2.slideShowCompleted();
        }
    }

    @Override // android.view.View, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.m.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.SlideShowView.20
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                SlideShowView.this.m.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                SlideShowView.this.resize(SlideShowView.this.m.getWidth(), SlideShowView.this.m.getHeight(), true);
            }
        });
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        Runtime runtime = Runtime.getRuntime();
        long jMaxMemory = runtime.maxMemory();
        long j = runtime.totalMemory();
        long jFreeMemory = runtime.freeMemory();
        if (this.f4836a) {
            Log.i(this.f4837b, String.format("onTrimMemory(), Memory: %d, level:%d", Long.valueOf((jMaxMemory - j) + jFreeMemory), Integer.valueOf(i)));
        }
        if (i == 5 || i == 10 || i == 15) {
            this.p = true;
            if (this.l == 0) {
                this.i[1 - this.j].clearUpAnimTiles(true);
            }
        }
    }

    public void previousSlide() {
        int i;
        if (this.l <= 0 && (i = this.h) > 0) {
            SlideShowViewListener slideShowViewListener = this.g;
            if (slideShowViewListener != null) {
                slideShowViewListener.slideEnded(i);
            }
            this.h--;
            d();
        }
    }

    public void resize(int i, int i2, boolean z) {
        for (int i3 = 0; i3 <= 1; i3++) {
            if (this.i[i3].getPageNumber() >= 0) {
                this.i[i3].resize(i, i2, z);
            }
        }
    }

    public void setListener(SlideShowViewListener slideShowViewListener) {
        this.g = slideShowViewListener;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public static /* synthetic */ int g(SlideShowView slideShowView) {
        int i = slideShowView.l;
        slideShowView.l = i - 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        nextSlide();
    }

    public SlideShowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4836a = false;
        this.f4837b = "SlideShowView";
        this.g = null;
        this.h = -1;
        this.i = new SlideShowPageLayout[]{null, null};
        this.j = 0;
        this.k = 1;
        this.l = 0;
        this.n = null;
        this.o = null;
        this.p = false;
        a();
    }

    public SlideShowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4836a = false;
        this.f4837b = "SlideShowView";
        this.g = null;
        this.h = -1;
        this.i = new SlideShowPageLayout[]{null, null};
        this.j = 0;
        this.k = 1;
        this.l = 0;
        this.n = null;
        this.o = null;
        this.p = false;
        a();
    }

    private void c(String str, View view, final View view2, int i) {
        this.l = 1;
        m mVar = new m(str, view, view2, i);
        mVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.24
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(mVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ViewPropertyAnimator a(View view) {
        synchronized (this) {
            if (view == null) {
                return null;
            }
            try {
                ViewPropertyAnimator viewPropertyAnimator = this.o;
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.setListener(null);
                    this.o.cancel();
                    this.o = null;
                }
                ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
                this.o = viewPropertyAnimatorAnimate;
                return viewPropertyAnimatorAnimate;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private ClippedImageView a(View view, Bitmap bitmap) {
        ClippedImageView clippedImageView = new ClippedImageView(getContext());
        clippedImageView.setImageBitmap(bitmap);
        this.m.addView(clippedImageView);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.width = view.getWidth();
        layoutParams.height = view.getHeight();
        layoutParams.addRule(15, -1);
        clippedImageView.setLayoutParams(layoutParams);
        return clippedImageView;
    }

    private void a(View view, View view2) {
        view2.setVisibility(0);
        view.setVisibility(4);
    }

    private void a(View view, final View view2, int i) {
        this.l = 1;
        view2.setVisibility(4);
        view.setVisibility(4);
        new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.SlideShowView.17
            @Override // java.lang.Runnable
            public void run() {
                view2.setVisibility(0);
                SlideShowView.this.l = 0;
            }
        }, i);
    }

    private void a(String str, View view, final View view2, int i) {
        this.l = 1;
        n nVar = new n(str, view, view2, i);
        nVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.22
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(nVar);
    }

    private void d(String str, View view, final View view2, int i) {
        this.l = 1;
        this.m.bringChildToFront(view);
        i iVar = new i(str, view, view2, i);
        iVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.25
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.m.bringChildToFront(view2);
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(iVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.i[0] = new SlideShowPageLayout(this.c, this.n, getContext(), this.d);
        this.i[1] = new SlideShowPageLayout(this.c, this.n, getContext(), this.d);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.sodk_editor_slideshow_doc_inner_container);
        this.m = relativeLayout;
        relativeLayout.removeAllViews();
        this.m.addView(this.i[0]);
        this.m.addView(this.i[1]);
        this.i[0].setListener(this);
        this.i[1].setListener(this);
        this.m.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.SlideShowView.12
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                SlideShowView.this.m.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                SlideShowView.this.c();
            }
        });
    }

    private void b(final View view, final View view2, final int i) {
        this.l = 1;
        view2.setAlpha(0.0f);
        a(view).alpha(0.0f).setDuration(i / 2).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.18
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(4);
                view2.setVisibility(0);
                SlideShowView.this.a(view2).alpha(1.0f).setDuration(i / 2).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.editor.SlideShowView.18.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        SlideShowView.this.l = 0;
                    }
                });
            }
        });
    }

    private void e(String str, View view, final View view2, int i) {
        this.l = 1;
        j jVar = new j(str, view, view2, i);
        jVar.a(new k.a() { // from class: com.artifex.sonui.editor.SlideShowView.26
            @Override // com.artifex.sonui.editor.b.k.a
            public void a() {
                view2.clearAnimation();
                SlideShowView.this.l = 0;
            }
        });
        view2.startAnimation(jVar);
    }
}
