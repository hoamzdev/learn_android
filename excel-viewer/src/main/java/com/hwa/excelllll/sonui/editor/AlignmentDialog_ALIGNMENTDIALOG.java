package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class AlignmentDialog implements View.OnClickListener, View.OnTouchListener, PopupWindow.OnDismissListener {
    private static AlignmentDialog e;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f4504a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final View f4505b;
    private final SODoc c;
    private NUIPopupWindow f;
    private int[] g;
    private final ImageButton[][] d = (ImageButton[][]) Array.newInstance((Class<?>) ImageButton.class, 3, 3);
    private final Point h = new Point();

    public AlignmentDialog(Context context, SODoc sODoc, View view) {
        this.f4504a = context;
        this.f4505b = view;
        this.c = sODoc;
    }

    private void a() {
        int selectionAlignment = this.c.getSelectionAlignment();
        int selectionAlignmentV = this.c.getSelectionAlignmentV();
        int i = 0;
        while (i < 3) {
            int i2 = 0;
            while (i2 < 3) {
                this.d[i][i2].setSelected(i2 == selectionAlignment && i == selectionAlignmentV);
                i2++;
            }
            i++;
        }
    }

    private void b() {
        this.f.dismiss();
        e = null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        String[] strArrSplit = ((String) view.getTag()).split(",");
        int i = Integer.parseInt(strArrSplit[0]);
        int i2 = Integer.parseInt(strArrSplit[1]);
        if (i2 == 0) {
            this.c.setSelectionAlignment(0);
        } else if (i2 == 1) {
            this.c.setSelectionAlignment(1);
        } else if (i2 == 2) {
            this.c.setSelectionAlignment(2);
        }
        if (i == 0) {
            this.c.setSelectionAlignmentV(0);
        } else if (i == 1) {
            this.c.setSelectionAlignmentV(1);
        } else if (i == 2) {
            this.c.setSelectionAlignmentV(2);
        }
        a();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        b();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.g = new int[2];
            this.f.getContentView().getLocationOnScreen(this.g);
            this.h.set((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        } else if (action == 2) {
            int i = this.h.x;
            int rawX = (int) motionEvent.getRawX();
            int i2 = this.h.y;
            int rawY = (int) motionEvent.getRawY();
            NUIPopupWindow nUIPopupWindow = this.f;
            int[] iArr = this.g;
            nUIPopupWindow.update(iArr[0] - (i - rawX), iArr[1] - (i2 - rawY), -1, -1, true);
        }
        return true;
    }

    public void show() {
        e = this;
        View viewInflate = LayoutInflater.from(this.f4504a).inflate(R.layout.sodk_editor_alignment_dialog, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.row1);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.row2);
        LinearLayout linearLayout3 = (LinearLayout) viewInflate.findViewById(R.id.row3);
        for (int i = 0; i < 3; i++) {
            LinearLayout linearLayout4 = new LinearLayout[]{linearLayout, linearLayout2, linearLayout3}[i];
            int childCount = linearLayout4.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ImageButton imageButton = (ImageButton) linearLayout4.getChildAt(i2);
                this.d[i][i2] = imageButton;
                imageButton.setTag("" + i + "," + i2);
                imageButton.setOnClickListener(this);
            }
        }
        a();
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        this.f = nUIPopupWindow;
        nUIPopupWindow.setFocusable(true);
        viewInflate.measure(0, 0);
        Rect rect = new Rect();
        this.f4505b.getGlobalVisibleRect(rect);
        this.f.showAtLocation(this.f4505b, 51, rect.left, rect.bottom);
        this.f.setClippingEnabled(false);
        viewInflate.setOnTouchListener(this);
        this.f.setOnDismissListener(this);
    }
}
