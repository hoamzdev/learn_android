package com.hwa.excelllll.excelsoui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.hwa.excelllll.R;

public class VerticalRuler extends Ruler {
    private final Rect visibleRect;
    private float effectiveScale;

    public VerticalRuler(Context context) {
        super(context);
        this.visibleRect = new Rect();
    }

    @Override // com.artifex.sonui.editor.Ruler
    public float getEffectiveScale() {
        return this.effectiveScale;
    }

    @SuppressLint("DrawAllocation")
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
            int offsetY = this.mOffsetY;
            int topPos = ((int) (prevGraduation * scale)) - offsetY;
            int bottomPos = ((int) (scale * currentGraduation)) - offsetY;
            this.mRect.set(0, topPos, this.getWidth(), bottomPos);
            if (canvas.getClipBounds().intersect(this.mRect)) {
                hasIntersected = true;
            } else if (hasIntersected) {
                return;
            }
            canvas.drawRect(this.mRect, this.mPaint);
            if (index > 0) {
                drawTextCentered(canvas, this.mPaint, String.valueOf(index), (float) getWidth() / 2, (float) (topPos + bottomPos) / 2);
            }
            index++;
            prevGraduation = currentGraduation;
        }
    }

    @Override // com.artifex.sonui.editor.Ruler
    public void onUpdate() {
        Point realScreenSize = Utilities.getScreenSize(getContext());
        int screenWidth = realScreenSize.x;
        int screenHeight = realScreenSize.y;
        int maxRulerWidth = (((int) Math.sqrt((screenHeight * screenHeight) + (screenWidth * screenWidth))) * 7) / 100;
        int baseWidth = (int) (((int) getResources().getDimension(R.dimen.sodk_editor_vruler_base_size)) * this.mScale);
        int rulerWidth = Math.min(baseWidth, maxRulerWidth);
        this.effectiveScale = (this.mScale * rulerWidth) / baseWidth;
        getLayoutParams().width = rulerWidth;
        requestLayout();
        invalidate();
        LinearLayout hRulerSpacer = (LinearLayout) ((Activity) getContext()).findViewById(R.id.hruler_spacer);
        if (hRulerSpacer == null) {
            Log.w("VerticalRuler", "hruler_spacer not found in layout — skipping update");
            return;
        }
        hRulerSpacer.getLayoutParams().width = rulerWidth;
        hRulerSpacer.requestLayout();
        hRulerSpacer.invalidate();
    }

    public VerticalRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.visibleRect = new Rect();
    }

    public VerticalRuler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.visibleRect = new Rect();
    }
}
