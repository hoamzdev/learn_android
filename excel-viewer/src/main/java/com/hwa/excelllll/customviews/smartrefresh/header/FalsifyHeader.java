package com.hwa.excelllll.customviews.smartrefresh.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import com.xlsxreader.excelreader.R;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.SmartUtil;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class FalsifyHeader extends InternalAbstract implements RefreshHeader {

    /* JADX INFO: renamed from: d */
    public RefreshKernel f48557d;

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int iM21920c = SmartUtil.m21920c(5.0f);
            Context context = getContext();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(-858993460);
            paint.setStrokeWidth(SmartUtil.m21920c(1.0f));
            float f = iM21920c;
            paint.setPathEffect(new DashPathEffect(new float[]{f, f, f, f}, 1.0f));
            canvas.drawRect(f, f, getWidth() - iM21920c, getBottom() - iM21920c, paint);
            TextView textView = new TextView(context);
            textView.setText(context.getString(R.string.srl_component_falsify, getClass().getSimpleName(), Float.valueOf(getHeight() / SmartUtil.f48601b)));
            textView.setTextColor(-858993460);
            textView.setGravity(17);
            textView.measure(MeasureSpec.makeMeasureSpec(getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getHeight(), 1073741824));
            textView.layout(0, 0, getWidth(), getHeight());
            textView.draw(canvas);
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: e */
    public final void mo21902e(RefreshKernel refreshKernel, int i, int i2) {
        this.f48557d = refreshKernel;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: f */
    public final void mo21903f(SmartRefreshLayout smartRefreshLayout, int i, int i2) {
        if (this.f48557d != null) {
            smartRefreshLayout.m21881r();
        }
    }
}
