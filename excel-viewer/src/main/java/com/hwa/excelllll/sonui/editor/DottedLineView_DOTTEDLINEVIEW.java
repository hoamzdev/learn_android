package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class DottedLineView extends View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Paint f4596a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Path f4597b;
    private int c;
    private float[] d;

    public DottedLineView(Context context) {
        super(context);
        this.f4596a = new Paint();
        this.f4597b = new Path();
        a();
    }

    private void a() {
        setWillNotDraw(false);
        this.f4596a.setStyle(Paint.Style.STROKE);
        int iRound = Math.round((getContext().getResources().getDisplayMetrics().xdpi / 160.0f) * 6.0f);
        this.c = iRound;
        this.f4596a.setStrokeWidth(iRound);
        this.f4596a.setColor(-16777216);
        this.f4596a.setARGB(255, 0, 0, 0);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int i = 0;
        float f = 0.0f;
        while (true) {
            float[] fArr = this.d;
            if (i >= fArr.length) {
                int i2 = (int) f;
                this.f4597b.moveTo(0.0f, 0.0f);
                this.f4597b.lineTo(i2 * (measuredWidth / i2), 0.0f);
                canvas.drawPath(this.f4597b, this.f4596a);
                return;
            }
            f += fArr[i];
            i++;
        }
    }

    public void setPattern(float[] fArr) {
        this.d = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            this.d[i] = fArr[i] * this.c;
        }
        this.f4596a.setPathEffect(new DashPathEffect(this.d, 0.0f));
        invalidate();
    }

    public DottedLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4596a = new Paint();
        this.f4597b = new Path();
        a();
    }

    public DottedLineView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4596a = new Paint();
        this.f4597b = new Path();
        a();
    }
}
