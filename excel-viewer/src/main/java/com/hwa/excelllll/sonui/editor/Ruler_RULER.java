package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Ruler extends View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f4746a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Rect f4747b;
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
        this.f4746a = false;
        this.mGraduations = null;
        this.f4747b = new Rect();
        a();
    }

    private void a() {
        setWillNotDraw(false);
        this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(-16777216);
        Paint paint = this.mPaint;
        paint.setTextSize(paint.getTextSize() * 2.0f);
    }

    public void drawTextCentered(Canvas canvas, Paint paint, String str, float f, float f2) {
        float textSize = paint.getTextSize();
        Paint.Style style = paint.getStyle();
        paint.setTextSize((getEffectiveScale() * textSize) / 2.0f);
        paint.getTextBounds(str, 0, str.length(), this.f4747b);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(str, f - this.f4747b.exactCenterX(), f2 - this.f4747b.exactCenterY(), paint);
        paint.setTextSize(textSize);
        paint.setStyle(style);
    }

    public float getEffectiveScale() {
        return this.mScale;
    }

    public void onUpdate() {
        throw new AssertionError();
    }

    public void setGraduations(float[] fArr) {
        if (!Arrays.equals(fArr, this.mGraduations)) {
            this.f4746a = true;
        }
        this.mGraduations = fArr;
    }

    public void setOffsetX(int i) {
        if (i != this.mOffsetX) {
            this.f4746a = true;
        }
        this.mOffsetX = i;
    }

    public void setOffsetY(int i) {
        if (i != this.mOffsetY) {
            this.f4746a = true;
        }
        this.mOffsetY = i;
    }

    public void setScale(float f) {
        if (f != this.mScale) {
            this.f4746a = true;
        }
        this.mScale = f;
    }

    public void update() {
        if (this.f4746a) {
            this.f4746a = false;
            new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.Ruler.1
                @Override // java.lang.Runnable
                public void run() {
                    this.onUpdate();
                }
            });
        }
    }

    public Ruler(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.mRect = new Rect();
        this.f4746a = false;
        this.mGraduations = null;
        this.f4747b = new Rect();
        a();
    }

    public Ruler(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.mRect = new Rect();
        this.f4746a = false;
        this.mGraduations = null;
        this.f4747b = new Rect();
        a();
    }
}
