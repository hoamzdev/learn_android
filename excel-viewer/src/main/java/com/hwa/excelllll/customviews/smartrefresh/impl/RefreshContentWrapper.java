package com.hwa.excelllll.customviews.smartrefresh.impl;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import androidx.viewpager.widget.ViewPager;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshContent;
import com.xlsxreader.excelreader.customviews.smartrefresh.listener.CoordinatorLayoutListener;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.SmartUtil;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class RefreshContentWrapper implements RefreshContent, CoordinatorLayoutListener, ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: a */
    public View f48564a;

    /* JADX INFO: renamed from: b */
    public final View f48565b;

    /* JADX INFO: renamed from: c */
    public View f48566c;

    /* JADX INFO: renamed from: d */
    public View f48567d;

    /* JADX INFO: renamed from: e */
    public View f48568e;

    /* JADX INFO: renamed from: f */
    public int f48569f = 0;

    /* JADX INFO: renamed from: g */
    public boolean f48570g = true;

    /* JADX INFO: renamed from: h */
    public boolean f48571h = true;

    /* JADX INFO: renamed from: i */
    public final ScrollBoundaryDeciderAdapter f48572i;

    public RefreshContentWrapper(View view) {
        ScrollBoundaryDeciderAdapter scrollBoundaryDeciderAdapter = new ScrollBoundaryDeciderAdapter();
        scrollBoundaryDeciderAdapter.f48574b = true;
        this.f48572i = scrollBoundaryDeciderAdapter;
        this.f48566c = view;
        this.f48565b = view;
        this.f48564a = view;
    }

    /* JADX INFO: renamed from: c */
    public static View m21911c(View view, PointF pointF, View view2) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            PointF pointF2 = new PointF();
            for (int childCount = viewGroup.getChildCount(); childCount > 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount - 1);
                if (SmartUtil.m21922e(viewGroup, childAt, pointF.x, pointF.y, pointF2)) {
                    if (!(childAt instanceof ViewPager) && SmartUtil.m21921d(childAt)) {
                        return childAt;
                    }
                    pointF.offset(pointF2.x, pointF2.y);
                    View viewM21911c = m21911c(childAt, pointF, view2);
                    pointF.offset(-pointF2.x, -pointF2.y);
                    return viewM21911c;
                }
            }
        }
        return view2;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m21912a() {
        if (!this.f48571h) {
            return false;
        }
        View view = this.f48564a;
        ScrollBoundaryDeciderAdapter scrollBoundaryDeciderAdapter = this.f48572i;
        return SmartUtil.m21918a(view, scrollBoundaryDeciderAdapter.f48573a, scrollBoundaryDeciderAdapter.f48574b);
    }

    /* JADX INFO: renamed from: b */
    public final boolean m21913b() {
        return this.f48570g && SmartUtil.m21919b(this.f48564a, this.f48572i.f48573a);
    }

    /* JADX INFO: renamed from: d */
    public final void m21914d(int i, int i2, int i3) {
        boolean z;
        View viewFindViewById;
        View viewFindViewById2;
        boolean z2 = true;
        View view = this.f48565b;
        if (i2 == -1 || (viewFindViewById2 = view.findViewById(i2)) == null) {
            z = false;
        } else if (i > 0) {
            viewFindViewById2.setTranslationY(i);
            z = true;
        } else {
            if (viewFindViewById2.getTranslationY() > 0.0f) {
                viewFindViewById2.setTranslationY(0.0f);
            }
            z = false;
        }
        if (i3 == -1 || (viewFindViewById = view.findViewById(i3)) == null) {
            z2 = z;
        } else if (i < 0) {
            viewFindViewById.setTranslationY(i);
        } else {
            if (viewFindViewById.getTranslationY() < 0.0f) {
                viewFindViewById.setTranslationY(0.0f);
            }
            z2 = z;
        }
        if (z2) {
            view.setTranslationY(0.0f);
        } else {
            view.setTranslationY(i);
        }
        View view2 = this.f48567d;
        if (view2 != null) {
            view2.setTranslationY(Math.max(0, i));
        }
        View view3 = this.f48568e;
        if (view3 != null) {
            view3.setTranslationY(Math.min(0, i));
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x0011 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:9:0x0013  */
    /* JADX INFO: renamed from: e */
    public final RefreshContentWrapper m21915e(int i) {
        View view;
        View view2 = this.f48566c;
        if (view2 == null || i == 0) {
            return null;
        }
        if (i < 0) {
            float f = SmartUtil.f48601b;
            if (!view2.canScrollVertically(1)) {
                if (i > 0) {
                    return null;
                }
                view = this.f48566c;
                float f2 = SmartUtil.f48601b;
                if (!view.canScrollVertically(-1)) {
                    return null;
                }
            }
        } else {
            if (i > 0) {
                return null;
            }
            view = this.f48566c;
            float f3 = SmartUtil.f48601b;
            if (!view.canScrollVertically(-1)) {
                return null;
            }
        }
        this.f48569f = i;
        return this;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        try {
            float scaleY = (iIntValue - this.f48569f) * this.f48566c.getScaleY();
            View view = this.f48566c;
            if (view instanceof AbsListView) {
                float f = SmartUtil.f48601b;
                ((AbsListView) view).scrollListBy((int) scaleY);
            } else {
                view.scrollBy(0, (int) scaleY);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.f48569f = iIntValue;
    }
}
