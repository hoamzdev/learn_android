package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;

/* JADX INFO: loaded from: classes.dex */
public class ColorDialog implements View.OnTouchListener, PopupWindow.OnDismissListener {
    public static final int BG_COLORS = 2;
    public static final int FG_COLORS = 1;
    private static ColorDialog f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f4524a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final View f4525b;
    private final ColorChangedListener c;
    private final int d;
    private final SODoc e;
    private NUIPopupWindow g;
    private int[] j;
    private final String[] h = {"#000000", "#FFFFFF", "#D8D8D8", "#808080", "#EEECE1", "#1F497D", "#0070C0", "#C0504D", "#9BBB59", "#8064A2", "#4BACC6", "#F79646", "#FF0000", "#FFFF00", "#DBE5F1", "#F2DCDB", "#EBF1DD", "#00B050"};
    private boolean i = true;
    private final Point k = new Point();

    public ColorDialog(int i, Context context, SODoc sODoc, View view, ColorChangedListener colorChangedListener) {
        this.f4524a = context;
        this.f4525b = view;
        this.c = colorChangedListener;
        this.d = i;
        this.e = sODoc;
    }

    private void a() {
        this.g.dismiss();
        f = null;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        a();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.j = new int[2];
            this.g.getContentView().getLocationOnScreen(this.j);
            this.k.set((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        } else if (action == 2) {
            int i = this.k.x;
            int rawX = (int) motionEvent.getRawX();
            int i2 = this.k.y;
            int rawY = (int) motionEvent.getRawY();
            NUIPopupWindow nUIPopupWindow = this.g;
            int[] iArr = this.j;
            nUIPopupWindow.update(iArr[0] - (i - rawX), iArr[1] - (i2 - rawY), -1, -1, true);
        }
        return true;
    }

    public void setShowTitle(boolean z) {
        this.i = z;
    }

    public void show() {
        f = this;
        View viewInflate = LayoutInflater.from(this.f4524a).inflate(R.layout.sodk_editor_colors, (ViewGroup) null);
        SOTextView sOTextView = (SOTextView) viewInflate.findViewById(R.id.color_dialog_title);
        if (this.i) {
            sOTextView.setText(this.f4524a.getString(this.d == 2 ? R.string.sodk_editor_background : R.string.sodk_editor_color));
        } else {
            sOTextView.setVisibility(8);
        }
        String[] bgColorList = this.d == 2 ? this.e.getBgColorList() : this.h;
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.fontcolors_row1);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.fontcolors_row2);
        LinearLayout linearLayout3 = (LinearLayout) viewInflate.findViewById(R.id.fontcolors_row3);
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            LinearLayout linearLayout4 = new LinearLayout[]{linearLayout, linearLayout2, linearLayout3}[i2];
            int childCount = linearLayout4.getChildCount();
            int i3 = 0;
            while (i3 < childCount) {
                Button button = (Button) linearLayout4.getChildAt(i3);
                int i4 = i + 1;
                if (i4 <= bgColorList.length) {
                    button.setVisibility(0);
                    button.setBackgroundColor(Color.parseColor(bgColorList[i]));
                    button.setTag(bgColorList[i]);
                    button.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.ColorDialog.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ColorDialog.this.c.onColorChanged((String) view.getTag());
                            ColorDialog.this.onDismiss();
                        }
                    });
                } else {
                    button.setVisibility(8);
                }
                i3++;
                i = i4;
            }
        }
        Button button2 = (Button) viewInflate.findViewById(R.id.transparent_color_button);
        if (this.d == 2) {
            button2.setVisibility(0);
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.ColorDialog.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ColorDialog.this.c.onColorChanged((String) view.getTag());
                    ColorDialog.this.onDismiss();
                }
            });
        } else {
            button2.setVisibility(8);
        }
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(this.f4524a);
        this.g = nUIPopupWindow;
        nUIPopupWindow.setContentView(viewInflate);
        this.g.setClippingEnabled(false);
        viewInflate.setOnTouchListener(this);
        this.g.setOnDismissListener(this);
        this.g.setFocusable(true);
        viewInflate.measure(0, 0);
        this.g.setWidth(viewInflate.getMeasuredWidth());
        this.g.setHeight(viewInflate.getMeasuredHeight());
        this.g.showAtLocation(this.f4525b, 51, 50, 50);
    }
}
