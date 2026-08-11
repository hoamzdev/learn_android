package com.hwa.excelllll.excelsoui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hwa.excelllll.R;

public class HorizontalRuler extends Ruler {
    private final Rect visibleRect;
    private float effectiveScale;

    public HorizontalRuler(Context context) {
        super(context);
        this.visibleRect = new Rect();
    }

    private static String getColumnLabel(int index) {
        int labelLength = (int) Math.floor(Math.log((index + 1) * 25) / Math.log(26.0d));
        char[] charArr = new char[labelLength];
        for (int i = labelLength - 1; i >= 0; i--) {
            int adjustedIndex = index - 1;
            charArr[i] = (char) ((adjustedIndex % 26) + 65);
            index = adjustedIndex / 26;
        }
        return new String(charArr);
    }

    @Override // com.artifex.sonui.editor.Ruler
    public float getEffectiveScale() {
        return this.effectiveScale;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] graduations = this.mGraduations;
        if (graduations == null || graduations.length == 0) {
            return;
        }
        getLocalVisibleRect(this.visibleRect);
        float prevGraduation = 0.0f;
        int index = 0;
        boolean hasIntersected = false;
        while (true) {
            float[] activeGraduations = this.mGraduations;
            if (index >= activeGraduations.length) {
                return;
            }
            float currentGraduation = Math.round(activeGraduations[index]);
            float scale = this.mScale;
            int offsetX = this.mOffsetX;
            int leftPos = ((int) (prevGraduation * scale)) - offsetX;
            int rightPos = ((int) (scale * currentGraduation)) - offsetX;
            this.mRect.set(leftPos, 0, rightPos, getHeight());
            if (canvas.getClipBounds().intersect(this.mRect)) {
                hasIntersected = true;
            } else if (hasIntersected) {
                return;
            }
            this.mRect.set(leftPos, 0, rightPos, getHeight());
            canvas.drawRect(this.mRect, this.mPaint);
            drawTextCentered(canvas, this.mPaint, getColumnLabel(index), (float) (leftPos + rightPos) / 2, getHeight() / 2);
            index++;
            prevGraduation = currentGraduation;
        }
    }

    @Override // com.artifex.sonui.editor.Ruler
    public void onUpdate() {
        Point realScreenSize = Utilities.getScreenSize(getContext());
        int screenWidth = realScreenSize.x;
        int screenHeight = realScreenSize.y;
        int maxRulerHeight = (((int) Math.sqrt((screenHeight * screenHeight) + (screenWidth * screenWidth))) * 7) / 100;
        int baseHeight = (int) (((int) getResources().getDimension(R.dimen.sodk_editor_hruler_base_size)) * this.mScale);
        int rulerHeight = Math.min(baseHeight, maxRulerHeight);
        this.effectiveScale = (this.mScale * rulerHeight) / baseHeight;
        getLayoutParams().height = rulerHeight;
        requestLayout();
        invalidate();
        ((LinearLayout) getParent()).getLayoutParams().height = rulerHeight;
        getParent().requestLayout();
        invalidate();
    }

    public HorizontalRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.visibleRect = new Rect();
    }

    public HorizontalRuler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.visibleRect = new Rect();
    }
}
