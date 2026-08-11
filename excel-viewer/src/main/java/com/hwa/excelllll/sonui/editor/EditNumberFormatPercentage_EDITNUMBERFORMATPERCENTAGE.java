package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.d;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatPercentage {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String[] f4638a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final String[] f4639b;

    static {
        String strConcat = String.format("%d", 0).concat("%");
        String strConcat2 = String.format("%.1f", Double.valueOf(0.1d)).concat("%");
        String strConcat3 = String.format("%.2f", Double.valueOf(0.12d)).concat("%");
        String strConcat4 = String.format("%.3f", Double.valueOf(0.123d)).concat("%");
        String strConcat5 = String.format("%.4f", Double.valueOf(0.1234d)).concat("%");
        String strConcat6 = String.format("%.5f", Double.valueOf(0.12345d)).concat("%");
        String strConcat7 = String.format("%.6f", Double.valueOf(0.123456d)).concat("%");
        String strConcat8 = String.format("%.7f", Double.valueOf(0.1234567d)).concat("%");
        String strConcat9 = String.format("%.8f", Double.valueOf(0.12345678d)).concat("%");
        Double dValueOf = Double.valueOf(0.123456789d);
        f4638a = new String[]{strConcat, strConcat2, strConcat3, strConcat4, strConcat5, strConcat6, strConcat7, strConcat8, strConcat9, String.format("%.9f", dValueOf).concat("%"), String.format("%.10f", dValueOf)};
        f4639b = new String[]{"0%", "0.0%", "0.00%", "0.000%", "0.0000%", "0.00000%", "0.000000%", "0.0000000%", "0.00000000%", "0.000000000%", "0.0000000000%"};
    }

    public static void show(Context context, View view, final SODoc sODoc) {
        String selectedCellFormat = sODoc.getSelectedCellFormat();
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_percentage, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.wheel);
        c cVar = new c(context, f4638a);
        cVar.c = context.getResources().getColor(R.color.sodk_editor_wheel_item_text_color);
        wheelView.setViewAdapter(cVar);
        wheelView.setVisibleItems(5);
        wheelView.setCurrentItem(0);
        int i = 0;
        while (true) {
            String[] strArr = f4639b;
            if (i >= strArr.length) {
                wheelView.d(new d() { // from class: com.artifex.sonui.editor.EditNumberFormatPercentage.1
                    @Override // kankan.wheel.widget.d
                    public void b(WheelView wheelView2) {
                        sODoc.setSelectedCellFormat(EditNumberFormatPercentage.f4639b[wheelView2.getCurrentItem()]);
                    }

                    @Override // kankan.wheel.widget.d
                    public void a(WheelView wheelView2) {
                    }
                });
                NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
                nUIPopupWindow.setFocusable(true);
                nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatPercentage.2
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
