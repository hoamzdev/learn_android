package com.hwa.excelllll.excelsoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.hwa.excelllll.R;

@SuppressLint("ViewConstructor")
public class DragHandle extends FrameLayout implements View.OnTouchListener {
    public static final int DRAG = 7;
    public static final int RESIZE_BOTTOM_LEFT = 5;
    public static final int RESIZE_BOTTOM_RIGHT = 6;
    public static final int RESIZE_TOP_LEFT = 3;
    public static final int RESIZE_TOP_RIGHT = 4;
    public static final int ROTATE = 8;
    public static final int SELECTION_BOTTOM_RIGHT = 2;
    public static final int SELECTION_TOP_LEFT = 1;
    private int touchOffsetX;
    private int touchOffsetY;
    private boolean dragging;
    private int touchDownX;
    private int touchDownY;
    private int dragSlop;
    private int positionX;
    private int positionY;
    private DragHandleListener dragListener;
    private int handleKind;

    public DragHandle(Context context, int layoutResourceId, int handleKind) {
        super(context);
        this.dragging = false;
        this.dragSlop = 0;
        this.positionX = 0;
        this.positionY = 0;
        this.dragListener = null;
        this.handleKind = handleKind;
        View.inflate(context, layoutResourceId, this);
        setOnTouchListener(this);
        this.dragSlop = (int) context.getResources().getDimension(R.dimen.sodk_editor_drag_slop);
    }

    public int getKind() {
        return this.handleKind;
    }

    public Point getPosition() {
        return new Point(this.positionX, this.positionY);
    }

    public boolean isDragKind() {
        return this.handleKind == DRAG;
    }

    public boolean isResizeKind() {
        int kind = this.handleKind;
        return kind == RESIZE_TOP_LEFT || kind == RESIZE_TOP_RIGHT || kind == RESIZE_BOTTOM_LEFT || kind == RESIZE_BOTTOM_RIGHT;
    }

    public boolean isRotateKind() {
        return this.handleKind == ROTATE;
    }

    public boolean isSelectionKind() {
        int kind = this.handleKind;
        return kind == SELECTION_TOP_LEFT || kind == SELECTION_BOTTOM_RIGHT;
    }

    public void moveTo(int x, int y) {
        offsetLeftAndRight(x - this.positionX);
        offsetTopAndBottom(y - this.positionY);
        this.positionX = x;
        this.positionY = y;
        invalidate();
    }

    public Point offsetCircleToEdge() {
        int offsetX;
        int offsetY;
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
            offsetY = (int) (((intrinsicHeight * scaleY) * 0.707d) / 2.0d);
            int kind = this.handleKind;
            offsetX = (kind == SELECTION_TOP_LEFT || kind == RESIZE_TOP_LEFT || kind == RESIZE_BOTTOM_LEFT) ? -offsetY : offsetY;
            if (kind == SELECTION_TOP_LEFT || kind == RESIZE_TOP_LEFT || kind == RESIZE_TOP_RIGHT) {
                offsetY = -offsetY;
            }
        } else {
            offsetX = 0;
            offsetY = 0;
        }
        return new Point(offsetX, offsetY);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        offsetLeftAndRight(this.positionX);
        offsetTopAndBottom(this.positionY);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        DragHandleListener dragHandleListener;
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            Point position = getPosition();
            this.touchOffsetX = rawX - position.x;
            this.touchOffsetY = rawY - position.y;
            this.touchDownX = rawX;
            this.touchDownY = rawY;
        } else if (action == 1) {
            this.dragging = false;
            DragHandleListener dragHandleListener2 = this.dragListener;
            if (dragHandleListener2 != null) {
                dragHandleListener2.onEndDrag(this);
            }
        } else if (action == 2) {
            if (!this.dragging) {
                int distanceX = Math.abs(rawX - this.touchDownX);
                int distanceY = Math.abs(rawY - this.touchDownY);
                int threshold = this.dragSlop;
                if (distanceX > threshold || distanceY > threshold) {
                    this.dragging = true;
                    DragHandleListener dragHandleListener3 = this.dragListener;
                    if (dragHandleListener3 != null) {
                        dragHandleListener3.onStartDrag(this);
                    }
                }
            }
            moveTo(rawX - this.touchOffsetX, rawY - this.touchOffsetY);
            if (this.dragging && (dragHandleListener = this.dragListener) != null) {
                dragHandleListener.onDrag(this);
            }
        }
        return true;
    }

    public void setDragHandleListener(DragHandleListener dragHandleListener) {
        this.dragListener = dragHandleListener;
    }

    public void show(boolean visible) {
        int visibility = getVisibility();
        int newVisibility = visible ? VISIBLE : GONE;
        setVisibility(newVisibility);
        if (newVisibility != visibility) {
            requestLayout();
        }
    }
}
