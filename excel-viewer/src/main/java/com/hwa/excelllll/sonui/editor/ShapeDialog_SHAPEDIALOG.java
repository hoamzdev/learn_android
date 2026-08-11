package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public class ShapeDialog implements View.OnTouchListener, PopupWindow.OnDismissListener {
    private static ShapeDialog c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f4771a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final View f4772b;
    private NUIPopupWindow d;
    private final onSelectShapeListener e;
    private int[] f;
    private final Point g = new Point();
    private final Shape[] h = {new Shape("diamond", "Diamond", ""), new Shape("speech1", "WedgeEllipseCallout", ""), new Shape("speech2", "WedgeRectCallout", ""), new Shape("pentagon", "Pentagon", ""), new Shape("star", "Star", ""), new Shape("circle", "Ellipse", ""), new Shape("triangle1", "Triangle", ""), new Shape("triangle2", "RtTriangle", ""), new Shape("arrow1", "Arrow", ""), new Shape("arrow2", "LeftRightArrow", ""), new Shape("text", "TextBox", "fill-color:transparent"), new Shape("line", "Line", ""), new Shape("arrow-line", "Line", "end-decoration:\"arrow\""), new Shape("square", "Rect", ""), new Shape("rounded-square", "RoundRect", "")};

    public class Shape {
        public final String name;
        public final String properties;
        public final String shape;

        public Shape(String str, String str2, String str3) {
            this.name = str;
            this.shape = str2;
            this.properties = str3;
        }
    }

    public interface onSelectShapeListener {
        void onSelectShape(Shape shape);
    }

    public ShapeDialog(Context context, View view, onSelectShapeListener onselectshapelistener) {
        this.f4771a = context;
        this.f4772b = view;
        this.e = onselectshapelistener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.d.dismiss();
        c = null;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        a();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f = new int[2];
            this.d.getContentView().getLocationOnScreen(this.f);
            this.g.set((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        } else if (action == 2) {
            int i = this.g.x;
            int rawX = (int) motionEvent.getRawX();
            int i2 = this.g.y;
            int rawY = (int) motionEvent.getRawY();
            NUIPopupWindow nUIPopupWindow = this.d;
            int[] iArr = this.f;
            nUIPopupWindow.update(iArr[0] - (i - rawX), iArr[1] - (i2 - rawY), -1, -1, true);
        }
        return true;
    }

    public void show() {
        c = this;
        View viewInflate = LayoutInflater.from(this.f4771a).inflate(R.layout.sodk_editor_shape_dialog, (ViewGroup) null);
        ((SOTextView) viewInflate.findViewById(R.id.shape_dialog_title)).setText(this.f4771a.getString(R.string.sodk_editor_shape_upper));
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.row1);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.row2);
        LinearLayout linearLayout3 = (LinearLayout) viewInflate.findViewById(R.id.row3);
        for (int i = 0; i < 3; i++) {
            LinearLayout linearLayout4 = new LinearLayout[]{linearLayout, linearLayout2, linearLayout3}[i];
            int childCount = linearLayout4.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ((ImageButton) linearLayout4.getChildAt(i2)).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.ShapeDialog.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        String str = (String) view.getTag();
                        int length = ShapeDialog.this.h.length;
                        for (int i3 = 0; i3 < length; i3++) {
                            if (ShapeDialog.this.h[i3].name.equalsIgnoreCase(str)) {
                                ShapeDialog.this.e.onSelectShape(ShapeDialog.this.h[i3]);
                                ShapeDialog.this.a();
                                return;
                            }
                        }
                    }
                });
            }
        }
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(this.f4771a);
        this.d = nUIPopupWindow;
        nUIPopupWindow.setContentView(viewInflate);
        this.d.setClippingEnabled(false);
        viewInflate.setOnTouchListener(this);
        this.d.setOnDismissListener(this);
        this.d.setFocusable(true);
        viewInflate.measure(0, 0);
        this.d.setWidth(viewInflate.getMeasuredWidth());
        this.d.setHeight(viewInflate.getMeasuredHeight());
        this.d.showAtLocation(this.f4772b, 51, 50, 50);
    }
}
