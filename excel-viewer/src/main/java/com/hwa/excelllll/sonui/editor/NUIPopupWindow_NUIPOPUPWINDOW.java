package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public class NUIPopupWindow extends PopupWindow {
    public NUIPopupWindow(Context context) {
        super(context);
        a();
    }

    private void a() {
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
    }

    public NUIPopupWindow(View view) {
        super(view);
        a();
    }

    public NUIPopupWindow(View view, int i, int i2) {
        super(view, i, i2);
        a();
    }
}
