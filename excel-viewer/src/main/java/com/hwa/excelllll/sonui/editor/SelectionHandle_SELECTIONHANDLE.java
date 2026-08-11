package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public class SelectionHandle extends View implements View.OnTouchListener {
    public static final int KIND_END = 2;
    public static final int KIND_START = 1;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f4768a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f4769b;
    private int c;
    private boolean d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private SelectionHandleListener k;

    public interface SelectionHandleListener {
        void onDrag(SelectionHandle selectionHandle);

        void onEndDrag(SelectionHandle selectionHandle);

        void onStartDrag(SelectionHandle selectionHandle);
    }

    public SelectionHandle(Context context) {
        super(context);
        this.f4768a = 0;
        this.d = false;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = false;
        a(context);
    }

    private void a(Context context) {
        setOnTouchListener(this);
        this.g = (int) context.getResources().getDimension(R.dimen.sodk_editor_drag_slop);
        setWillNotDraw(false);
        this.f4768a = Integer.parseInt((String) getTag());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.SelectionHandle.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                SelectionHandle selectionHandle = SelectionHandle.this;
                selectionHandle.offsetLeftAndRight(selectionHandle.h);
                SelectionHandle selectionHandle2 = SelectionHandle.this;
                selectionHandle2.offsetTopAndBottom(selectionHandle2.i);
                SelectionHandle.this.invalidate();
            }
        });
        moveTo(0, 0);
    }

    public int getKind() {
        return this.f4768a;
    }

    public Point getPoint() {
        int i;
        int i2;
        Point point = new Point();
        int i3 = getLayoutParams().width;
        int i4 = getLayoutParams().height;
        int i5 = this.f4768a;
        if (i5 == 1) {
            i = (i3 / 2) + this.h;
            i2 = this.i + i4;
        } else {
            if (i5 != 2) {
                return point;
            }
            i = (i3 / 2) + this.h;
            i2 = this.i;
        }
        point.set(i, i2);
        return point;
    }

    public Point getPosition() {
        return new Point(this.h, this.i);
    }

    public void hide() {
        setVisibility(8);
    }

    public void moveTo(int i, int i2) {
        offsetLeftAndRight(i - this.h);
        offsetTopAndBottom(i2 - this.i);
        this.h = i;
        this.i = i2;
        invalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.j) {
            Paint paint = new Paint();
            paint.setColor(-7829249);
            int width = getWidth();
            int height = getHeight();
            Path path = new Path();
            int i = this.f4768a;
            if (i != 1) {
                if (i == 2) {
                    float f = width / 4;
                    float f2 = height;
                    path.moveTo(f, f2);
                    float f3 = height / 3;
                    path.lineTo(f, f3);
                    path.lineTo(width / 2, 0.0f);
                    float f4 = (width * 3) / 4;
                    path.lineTo(f4, f3);
                    path.lineTo(f4, f2);
                    path.lineTo(f, f2);
                }
                canvas.drawPath(path, paint);
            }
            float f5 = width / 4;
            path.moveTo(f5, 0.0f);
            float f6 = (height * 2) / 3;
            path.lineTo(f5, f6);
            path.lineTo(width / 2, height);
            float f7 = (width * 3) / 4;
            path.lineTo(f7, f6);
            path.lineTo(f7, 0.0f);
            path.lineTo(f5, 0.0f);
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        SelectionHandleListener selectionHandleListener;
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        if (!rect.contains(rawX, rawY) && !this.d) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            Point position = getPosition();
            this.f4769b = rawX - position.x;
            this.c = rawY - position.y;
            this.e = rawX;
            this.f = rawY;
        } else if (action == 1) {
            this.d = false;
            SelectionHandleListener selectionHandleListener2 = this.k;
            if (selectionHandleListener2 != null) {
                selectionHandleListener2.onEndDrag(this);
            }
        } else if (action == 2) {
            if (!this.d) {
                int iAbs = Math.abs(rawX - this.e);
                int iAbs2 = Math.abs(rawY - this.f);
                int i = this.g;
                if (iAbs > i || iAbs2 > i) {
                    this.d = true;
                    SelectionHandleListener selectionHandleListener3 = this.k;
                    if (selectionHandleListener3 != null) {
                        selectionHandleListener3.onStartDrag(this);
                    }
                }
            }
            moveTo(rawX - this.f4769b, rawY - this.c);
            if (this.d && (selectionHandleListener = this.k) != null) {
                selectionHandleListener.onDrag(this);
            }
        }
        return true;
    }

    public void setMayDraw(boolean z) {
        if (z != this.j) {
            this.j = z;
            invalidate();
        }
    }

    public void setPoint(int i, int i2) {
        int i3;
        int i4 = getLayoutParams().width;
        int i5 = getLayoutParams().height;
        int i6 = this.f4768a;
        if (i6 == 1) {
            i3 = i - (i4 / 2);
            i2 -= i5;
        } else if (i6 != 2) {
            return;
        } else {
            i3 = i - (i4 / 2);
        }
        moveTo(i3, i2);
    }

    public void setSelectionHandleListener(SelectionHandleListener selectionHandleListener) {
        this.k = selectionHandleListener;
    }

    public void show() {
        setVisibility(0);
    }

    public SelectionHandle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4768a = 0;
        this.d = false;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = false;
        a(context);
    }

    public SelectionHandle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4768a = 0;
        this.d = false;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = false;
        a(context);
    }
}
