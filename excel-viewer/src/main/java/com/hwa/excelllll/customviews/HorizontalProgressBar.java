package com.hwa.excelllll.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.xlsxreader.excelreader.R;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class HorizontalProgressBar extends View {

    /* JADX INFO: renamed from: a */
    public int f48289a;

    /* JADX INFO: renamed from: b */
    public int f48290b;

    /* JADX INFO: renamed from: c */
    public boolean f48291c;

    /* JADX INFO: renamed from: d */
    public int f48292d;

    /* JADX INFO: renamed from: e */
    public int f48293e;

    /* JADX INFO: renamed from: f */
    public final RectF f48294f;

    /* JADX INFO: renamed from: g */
    public final Paint f48295g;

    /* JADX INFO: renamed from: h */
    public final Path f48296h;

    public HorizontalProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f48289a = 100;
        this.f48290b = 0;
        this.f48291c = false;
        this.f48294f = new RectF();
        this.f48296h = new Path();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.hpb_colorBackground, R.attr.hpb_colorProgress, R.attr.hpb_progress, R.attr.hpb_useRoundRect}, 0, 0);
            try {
                this.f48292d = typedArrayObtainStyledAttributes.getColor(0, 1683075321);
                this.f48293e = typedArrayObtainStyledAttributes.getColor(1, -12942662);
                this.f48290b = typedArrayObtainStyledAttributes.getInt(2, 0);
                this.f48291c = typedArrayObtainStyledAttributes.getBoolean(3, false);
                typedArrayObtainStyledAttributes.recycle();
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            this.f48292d = 1683075321;
            this.f48293e = -12942662;
        }
        Paint paint = new Paint(1);
        this.f48295g = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    public int getProgress() {
        return this.f48290b;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float f = width;
        int i = (int) ((this.f48290b / this.f48289a) * f);
        boolean z = this.f48291c;
        Paint paint = this.f48295g;
        if (!z) {
            paint.setColor(this.f48292d);
            float f2 = height;
            canvas.drawRect(0.0f, 0.0f, f, f2, paint);
            paint.setColor(this.f48293e);
            canvas.drawRect(0.0f, 0.0f, (this.f48290b / this.f48289a) * f, f2, paint);
            return;
        }
        paint.setColor(this.f48292d);
        RectF rectF = this.f48294f;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = f;
        float f3 = height;
        rectF.bottom = f3;
        float f4 = height / 2;
        canvas.drawRoundRect(rectF, f4, f4, paint);
        Path.Direction direction = Path.Direction.CW;
        Path path = this.f48296h;
        path.addRect(0.0f, 0.0f, i, f3, direction);
        canvas.clipPath(path);
        paint.setColor(this.f48293e);
        canvas.drawRoundRect(rectF, f4, f4, paint);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.f48292d = i;
    }

    public void setForegroundColor(int i) {
        this.f48293e = i;
    }

    public void setMax(int i) {
        if (this.f48289a != i) {
            this.f48289a = Math.max(1, i);
            invalidate();
        }
    }

    public void setProgress(int i) {
        if (this.f48290b != i) {
            this.f48290b = Math.min(Math.max(0, i), this.f48289a);
            invalidate();
        }
    }

    public void setUseRoundRect(boolean z) {
        this.f48291c = z;
    }
}
