package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class DragHandle extends FrameLayout implements View.OnTouchListener {
    public static final int DRAG = 7;
    public static final int RESIZE_BOTTOM_LEFT = 5;
    public static final int RESIZE_BOTTOM_RIGHT = 6;
    public static final int RESIZE_TOP_LEFT = 3;
    public static final int RESIZE_TOP_RIGHT = 4;
    public static final int ROTATE = 8;
    public static final int SELECTION_BOTTOM_RIGHT = 2;
    public static final int SELECTION_TOP_LEFT = 1;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f4598a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f4599b;
    private boolean c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private DragHandleListener i;
    private int j;

    public DragHandle(Context context, int i, int i2) {
        super(context);
        this.c = false;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.i = null;
        this.j = i2;
        View.inflate(context, i, this);
        setOnTouchListener(this);
        this.f = (int) context.getResources().getDimension(R.dimen.sodk_editor_drag_slop);
    }

    public int getKind() {
        return this.j;
    }

    public Point getPosition() {
        return new Point(this.g, this.h);
    }

    public boolean isDragKind() {
        return this.j == 7;
    }

    public boolean isResizeKind() {
        int i = this.j;
        return i == 3 || i == 4 || i == 5 || i == 6;
    }

    public boolean isRotateKind() {
        return this.j == 8;
    }

    public boolean isSelectionKind() {
        int i = this.j;
        return i == 1 || i == 2;
    }

    public void moveTo(int i, int i2) {
        offsetLeftAndRight(i - this.g);
        offsetTopAndBottom(i2 - this.h);
        this.g = i;
        this.h = i2;
        invalidate();
    }

    public Point offsetCircleToEdge() {
        int i;
        int i2;
        ImageView imageView = (ImageView) findViewById(R.id.handle_image);
        if (imageView != null) {
            double intrinsicHeight = imageView.getDrawable().getIntrinsicHeight();
            double scaleY = imageView.getScaleY();
            Double.isNaN(intrinsicHeight);
            Double.isNaN(scaleY);
            Double.isNaN(intrinsicHeight);
            Double.isNaN(scaleY);
            Double.isNaN(intrinsicHeight);
            Double.isNaN(scaleY);
            i2 = (int) (((intrinsicHeight * scaleY) * 0.707d) / 2.0d);
            int i3 = this.j;
            i = (i3 == 1 || i3 == 3 || i3 == 5) ? -i2 : i2;
            if (i3 == 1 || i3 == 3 || i3 == 4) {
                i2 = -i2;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        return new Point(i, i2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        offsetLeftAndRight(this.g);
        offsetTopAndBottom(this.h);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        DragHandleListener dragHandleListener;
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            Point position = getPosition();
            this.f4598a = rawX - position.x;
            this.f4599b = rawY - position.y;
            this.d = rawX;
            this.e = rawY;
        } else if (action == 1) {
            this.c = false;
            DragHandleListener dragHandleListener2 = this.i;
            if (dragHandleListener2 != null) {
                dragHandleListener2.onEndDrag(this);
            }
        } else if (action == 2) {
            if (!this.c) {
                int iAbs = Math.abs(rawX - this.d);
                int iAbs2 = Math.abs(rawY - this.e);
                int i = this.f;
                if (iAbs > i || iAbs2 > i) {
                    this.c = true;
                    DragHandleListener dragHandleListener3 = this.i;
                    if (dragHandleListener3 != null) {
                        dragHandleListener3.onStartDrag(this);
                    }
                }
            }
            moveTo(rawX - this.f4598a, rawY - this.f4599b);
            if (this.c && (dragHandleListener = this.i) != null) {
                dragHandleListener.onDrag(this);
            }
        }
        return true;
    }

    public void setDragHandleListener(DragHandleListener dragHandleListener) {
        this.i = dragHandleListener;
    }

    public void show(boolean z) {
        int visibility = getVisibility();
        int i = z ? 0 : 8;
        setVisibility(i);
        if (i != visibility) {
            requestLayout();
        }
    }
}
