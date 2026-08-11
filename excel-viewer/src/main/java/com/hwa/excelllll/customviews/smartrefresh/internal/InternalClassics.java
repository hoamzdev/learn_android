package com.hwa.excelllll.customviews.smartrefresh.internal;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.SmartUtil;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public abstract class InternalClassics<T extends InternalClassics> extends InternalAbstract implements RefreshInternal {

    /* JADX INFO: renamed from: d */
    public TextView f48581d;

    /* JADX INFO: renamed from: e */
    public ImageView f48582e;

    /* JADX INFO: renamed from: f */
    public ImageView f48583f;

    /* JADX INFO: renamed from: g */
    public RefreshKernel f48584g;

    /* JADX INFO: renamed from: h */
    public ArrowDrawable f48585h;

    /* JADX INFO: renamed from: i */
    public ProgressDrawable f48586i;

    /* JADX INFO: renamed from: j */
    public boolean f48587j;

    /* JADX INFO: renamed from: k */
    public boolean f48588k;

    /* JADX INFO: renamed from: l */
    public int f48589l;

    /* JADX INFO: renamed from: m */
    public int f48590m;

    /* JADX INFO: renamed from: n */
    public int f48591n;

    /* JADX INFO: renamed from: o */
    public int f48592o;

    /* JADX INFO: renamed from: p */
    public int f48593p;

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: d */
    public final void mo21901d(RefreshLayout refreshLayout, int i, int i2) {
        ImageView imageView = this.f48583f;
        if (imageView.getVisibility() != 0) {
            imageView.setVisibility(0);
            Object drawable = this.f48583f.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                imageView.animate().rotation(36000.0f).setDuration(100000L);
            }
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: e */
    public final void mo21902e(RefreshKernel refreshKernel, int i, int i2) {
        this.f48584g = refreshKernel;
        refreshKernel.mo21892c(this, this.f48589l);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: f */
    public final void mo21903f(SmartRefreshLayout smartRefreshLayout, int i, int i2) {
        mo21901d(smartRefreshLayout, i, i2);
    }

    /* JADX INFO: renamed from: g */
    public void mo21909g(int i) {
        this.f48587j = true;
        this.f48581d.setTextColor(i);
        ArrowDrawable arrowDrawable = this.f48585h;
        if (arrowDrawable != null) {
            arrowDrawable.f48594a.setColor(i);
            this.f48582e.invalidateDrawable(this.f48585h);
        }
        ProgressDrawable progressDrawable = this.f48586i;
        if (progressDrawable != null) {
            progressDrawable.f48594a.setColor(i);
            this.f48583f.invalidateDrawable(this.f48586i);
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: h */
    public int mo21904h(RefreshLayout refreshLayout, boolean z) {
        ImageView imageView = this.f48583f;
        Object drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        } else {
            imageView.animate().rotation(0.0f).setDuration(0L);
        }
        imageView.setVisibility(8);
        return this.f48590m;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ImageView imageView = this.f48582e;
        ImageView imageView2 = this.f48583f;
        imageView.animate().cancel();
        imageView2.animate().cancel();
        Object drawable = this.f48583f.getDrawable();
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
            }
        }
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        if (this.f48593p == 0) {
            this.f48591n = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            this.f48592o = paddingBottom;
            if (this.f48591n == 0 || paddingBottom == 0) {
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int iM21920c = this.f48591n;
                if (iM21920c == 0) {
                    iM21920c = SmartUtil.m21920c(20.0f);
                }
                this.f48591n = iM21920c;
                int iM21920c2 = this.f48592o;
                if (iM21920c2 == 0) {
                    iM21920c2 = SmartUtil.m21920c(20.0f);
                }
                this.f48592o = iM21920c2;
                setPadding(paddingLeft, this.f48591n, paddingRight, iM21920c2);
            }
            setClipToPadding(false);
        }
        if (MeasureSpec.getMode(i2) == 1073741824) {
            int size = MeasureSpec.getSize(i2);
            int i3 = this.f48593p;
            if (size < i3) {
                int i4 = (size - i3) / 2;
                setPadding(getPaddingLeft(), i4, getPaddingRight(), i4);
            } else {
                setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
            }
        } else {
            setPadding(getPaddingLeft(), this.f48591n, getPaddingRight(), this.f48592o);
        }
        super.onMeasure(i, i2);
        if (this.f48593p == 0) {
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                int measuredHeight = getChildAt(i5).getMeasuredHeight();
                if (this.f48593p < measuredHeight) {
                    this.f48593p = measuredHeight;
                }
            }
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    public void setPrimaryColors(int... iArr) {
        if (iArr.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable) && !this.f48588k) {
                int i = iArr[0];
                this.f48588k = true;
                this.f48589l = i;
                RefreshKernel refreshKernel = this.f48584g;
                if (refreshKernel != null) {
                    refreshKernel.mo21892c(this, i);
                }
                this.f48588k = false;
            }
            if (this.f48587j) {
                return;
            }
            if (iArr.length > 1) {
                mo21909g(iArr[1]);
            } else {
                mo21909g(iArr[0] == -1 ? -10066330 : -1);
            }
            this.f48587j = false;
        }
    }
}
