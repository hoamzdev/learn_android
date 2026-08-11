package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.d;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatFraction {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String[] f4632a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final String[] f4633b = {"# ?/?", "# ??/??", "#\\ ???/???", "#\\ ?/2", "#\\ ?/4", "#\\ ?/8", "#\\ ?/16", "#\\ ?/10", "#\\ ?/100"};

    public static void show(Context context, View view, final SODoc sODoc) {
        f4632a = new String[]{context.getString(R.string.sodk_editor_up_to_one_digit), context.getString(R.string.sodk_editor_up_to_two_digits), context.getString(R.string.sodk_editor_up_to_three_digits), context.getString(R.string.sodk_editor_as_halves), context.getString(R.string.sodk_editor_as_quarters), context.getString(R.string.sodk_editor_as_eighths), context.getString(R.string.sodk_editor_as_sixteenths), context.getString(R.string.sodk_editor_as_tenths), context.getString(R.string.sodk_editor_as_hundredths)};
        String selectedCellFormat = sODoc.getSelectedCellFormat();
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_fractions, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.wheel);
        c cVar = new c(context, f4632a);
        cVar.c = context.getResources().getColor(R.color.sodk_editor_wheel_item_text_color);
        wheelView.setViewAdapter(cVar);
        wheelView.setVisibleItems(5);
        wheelView.setCurrentItem(0);
        int i = 0;
        while (true) {
            String[] strArr = f4633b;
            if (i >= strArr.length) {
                wheelView.d(new d() { // from class: com.artifex.sonui.editor.EditNumberFormatFraction.1
                    @Override // kankan.wheel.widget.d
                    public void b(WheelView wheelView2) {
                        sODoc.setSelectedCellFormat(EditNumberFormatFraction.f4633b[wheelView2.getCurrentItem()]);
                    }

                    @Override // kankan.wheel.widget.d
                    public void a(WheelView wheelView2) {
                    }
                });
                NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
                nUIPopupWindow.setFocusable(true);
                nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatFraction.2
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        wheelView.b();
                    }
                });
                nUIPopupWindow.showAsDropDown(view, 30, 30);
                return;
            }
            if (strArr[i].equals(selectedCellFormat)) {
                wheelView.setCurrentItem(0);
            }
            i++;
        }
    }
}
