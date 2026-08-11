package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
public class ToolbarButton extends Button {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private float f4890a;

    public ToolbarButton(Context context) {
        super(context);
        a();
    }

    private void a() {
        this.f4890a = getTextSize();
    }

    public static int getMaxWidth(ToolbarButton[] toolbarButtonArr) {
        int length = toolbarButtonArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            ToolbarButton toolbarButton = toolbarButtonArr[i2];
            if (toolbarButton != null) {
                toolbarButton.measure(-2, -2);
                int measuredWidth = toolbarButtonArr[i2].getMeasuredWidth();
                if (measuredWidth > i) {
                    i = measuredWidth;
                }
            }
        }
        return i;
    }

    public static void setAllSameSize(ToolbarButton[] toolbarButtonArr) {
        int measuredWidth;
        String str;
        ToolbarButton toolbarButton;
        int length = toolbarButtonArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            ToolbarButton toolbarButton2 = toolbarButtonArr[i2];
            if (toolbarButton2 != null) {
                String[] strArrSplit = toolbarButton2.getText().toString().split(" ");
                if (strArrSplit.length == 2) {
                    str = strArrSplit[0] + "\n" + strArrSplit[1];
                    toolbarButton = toolbarButtonArr[i2];
                } else if (strArrSplit.length == 3) {
                    str = strArrSplit[0] + " " + strArrSplit[1] + "\n" + strArrSplit[2];
                    toolbarButton = toolbarButtonArr[i2];
                }
                toolbarButton.setText(str);
                i++;
            }
        }
        if (i > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                ToolbarButton toolbarButton3 = toolbarButtonArr[i3];
                if (toolbarButton3 != null) {
                    String string = toolbarButton3.getText().toString();
                    if (!string.contains("\n")) {
                        toolbarButtonArr[i3].setText(string.concat("\n"));
                    }
                }
            }
        }
        int i4 = 0;
        for (ToolbarButton toolbarButton4 : toolbarButtonArr) {
            if (toolbarButton4 != null && (measuredWidth = toolbarButton4.getMeasuredWidth()) > i4) {
                i4 = measuredWidth;
            }
        }
        setAllSameSize(i4, toolbarButtonArr);
    }

    public void hideText() {
        setTextSize(0.0f);
    }

    public void setDrawableColor(int i) {
        for (Drawable drawable : getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setTint(i);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setAlpha(z ? 1.0f : 0.4f);
    }

    public void setImageResource(int i) {
        setCompoundDrawablesWithIntrinsicBounds((Drawable) null, getContext().getDrawable(i), (Drawable) null, (Drawable) null);
    }

    public void showText() {
        setTextSize(0, this.f4890a);
    }

    public ToolbarButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public ToolbarButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    public static void setAllSameSize(int i, ToolbarButton[] toolbarButtonArr) {
        for (ToolbarButton toolbarButton : toolbarButtonArr) {
            if (toolbarButton != null) {
                toolbarButton.setWidth(i);
            }
        }
    }
}
