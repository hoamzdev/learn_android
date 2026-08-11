package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class Slider extends View implements View.OnTouchListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private OnSliderEventListener f4888a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private float f4889b;
    private float c;
    private float d;
    private final Paint e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private float k;
    private float l;
    private float m;

    public interface OnSliderEventListener {
        void onFinished(View view, float f);

        void onSlide(View view, float f);
    }

    public Slider(Context context) {
        super(context);
        this.f4888a = null;
        this.f4889b = 0.0f;
        this.c = 1.0f;
        this.d = 0.5f;
        this.e = new Paint();
        this.f = 0;
        this.g = 1;
        this.h = -1;
        this.i = 10;
        this.j = false;
        a((AttributeSet) null);
    }

    private void a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.sodk_editor_Slider, 0, 0);
            this.f = typedArrayObtainStyledAttributes.getColor(R.styleable.sodk_editor_Slider_trackColor, 0);
            this.h = typedArrayObtainStyledAttributes.getColor(R.styleable.sodk_editor_Slider_thumbColor, -1);
            this.g = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.sodk_editor_Slider_trackWidth, 1.0f);
            this.i = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.sodk_editor_Slider_thumbRadius, 10.0f);
            typedArrayObtainStyledAttributes.recycle();
        }
        a();
        setOnTouchListener(this);
    }

    private float b(float f) {
        return !this.j ? f : ((((float) Math.log(f)) - this.l) / this.m) + this.k;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int i = this.f;
        int color = this.h;
        if (!isEnabled()) {
            int color2 = Color.parseColor("#aaaaaa");
            color = Color.parseColor("#aaaaaa");
            i = color2;
        }
        this.e.setStyle(Paint.Style.STROKE);
        this.e.setStrokeWidth(this.g);
        this.e.setColor(i);
        float f = measuredHeight / 2;
        canvas.drawLine(0.0f, f, measuredWidth, f, this.e);
        this.e.setStyle(Paint.Style.FILL);
        this.e.setColor(color);
        int i2 = this.i;
        canvas.drawCircle(((int) ((this.d / (this.c - this.f4889b)) * (measuredWidth - (i2 * 2)))) + i2, f, i2, this.e);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0017  */
    /* JADX WARN: Code duplicated, block: B:15:0x001b  */
    /* JADX WARN: Code duplicated, block: B:16:0x0025  */
    /* JADX WARN: Code duplicated, block: B:18:0x0043  */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        OnSliderEventListener onSliderEventListener;
        OnSliderEventListener onSliderEventListener2;
        if (!isEnabled()) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            float x = motionEvent.getX() / getMeasuredWidth();
            float f = this.f4889b;
            this.d = e6.A(this.c, f, x, f);
            b();
            invalidate();
            onSliderEventListener = this.f4888a;
            if (onSliderEventListener != null) {
                onSliderEventListener.onSlide(this, a(this.d));
            }
        } else if (action == 1) {
            onSliderEventListener2 = this.f4888a;
            if (onSliderEventListener2 != null) {
                onSliderEventListener2.onFinished(this, a(this.d));
            }
        } else if (action == 2) {
            float x2 = motionEvent.getX() / getMeasuredWidth();
            float f2 = this.f4889b;
            this.d = e6.A(this.c, f2, x2, f2);
            b();
            invalidate();
            onSliderEventListener = this.f4888a;
            if (onSliderEventListener != null) {
                onSliderEventListener.onSlide(this, a(this.d));
            }
        } else if (action == 3) {
            onSliderEventListener2 = this.f4888a;
            if (onSliderEventListener2 != null) {
                onSliderEventListener2.onFinished(this, a(this.d));
            }
        }
        return true;
    }

    public void setCurrent(float f) {
        this.d = b(f);
        b();
        invalidate();
    }

    public void setLogarithmic(boolean z) {
        this.j = z;
    }

    public void setParameters(float f, float f2, float f3) {
        this.f4889b = f;
        this.c = f2;
        this.d = b(f3);
        b();
        a();
    }

    public void setSliderEventListener(OnSliderEventListener onSliderEventListener) {
        this.f4888a = onSliderEventListener;
    }

    private void b() {
        float f = this.d;
        float f2 = this.f4889b;
        if (f < f2) {
            this.d = f2;
        }
        float f3 = this.d;
        float f4 = this.c;
        if (f3 > f4) {
            this.d = f4;
        }
    }

    public Slider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4888a = null;
        this.f4889b = 0.0f;
        this.c = 1.0f;
        this.d = 0.5f;
        this.e = new Paint();
        this.f = 0;
        this.g = 1;
        this.h = -1;
        this.i = 10;
        this.j = false;
        a(attributeSet);
    }

    public Slider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4888a = null;
        this.f4889b = 0.0f;
        this.c = 1.0f;
        this.d = 0.5f;
        this.e = new Paint();
        this.f = 0;
        this.g = 1;
        this.h = -1;
        this.i = 10;
        this.j = false;
        a(attributeSet);
    }

    private void a() {
        float f = this.f4889b;
        this.k = f;
        float f2 = this.c;
        this.l = (float) Math.log(f);
        this.m = (((float) Math.log(this.c)) - this.l) / (f2 - this.k);
    }

    private float a(float f) {
        if (!this.j) {
            return f;
        }
        return (float) Math.exp(e6.A(this.d, this.k, this.m, this.l));
    }
}
