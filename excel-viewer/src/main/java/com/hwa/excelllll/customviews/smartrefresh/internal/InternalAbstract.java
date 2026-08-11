package com.hwa.excelllll.customviews.smartrefresh.internal;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshFooter;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.xlsxreader.excelreader.customviews.smartrefresh.impl.RefreshFooterWrapper;
import com.xlsxreader.excelreader.customviews.smartrefresh.impl.RefreshHeaderWrapper;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public abstract class InternalAbstract extends RelativeLayout implements RefreshInternal {

    /* JADX INFO: renamed from: a */
    public final View f48578a;

    /* JADX INFO: renamed from: b */
    public SpinnerStyle f48579b;

    /* JADX INFO: renamed from: c */
    public RefreshInternal f48580c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public InternalAbstract(View view) {
        super(view.getContext(), null, 0);
        RefreshInternal refreshInternal = view instanceof RefreshInternal ? (RefreshInternal) view : null;
        this.f48578a = view;
        this.f48580c = refreshInternal;
        boolean z = this instanceof RefreshFooterWrapper;
        SpinnerStyle spinnerStyle = SpinnerStyle.f48500g;
        if (z && (refreshInternal instanceof RefreshHeader) && refreshInternal.getSpinnerStyle() == spinnerStyle) {
            refreshInternal.getView().setScaleY(-1.0f);
            return;
        }
        if (this instanceof RefreshHeaderWrapper) {
            RefreshInternal refreshInternal2 = this.f48580c;
            if ((refreshInternal2 instanceof RefreshFooter) && refreshInternal2.getSpinnerStyle() == spinnerStyle) {
                refreshInternal.getView().setScaleY(-1.0f);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void mo21900a(int i, int i2, float f) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.mo21900a(i, i2, f);
    }

    /* JADX INFO: renamed from: b */
    public void mo21908b(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        if ((this instanceof RefreshFooterWrapper) && (refreshInternal instanceof RefreshHeader)) {
            boolean z = refreshState.f48491b;
            if (z && z && !refreshState.f48492c) {
                refreshState = RefreshState.values()[refreshState.ordinal() - 1];
            }
            boolean z2 = refreshState2.f48491b;
            if (z2 && z2 && !refreshState2.f48492c) {
                refreshState2 = RefreshState.values()[refreshState2.ordinal() - 1];
            }
        } else if ((this instanceof RefreshHeaderWrapper) && (refreshInternal instanceof RefreshFooter)) {
            boolean z3 = refreshState.f48490a;
            if (z3 && z3 && !refreshState.f48492c) {
                refreshState = RefreshState.values()[refreshState.ordinal() + 1];
            }
            boolean z4 = refreshState2.f48490a;
            if (z4 && z4 && !refreshState2.f48492c) {
                refreshState2 = RefreshState.values()[refreshState2.ordinal() + 1];
            }
        }
        RefreshInternal refreshInternal2 = this.f48580c;
        if (refreshInternal2 != null) {
            refreshInternal2.mo21908b(refreshLayout, refreshState, refreshState2);
        }
    }

    /* JADX INFO: renamed from: c */
    public boolean mo21899c(boolean z) {
        RefreshInternal refreshInternal = this.f48580c;
        return (refreshInternal instanceof RefreshFooter) && ((RefreshFooter) refreshInternal).mo21899c(z);
    }

    /* JADX INFO: renamed from: d */
    public void mo21901d(RefreshLayout refreshLayout, int i, int i2) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.mo21901d(refreshLayout, i, i2);
    }

    /* JADX INFO: renamed from: e */
    public void mo21902e(RefreshKernel refreshKernel, int i, int i2) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal != null && refreshInternal != this) {
            refreshInternal.mo21902e(refreshKernel, i, i2);
            return;
        }
        View view = this.f48578a;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof SmartRefreshLayout.LayoutParams) {
                refreshKernel.mo21892c(this, ((SmartRefreshLayout.LayoutParams) layoutParams).f48460a);
            }
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof RefreshInternal) && getView() == ((RefreshInternal) obj).getView();
        }
        return true;
    }

    /* JADX INFO: renamed from: f */
    public void mo21903f(SmartRefreshLayout smartRefreshLayout, int i, int i2) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.mo21903f(smartRefreshLayout, i, i2);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    public SpinnerStyle getSpinnerStyle() {
        int i;
        SpinnerStyle spinnerStyle = this.f48579b;
        if (spinnerStyle != null) {
            return spinnerStyle;
        }
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal != null && refreshInternal != this) {
            return refreshInternal.getSpinnerStyle();
        }
        View view = this.f48578a;
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof SmartRefreshLayout.LayoutParams) {
                SpinnerStyle spinnerStyle2 = ((SmartRefreshLayout.LayoutParams) layoutParams).f48461b;
                this.f48579b = spinnerStyle2;
                if (spinnerStyle2 != null) {
                    return spinnerStyle2;
                }
            }
            if (layoutParams != null && ((i = layoutParams.height) == 0 || i == -1)) {
                for (int i2 = 0; i2 < 5; i2++) {
                    SpinnerStyle spinnerStyle3 = SpinnerStyle.f48501h[i2];
                    if (spinnerStyle3.f48504c) {
                        this.f48579b = spinnerStyle3;
                        return spinnerStyle3;
                    }
                }
            }
        }
        SpinnerStyle spinnerStyle4 = SpinnerStyle.f48497d;
        this.f48579b = spinnerStyle4;
        return spinnerStyle4;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    public View getView() {
        View view = this.f48578a;
        return view == null ? this : view;
    }

    /* JADX INFO: renamed from: h */
    public int mo21904h(RefreshLayout refreshLayout, boolean z) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return 0;
        }
        return refreshInternal.mo21904h(refreshLayout, z);
    }

    /* JADX INFO: renamed from: i */
    public boolean mo21905i() {
        RefreshInternal refreshInternal = this.f48580c;
        return (refreshInternal == null || refreshInternal == this || !refreshInternal.mo21905i()) ? false : true;
    }

    /* JADX INFO: renamed from: j */
    public void mo21906j(boolean z, float f, int i, int i2, int i3) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.mo21906j(z, f, i, i2, i3);
    }

    public void setPrimaryColors(int... iArr) {
        RefreshInternal refreshInternal = this.f48580c;
        if (refreshInternal == null || refreshInternal == this) {
            return;
        }
        refreshInternal.setPrimaryColors(iArr);
    }
}
