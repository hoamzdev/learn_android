package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalRuler extends Ruler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Rect f4648a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private float f4649b;

    public HorizontalRuler(Context context) {
        super(context);
        this.f4648a = new Rect();
    }

    private static String a(int i) {
        int iFloor = (int) Math.floor(Math.log((i + 1) * 25) / Math.log(26.0d));
        char[] cArr = new char[iFloor];
        for (int i2 = iFloor - 1; i2 >= 0; i2--) {
            int i3 = i - 1;
            cArr[i2] = (char) ((i3 % 26) + 65);
            i = i3 / 26;
        }
        return new String(cArr);
    }

    @Override // com.artifex.sonui.editor.Ruler
    public float getEffectiveScale() {
        return this.f4649b;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] fArr = this.mGraduations;
        if (fArr == null || fArr.length == 0) {
            return;
        }
        getLocalVisibleRect(this.f4648a);
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
            int i2 = this.mOffsetX;
            int i3 = ((int) (f * f2)) - i2;
            int i4 = ((int) (f2 * fRound)) - i2;
            this.mRect.set(i3, 0, i4, getHeight());
            if (canvas.getClipBounds().intersect(this.mRect)) {
                z = true;
            } else if (z) {
                return;
            }
            this.mRect.set(i3, 0, i4, getHeight());
            canvas.drawRect(this.mRect, this.mPaint);
            drawTextCentered(canvas, this.mPaint, a(i), (i3 + i4) / 2, getHeight() / 2);
            i++;
            f = fRound;
        }
    }

    @Override // com.artifex.sonui.editor.Ruler
    public void onUpdate() {
        Point realScreenSize = Utilities.getRealScreenSize(getContext());
        int i = realScreenSize.x;
        int i2 = realScreenSize.y;
        int iSqrt = (((int) Math.sqrt((i2 * i2) + (i * i))) * 7) / 100;
        int dimension = (int) (((int) getResources().getDimension(R.dimen.sodk_editor_hruler_base_size)) * this.mScale);
        int iMin = Math.min(dimension, iSqrt);
        this.f4649b = (this.mScale * iMin) / dimension;
        getLayoutParams().height = iMin;
        requestLayout();
        invalidate();
        ((LinearLayout) getParent()).getLayoutParams().height = iMin;
        getParent().requestLayout();
        invalidate();
    }

    public HorizontalRuler(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4648a = new Rect();
    }

    public HorizontalRuler(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4648a = new Rect();
    }
}
