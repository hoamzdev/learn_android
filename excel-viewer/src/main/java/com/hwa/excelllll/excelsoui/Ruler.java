package com.hwa.excelllll.excelsoui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;

public class Ruler extends View {
    private boolean needsUpdate;
    private final Rect textBounds;
    protected float[] mGraduations;
    protected int mOffsetX;
    protected int mOffsetY;
    protected final Paint mPaint;
    protected final Rect mRect;
    protected float mScale;

    public Ruler(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mRect = new Rect();
        this.needsUpdate = false;
        this.mGraduations = null;
        this.textBounds = new Rect();
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(-16777216);
        Paint paint = this.mPaint;
        paint.setTextSize(paint.getTextSize() * 2.0f);
    }

    public void drawTextCentered(Canvas canvas, Paint paint, String text, float x, float y) {
        float textSize = paint.getTextSize();
        Paint.Style style = paint.getStyle();
        paint.setTextSize((getEffectiveScale() * textSize) / 2.0f);
        paint.getTextBounds(text, 0, text.length(), this.textBounds);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(text, x - this.textBounds.exactCenterX(), y - this.textBounds.exactCenterY(), paint);
        paint.setTextSize(textSize);
        paint.setStyle(style);
    }

    public float getEffectiveScale() {
        return this.mScale;
    }

    public void onUpdate() {
        throw new AssertionError();
    }

    public void setGraduations(float[] graduations) {
        if (!Arrays.equals(graduations, this.mGraduations)) {
            this.needsUpdate = true;
        }
        this.mGraduations = graduations;
    }

    public void setOffsetX(int offsetX) {
        if (offsetX != this.mOffsetX) {
            this.needsUpdate = true;
        }
        this.mOffsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        if (offsetY != this.mOffsetY) {
            this.needsUpdate = true;
        }
        this.mOffsetY = offsetY;
    }

    public void setScale(float scale) {
        if (scale != this.mScale) {
            this.needsUpdate = true;
        }
        this.mScale = scale;
    }

    public void update() {
        if (this.needsUpdate) {
            this.needsUpdate = false;
            new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.Ruler.1
                @Override // java.lang.Runnable
                public void run() {
                    Ruler.this.onUpdate();
                }
            });
        }
    }

    public Ruler(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaint = new Paint();
        this.mRect = new Rect();
        this.needsUpdate = false;
        this.mGraduations = null;
        this.textBounds = new Rect();
        init();
    }

    public Ruler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint = new Paint();
        this.mRect = new Rect();
        this.needsUpdate = false;
        this.mGraduations = null;
        this.textBounds = new Rect();
        init();
    }
}
