package com.hwa.excelllll.customviews.smartrefresh.util;

import android.content.res.Resources;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ScrollView;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ScrollingView;
import androidx.viewpager.widget.ViewPager;
import p000.AbstractC7479e6;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class SmartUtil implements Interpolator {

    /* JADX INFO: renamed from: c */
    public static final float f48602c;

    /* JADX INFO: renamed from: d */
    public static final float f48603d;

    /* JADX INFO: renamed from: a */
    public final int f48605a;

    /* JADX INFO: renamed from: b */
    public static final float f48601b = Resources.getSystem().getDisplayMetrics().density;

    /* JADX INFO: renamed from: e */
    public static final int f48604e = 1;

    static {
        float fM21924g = 1.0f / m21924g(1.0f);
        f48602c = fM21924g;
        f48603d = 1.0f - (m21924g(1.0f) * fM21924g);
    }

    public SmartUtil(int i) {
        this.f48605a = i;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m21918a(View view, PointF pointF, boolean z) {
        if (view.canScrollVertically(1) && view.getVisibility() == 0) {
            return false;
        }
        if ((view instanceof ViewGroup) && pointF != null && !(view instanceof AbsListView) && !(view instanceof ScrollView) && !(view instanceof ScrollingView) && !(view instanceof WebView) && !(view instanceof NestedScrollingChild)) {
            ViewGroup viewGroup = (ViewGroup) view;
            PointF pointF2 = new PointF();
            for (int childCount = viewGroup.getChildCount(); childCount > 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount - 1);
                if (m21922e(viewGroup, childAt, pointF.x, pointF.y, pointF2)) {
                    if ("fixed".equals(childAt.getTag()) || "fixed-top".equals(childAt.getTag())) {
                        return false;
                    }
                    pointF.offset(pointF2.x, pointF2.y);
                    boolean zM21918a = m21918a(childAt, pointF, z);
                    pointF.offset(-pointF2.x, -pointF2.y);
                    return zM21918a;
                }
            }
        }
        return z || view.canScrollVertically(-1);
    }

    /* JADX INFO: renamed from: b */
    public static boolean m21919b(View view, PointF pointF) {
        if (view.canScrollVertically(-1) && view.getVisibility() == 0) {
            return false;
        }
        if (!(view instanceof ViewGroup) || pointF == null) {
            return true;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        PointF pointF2 = new PointF();
        for (int childCount = viewGroup.getChildCount(); childCount > 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount - 1);
            if (m21922e(viewGroup, childAt, pointF.x, pointF.y, pointF2)) {
                if ("fixed".equals(childAt.getTag()) || "fixed-bottom".equals(childAt.getTag())) {
                    return false;
                }
                pointF.offset(pointF2.x, pointF2.y);
                boolean zM21919b = m21919b(childAt, pointF);
                pointF.offset(-pointF2.x, -pointF2.y);
                return zM21919b;
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: c */
    public static int m21920c(float f) {
        return (int) ((f * f48601b) + 0.5f);
    }

    /* JADX INFO: renamed from: d */
    public static boolean m21921d(View view) {
        return (view instanceof AbsListView) || (view instanceof ScrollView) || (view instanceof ScrollingView) || (view instanceof WebView) || (view instanceof NestedScrollingChild) || (view instanceof ViewPager) || (view instanceof NestedScrollingParent);
    }

    /* JADX INFO: renamed from: e */
    public static boolean m21922e(View view, View view2, float f, float f2, PointF pointF) {
        if (view2.getVisibility() != 0) {
            return false;
        }
        float[] fArr = {f, f2};
        fArr[0] = (view.getScrollX() - view2.getLeft()) + f;
        float scrollY = fArr[1] + (view.getScrollY() - view2.getTop());
        fArr[1] = scrollY;
        float f3 = fArr[0];
        boolean z = f3 >= 0.0f && scrollY >= 0.0f && f3 < ((float) view2.getWidth()) && fArr[1] < ((float) view2.getHeight());
        if (z) {
            pointF.set(fArr[0] - f, fArr[1] - f2);
        }
        return z;
    }

    /* JADX INFO: renamed from: f */
    public static int m21923f(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int i = layoutParams.height;
        view.measure(childMeasureSpec, i > 0 ? View.MeasureSpec.makeMeasureSpec(i, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredHeight();
    }

    /* JADX INFO: renamed from: g */
    public static float m21924g(float f) {
        float f2 = f * 8.0f;
        return f2 < 1.0f ? f2 - (1.0f - ((float) Math.exp(-f2))) : AbstractC7479e6.m22045A(1.0f, (float) Math.exp(1.0f - f2), 0.63212055f, 0.36787945f);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        if (this.f48605a == f48604e) {
            float f2 = 1.0f - f;
            return 1.0f - (f2 * f2);
        }
        float fM21924g = m21924g(f) * f48602c;
        return fM21924g > 0.0f ? fM21924g + f48603d : fM21924g;
    }
}
