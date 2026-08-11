package com.hwa.excelllll.customviews.seekbar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.supportv1.p030v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import p000.C0013a7;
import p000.C1266c8;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class RangeSeekBar extends View {

    /* JADX INFO: renamed from: A */
    public float f48300A;

    /* JADX INFO: renamed from: B */
    public float f48301B;

    /* JADX INFO: renamed from: C */
    public int f48302C;

    /* JADX INFO: renamed from: D */
    public boolean f48303D;

    /* JADX INFO: renamed from: E */
    public float f48304E;

    /* JADX INFO: renamed from: F */
    public float f48305F;

    /* JADX INFO: renamed from: G */
    public boolean f48306G;

    /* JADX INFO: renamed from: H */
    public OnRangeChangedListener f48307H;

    /* JADX INFO: renamed from: a */
    public Bitmap f48308a;

    /* JADX INFO: renamed from: b */
    public Bitmap f48309b;

    /* JADX INFO: renamed from: c */
    public int f48310c;

    /* JADX INFO: renamed from: d */
    public int f48311d;

    /* JADX INFO: renamed from: e */
    public int f48312e;

    /* JADX INFO: renamed from: f */
    public int f48313f;

    /* JADX INFO: renamed from: g */
    public int f48314g;

    /* JADX INFO: renamed from: h */
    public int f48315h;

    /* JADX INFO: renamed from: i */
    public int f48316i;

    /* JADX INFO: renamed from: j */
    public int f48317j;

    /* JADX INFO: renamed from: k */
    public int f48318k;

    /* JADX INFO: renamed from: l */
    public int f48319l;

    /* JADX INFO: renamed from: m */
    public int f48320m;

    /* JADX INFO: renamed from: n */
    public int f48321n;

    /* JADX INFO: renamed from: o */
    public CharSequence[] f48322o;

    /* JADX INFO: renamed from: p */
    public float f48323p;

    /* JADX INFO: renamed from: q */
    public int f48324q;

    /* JADX INFO: renamed from: r */
    public int f48325r;

    /* JADX INFO: renamed from: s */
    public int f48326s;

    /* JADX INFO: renamed from: t */
    public int f48327t;

    /* JADX INFO: renamed from: u */
    public int f48328u;

    /* JADX INFO: renamed from: v */
    public int f48329v;

    /* JADX INFO: renamed from: w */
    public float f48330w;

    /* JADX INFO: renamed from: x */
    public int f48331x;

    /* JADX INFO: renamed from: y */
    public int f48332y;

    /* JADX INFO: renamed from: z */
    public float f48333z;

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static class Gravity {
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityDef {
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekBarModeDef {
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    @Retention(RetentionPolicy.SOURCE)
    public @interface TickMarkGravityDef {
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    @Retention(RetentionPolicy.SOURCE)
    public @interface TickMarkLayoutGravityDef {
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    @Retention(RetentionPolicy.SOURCE)
    public @interface TickMarkModeDef {
    }

    /* JADX INFO: renamed from: a */
    public final void m21862a() {
        if (this.f48308a == null) {
            this.f48308a = Utils.m21866a(getContext(), this.f48329v, this.f48328u, this.f48326s);
        }
        if (this.f48309b == null) {
            this.f48309b = Utils.m21866a(getContext(), this.f48329v, this.f48328u, this.f48327t);
        }
    }

    /* JADX INFO: renamed from: b */
    public final void m21863b(float f, float f2) {
        float fMin = Math.min(f, f2);
        float fMax = Math.max(fMin, f2);
        float f3 = fMax - fMin;
        float f4 = this.f48330w;
        if (f3 < f4) {
            if (fMin - this.f48304E > this.f48305F - fMax) {
                fMin = fMax - f4;
            } else {
                fMax = fMin + f4;
            }
        }
        float f5 = this.f48304E;
        if (fMin < f5) {
            throw new IllegalArgumentException("setProgress() min < (preset min - offsetValue) . #min:" + fMin + " #preset min:" + fMax);
        }
        if (fMax <= this.f48305F) {
            Math.abs(fMin - f5);
            throw null;
        }
        throw new IllegalArgumentException("setProgress() max > (preset max - offsetValue) . #max:" + fMax + " #preset max:" + fMax);
    }

    /* JADX INFO: renamed from: c */
    public final void m21864c(float f, float f2, float f3) {
        if (f2 <= f) {
            throw new IllegalArgumentException("setRange() max must be greater than min ! #max:" + f2 + " #min:" + f);
        }
        if (f3 < 0.0f) {
            C1266c8.m5163f("setRange() interval must be greater than zero ! #minInterval:", f3);
            return;
        }
        float f4 = f2 - f;
        if (f3 >= f4) {
            throw new IllegalArgumentException("setRange() interval must be less than (max - min) ! #minInterval:" + f3 + " #max - min:" + f4);
        }
        this.f48305F = f2;
        this.f48304E = f;
        this.f48330w = f3;
        if (this.f48314g == 2) {
            throw null;
        }
        invalidate();
    }

    /* JADX INFO: renamed from: d */
    public final boolean m21865d() {
        return this.f48302C >= 1 && this.f48300A > 0.0f && this.f48333z > 0.0f;
    }

    public int getGravity() {
        return this.f48331x;
    }

    public SeekBar getLeftSeekBar() {
        return null;
    }

    public float getMaxProgress() {
        return this.f48305F;
    }

    public float getMinInterval() {
        return this.f48330w;
    }

    public float getMinProgress() {
        return this.f48304E;
    }

    public int getProgressBottom() {
        return this.f48311d;
    }

    public int getProgressColor() {
        return this.f48324q;
    }

    public int getProgressDefaultColor() {
        return this.f48325r;
    }

    public int getProgressDefaultDrawableId() {
        return this.f48327t;
    }

    public int getProgressDrawableId() {
        return this.f48326s;
    }

    public int getProgressHeight() {
        return this.f48328u;
    }

    public int getProgressLeft() {
        return this.f48312e;
    }

    public int getProgressPaddingRight() {
        throw null;
    }

    public float getProgressRadius() {
        return this.f48323p;
    }

    public int getProgressRight() {
        return this.f48313f;
    }

    public int getProgressTop() {
        return this.f48310c;
    }

    public int getProgressWidth() {
        return this.f48329v;
    }

    public SeekBarState[] getRangeSeekBarState() {
        throw null;
    }

    public float getRawHeight() {
        if (this.f48314g == 1) {
            throw null;
        }
        throw null;
    }

    public SeekBar getRightSeekBar() {
        return null;
    }

    public int getSeekBarMode() {
        return this.f48314g;
    }

    public int getSteps() {
        return this.f48302C;
    }

    public List<Bitmap> getStepsBitmaps() {
        return null;
    }

    public int getStepsColor() {
        return this.f48332y;
    }

    public int getStepsDrawableId() {
        throw null;
    }

    public float getStepsHeight() {
        return this.f48300A;
    }

    public float getStepsRadius() {
        return this.f48301B;
    }

    public float getStepsWidth() {
        return this.f48333z;
    }

    public int getTickMarkGravity() {
        return this.f48318k;
    }

    public int getTickMarkInRangeTextColor() {
        return this.f48321n;
    }

    public int getTickMarkLayoutGravity() {
        return this.f48319l;
    }

    public int getTickMarkMode() {
        return this.f48315h;
    }

    public int getTickMarkRawHeight() {
        CharSequence[] charSequenceArr = this.f48322o;
        if (charSequenceArr == null || charSequenceArr.length <= 0) {
            return 0;
        }
        int i = this.f48316i;
        String strValueOf = String.valueOf(charSequenceArr[0]);
        float f = this.f48317j;
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.setTextSize(f);
        paint.getTextBounds(strValueOf, 0, strValueOf.length(), rect);
        paint.reset();
        return rect.height() + i + 3;
    }

    public CharSequence[] getTickMarkTextArray() {
        return this.f48322o;
    }

    public int getTickMarkTextColor() {
        return this.f48320m;
    }

    public int getTickMarkTextMargin() {
        return this.f48316i;
    }

    public int getTickMarkTextSize() {
        return this.f48317j;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CharSequence[] charSequenceArr = this.f48322o;
        if (charSequenceArr != null) {
            int length = this.f48329v / (charSequenceArr.length - 1);
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr2 = this.f48322o;
                if (i >= charSequenceArr2.length) {
                    break;
                }
                String string = charSequenceArr2[i].toString();
                if (!TextUtils.isEmpty(string)) {
                    string.length();
                    throw null;
                }
                i++;
            }
        }
        Bitmap bitmap = this.f48309b;
        if (bitmap == null || bitmap.isRecycled() || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            throw null;
        }
        canvas.drawBitmap(this.f48309b, (Rect) null, (RectF) null, (Paint) null);
        if (this.f48314g == 2) {
            getProgressTop();
            throw null;
        }
        getProgressTop();
        throw null;
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        float rawHeight;
        int iMakeMeasureSpec;
        int size = MeasureSpec.getSize(i2);
        int mode = MeasureSpec.getMode(i2);
        if (mode == 1073741824) {
            iMakeMeasureSpec = MeasureSpec.makeMeasureSpec(size, 1073741824);
        } else if (mode == Integer.MIN_VALUE && (getParent() instanceof ViewGroup) && size == -1) {
            iMakeMeasureSpec = MeasureSpec.makeMeasureSpec(((ViewGroup) getParent()).getMeasuredHeight(), LinearLayoutManager.INVALID_OFFSET);
        } else {
            if (this.f48331x != 2) {
                rawHeight = getRawHeight();
            } else {
                if (this.f48322o == null || this.f48319l != 1) {
                    getRawHeight();
                    throw null;
                }
                rawHeight = (getRawHeight() - getTickMarkRawHeight()) * 2.0f;
            }
            iMakeMeasureSpec = MeasureSpec.makeMeasureSpec((int) rawHeight, 1073741824);
        }
        super.onMeasure(i, iMakeMeasureSpec);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        try {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            m21864c(savedState.f48334a, savedState.f48335b, savedState.f48336c);
            m21863b(savedState.f48338e, savedState.f48339f);
            throw null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f48334a = this.f48304E;
        savedState.f48335b = this.f48305F;
        savedState.f48336c = this.f48330w;
        SeekBarState[] rangeSeekBarState = getRangeSeekBarState();
        rangeSeekBarState[0].getClass();
        savedState.f48338e = 0.0f;
        rangeSeekBarState[1].getClass();
        savedState.f48339f = 0.0f;
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int paddingBottom = (i2 - getPaddingBottom()) - getPaddingTop();
        if (i2 <= 0) {
            m21864c(this.f48304E, this.f48305F, this.f48330w);
            getProgressBottom();
            getProgressTop();
            getProgressLeft();
            throw null;
        }
        int i5 = this.f48331x;
        if (i5 == 0) {
            throw null;
        }
        if (i5 != 1) {
            int i6 = this.f48328u;
            int i7 = (paddingBottom - i6) / 2;
            this.f48310c = i7;
            this.f48311d = i7 + i6;
            throw null;
        }
        if (this.f48322o == null || this.f48319l != 1) {
            throw null;
        }
        int tickMarkRawHeight = paddingBottom - getTickMarkRawHeight();
        this.f48311d = tickMarkRawHeight;
        this.f48310c = tickMarkRawHeight - this.f48328u;
        throw null;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.f48306G) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            motionEvent.getX();
            motionEvent.getY();
            if (this.f48314g == 2) {
                throw null;
            }
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            OnRangeChangedListener onRangeChangedListener = this.f48307H;
            if (onRangeChangedListener == null) {
                throw null;
            }
            onRangeChangedListener.m21861a();
            throw null;
        }
        if (action == 1) {
            if (m21865d() && this.f48303D) {
                motionEvent.getX();
                new BigDecimal(0.0f / (1.0f / this.f48302C)).setScale(0, RoundingMode.HALF_UP).intValue();
                throw null;
            }
            if (this.f48314g == 2) {
                throw null;
            }
            throw null;
        }
        if (action == 2) {
            motionEvent.getX();
            if (this.f48314g != 2) {
                throw null;
            }
            throw null;
        }
        if (action != 3) {
            return super.onTouchEvent(motionEvent);
        }
        if (this.f48314g != 2) {
            throw null;
        }
        throw null;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.f48306G = z;
    }

    public void setGravity(int i) {
        this.f48331x = i;
    }

    public void setIndicatorText(String str) {
        throw null;
    }

    public void setIndicatorTextDecimalFormat(String str) {
        throw null;
    }

    public void setIndicatorTextStringFormat(String str) {
        throw null;
    }

    public void setOnRangeChangedListener(OnRangeChangedListener onRangeChangedListener) {
        this.f48307H = onRangeChangedListener;
    }

    public void setProgress(float f) {
        m21863b(f, this.f48305F);
        throw null;
    }

    public void setProgressBottom(int i) {
        this.f48311d = i;
    }

    public void setProgressColor(int i) {
        this.f48324q = i;
    }

    public void setProgressDefaultColor(int i) {
        this.f48325r = i;
    }

    public void setProgressDefaultDrawableId(int i) {
        this.f48327t = i;
        this.f48309b = null;
        m21862a();
    }

    public void setProgressDrawableId(int i) {
        this.f48326s = i;
        this.f48308a = null;
        m21862a();
    }

    public void setProgressHeight(int i) {
        this.f48328u = i;
    }

    public void setProgressLeft(int i) {
        this.f48312e = i;
    }

    public void setProgressRadius(float f) {
        this.f48323p = f;
    }

    public void setProgressRight(int i) {
        this.f48313f = i;
    }

    public void setProgressTop(int i) {
        this.f48310c = i;
    }

    public void setProgressWidth(int i) {
        this.f48329v = i;
    }

    public void setSeekBarMode(int i) {
        this.f48314g = i;
        throw null;
    }

    public void setSteps(int i) {
        this.f48302C = i;
    }

    public void setStepsAutoBonding(boolean z) {
        this.f48303D = z;
    }

    public void setStepsBitmaps(List<Bitmap> list) {
        if (list != null && !list.isEmpty() && list.size() > this.f48302C) {
            throw null;
        }
        throw new IllegalArgumentException("stepsBitmaps must > steps !");
    }

    public void setStepsColor(int i) {
        this.f48332y = i;
    }

    public void setStepsDrawable(List<Integer> list) {
        if (list == null || list.isEmpty() || list.size() <= this.f48302C) {
            C0013a7.m31z("stepsDrawableIds must > steps !");
            return;
        }
        if (!m21865d()) {
            C0013a7.m31z("stepsWidth must > 0, stepsHeight must > 0,steps must > 0 First!!");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(Utils.m21866a(getContext(), (int) this.f48333z, (int) this.f48300A, list.get(i).intValue()));
        }
        setStepsBitmaps(arrayList);
    }

    public void setStepsDrawableId(int i) {
        throw null;
    }

    public void setStepsHeight(float f) {
        this.f48300A = f;
    }

    public void setStepsRadius(float f) {
        this.f48301B = f;
    }

    public void setStepsWidth(float f) {
        this.f48333z = f;
    }

    public void setTickMarkGravity(int i) {
        this.f48318k = i;
    }

    public void setTickMarkInRangeTextColor(int i) {
        this.f48321n = i;
    }

    public void setTickMarkLayoutGravity(int i) {
        this.f48319l = i;
    }

    public void setTickMarkMode(int i) {
        this.f48315h = i;
    }

    public void setTickMarkTextArray(CharSequence[] charSequenceArr) {
        this.f48322o = charSequenceArr;
    }

    public void setTickMarkTextColor(int i) {
        this.f48320m = i;
    }

    public void setTickMarkTextMargin(int i) {
        this.f48316i = i;
    }

    public void setTickMarkTextSize(int i) {
        this.f48317j = i;
    }

    public void setTypeface(Typeface typeface) {
        throw null;
    }

    public void setEnableThumbOverlap(boolean z) {
    }
}
