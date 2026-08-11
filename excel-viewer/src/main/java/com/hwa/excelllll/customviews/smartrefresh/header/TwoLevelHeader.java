package com.hwa.excelllll.customviews.smartrefresh.header;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.DimensionStatus;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class TwoLevelHeader extends InternalAbstract implements RefreshHeader {

    /* JADX INFO: renamed from: d */
    public int f48558d;

    /* JADX INFO: renamed from: e */
    public float f48559e;

    /* JADX INFO: renamed from: f */
    public int f48560f;

    /* JADX INFO: renamed from: g */
    public RefreshHeader f48561g;

    /* JADX INFO: renamed from: h */
    public RefreshKernel f48562h;

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.header.TwoLevelHeader$1 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static /* synthetic */ class C74401 {

        /* JADX INFO: renamed from: a */
        public static final /* synthetic */ int[] f48563a;

        static {
            int[] iArr = new int[RefreshState.values().length];
            f48563a = iArr;
            try {
                RefreshState refreshState = RefreshState.None;
                iArr[8] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                int[] iArr2 = f48563a;
                RefreshState refreshState2 = RefreshState.None;
                iArr2[13] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                int[] iArr3 = f48563a;
                RefreshState refreshState3 = RefreshState.None;
                iArr3[16] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                int[] iArr4 = f48563a;
                RefreshState refreshState4 = RefreshState.None;
                iArr4[1] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnStateChangedListener
    /* JADX INFO: renamed from: b */
    public final void mo21908b(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        RefreshHeader refreshHeader = this.f48561g;
        if (refreshHeader != null) {
            if (refreshState2 == RefreshState.ReleaseToRefresh) {
                refreshState2 = RefreshState.PullDownToRefresh;
            }
            refreshHeader.mo21908b(refreshLayout, refreshState, refreshState2);
            int iOrdinal = refreshState2.ordinal();
            if (iOrdinal == 1) {
                if (refreshHeader.getView().getAlpha() != 0.0f || refreshHeader.getView() == this) {
                    return;
                }
                refreshHeader.getView().setAlpha(1.0f);
                return;
            }
            if (iOrdinal != 8) {
                if (iOrdinal == 16 && refreshHeader.getView() != this) {
                    refreshHeader.getView().animate().alpha(1.0f).setDuration(0L);
                    return;
                }
                return;
            }
            if (refreshHeader.getView() != this) {
                refreshHeader.getView().animate().alpha(0.0f).setDuration(0L);
            }
            RefreshKernel refreshKernel = this.f48562h;
            if (refreshKernel != null) {
                refreshKernel.mo21891b();
            }
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: e */
    public final void mo21902e(RefreshKernel refreshKernel, int i, int i2) {
        RefreshHeader refreshHeader = this.f48561g;
        if (refreshHeader == null) {
            return;
        }
        if (((i2 + i) * 1.0f) / i != 0.0f && this.f48560f == 0) {
            this.f48560f = i;
            this.f48561g = null;
            SmartRefreshLayout smartRefreshLayoutMo21894e = refreshKernel.mo21894e();
            smartRefreshLayoutMo21894e.f48374Q0 = 0.0f;
            RefreshHeader refreshHeader2 = smartRefreshLayoutMo21894e.f48382U0;
            if (refreshHeader2 == null || !smartRefreshLayoutMo21894e.f48405h1) {
                DimensionStatus dimensionStatus = smartRefreshLayoutMo21894e.f48364L0;
                if (dimensionStatus.f48471b) {
                    dimensionStatus = DimensionStatus.f48469h[dimensionStatus.f48470a - 1];
                    if (dimensionStatus.f48471b) {
                        dimensionStatus = DimensionStatus.f48464c;
                    }
                }
                smartRefreshLayoutMo21894e.f48364L0 = dimensionStatus;
            } else {
                SmartRefreshLayout.RefreshKernelImpl refreshKernelImpl = smartRefreshLayoutMo21894e.f48389Z0;
                int i3 = smartRefreshLayoutMo21894e.f48362K0;
                refreshHeader2.mo21902e(refreshKernelImpl, i3, (int) (0.0f * i3));
            }
            this.f48561g = refreshHeader;
        }
        if (this.f48562h == null && refreshHeader.getSpinnerStyle() == SpinnerStyle.f48497d && !isInEditMode()) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) refreshHeader.getView().getLayoutParams();
            marginLayoutParams.topMargin -= i;
            refreshHeader.getView().setLayoutParams(marginLayoutParams);
        }
        this.f48560f = i;
        this.f48562h = refreshKernel;
        refreshKernel.mo21893d();
        SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
        if (equals(smartRefreshLayout.f48382U0)) {
            smartRefreshLayout.f48401f1 = true;
        } else if (equals(smartRefreshLayout.f48384V0)) {
            smartRefreshLayout.f48403g1 = true;
        }
        refreshHeader.mo21902e(refreshKernel, i, i2);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract
    public final boolean equals(Object obj) {
        RefreshHeader refreshHeader = this.f48561g;
        return (refreshHeader != null && refreshHeader.equals(obj)) || super.equals(obj);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: j */
    public final void mo21906j(boolean z, float f, int i, int i2, int i3) {
        RefreshHeader refreshHeader = this.f48561g;
        if (this.f48558d != i && refreshHeader != null) {
            this.f48558d = i;
            SpinnerStyle spinnerStyle = refreshHeader.getSpinnerStyle();
            if (spinnerStyle == SpinnerStyle.f48497d) {
                refreshHeader.getView().setTranslationY(i);
            } else if (spinnerStyle.f48504c) {
                View view = refreshHeader.getView();
                view.layout(view.getLeft(), view.getTop(), view.getRight(), Math.max(0, i) + view.getTop());
            }
        }
        RefreshHeader refreshHeader2 = this.f48561g;
        RefreshKernel refreshKernel = this.f48562h;
        if (refreshHeader2 != null) {
            refreshHeader2.mo21906j(z, f, i, i2, i3);
        }
        if (z) {
            if ((this.f48559e >= 0.0f && f < 0.0f) || refreshKernel.mo21894e().getState() != RefreshState.ReleaseToTwoLevel) {
                refreshKernel.mo21890a(RefreshState.PullDownToRefresh);
            }
            this.f48559e = f;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f48579b = SpinnerStyle.f48500g;
        if (this.f48561g == null) {
            ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
            RefreshHeader refreshHeader = this.f48561g;
            if (refreshHeader != null) {
                removeView(refreshHeader.getView());
            }
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            ViewGroup.LayoutParams layoutParams2 = classicsHeader.getView().getLayoutParams();
            if (layoutParams2 instanceof LayoutParams) {
                layoutParams = (LayoutParams) layoutParams2;
            }
            if (classicsHeader.getSpinnerStyle() == SpinnerStyle.f48498e) {
                addView(classicsHeader.getView(), 0, layoutParams);
            } else {
                addView(classicsHeader.getView(), getChildCount(), layoutParams);
            }
            this.f48561g = classicsHeader;
            this.f48580c = classicsHeader;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f48579b = SpinnerStyle.f48498e;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RefreshHeader) {
                this.f48561g = (RefreshHeader) childAt;
                this.f48580c = (RefreshInternal) childAt;
                bringChildToFront(childAt);
                return;
            }
        }
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        RefreshHeader refreshHeader = this.f48561g;
        if (refreshHeader == null) {
            super.onMeasure(i, i2);
        } else {
            if (MeasureSpec.getMode(i2) != Integer.MIN_VALUE) {
                super.onMeasure(i, i2);
                return;
            }
            refreshHeader.getView().measure(i, i2);
            super.setMeasuredDimension(View.resolveSize(super.getSuggestedMinimumWidth(), i), refreshHeader.getView().getMeasuredHeight());
        }
    }
}
