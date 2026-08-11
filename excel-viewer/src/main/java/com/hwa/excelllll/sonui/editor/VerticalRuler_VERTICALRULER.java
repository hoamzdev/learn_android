package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class VerticalRuler extends Ruler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Rect f4913a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private float f4914b;

    public VerticalRuler(Context context) {
        super(context);
        this.f4913a = new Rect();
    }

    @Override // com.artifex.sonui.editor.Ruler
    public float getEffectiveScale() {
        return this.f4914b;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        VerticalRuler verticalRuler;
        Canvas canvas2;
        super.onDraw(canvas);
        float[] fArr = this.mGraduations;
        if (fArr == null || fArr.length == 0) {
            return;
        }
        getLocalVisibleRect(this.f4913a);
        float f = 0.0f;
        int i = 0;
        boolean z = false;
        while (true) {
            float[] fArr2 = this.mGraduations;
            if (i >= fArr2.length) {
                return;
            }
            float fRound = Math.round(fArr2[i]);
            float f2 = this.mScale;
            int i2 = this.mOffsetY;
            int i3 = ((int) (f * f2)) - i2;
            int i4 = ((int) (f2 * fRound)) - i2;
            this.mRect.set(0, i3, this.getWidth(), i4);
            if (canvas.getClipBounds().intersect(this.mRect)) {
                z = true;
            } else if (z) {
                return;
            }
            canvas.drawRect(this.mRect, this.mPaint);
            if (i > 0) {
                verticalRuler = this;
                canvas2 = canvas;
                verticalRuler.drawTextCentered(canvas2, this.mPaint, String.valueOf(i), this.getWidth() / 2, (i3 + i4) / 2);
            } else {
                verticalRuler = this;
                canvas2 = canvas;
            }
            i++;
            f = fRound;
            this = verticalRuler;
            canvas = canvas2;
        }
    }

    @Override // com.artifex.sonui.editor.Ruler
    public void onUpdate() {
        Point realScreenSize = Utilities.getRealScreenSize(getContext());
        int i = realScreenSize.x;
        int i2 = realScreenSize.y;
        int iSqrt = (((int) Math.sqrt((i2 * i2) + (i * i))) * 7) / 100;
        int dimension = (int) (((int) getResources().getDimension(R.dimen.sodk_editor_vruler_base_size)) * this.mScale);
        int iMin = Math.min(dimension, iSqrt);
        this.f4914b = (this.mScale * iMin) / dimension;
        getLayoutParams().width = iMin;
        requestLayout();
        invalidate();
        LinearLayout linearLayout = (LinearLayout) ((Activity) getContext()).findViewById(R.id.hruler_spacer);
        if (linearLayout == null) {
            Log.w("VerticalRuler", "hruler_spacer not found in layout — skipping update");
            return;
        }
        linearLayout.getLayoutParams().width = iMin;
        linearLayout.requestLayout();
        linearLayout.invalidate();
    }

    public VerticalRuler(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4913a = new Rect();
    }

    public VerticalRuler(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4913a = new Rect();
    }
}
