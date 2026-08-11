package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import defpackage.h;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.apps.d;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatNumber {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String[] f4636a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final String[] f4637b;
    private static final String[] c;
    private static final String[] d;
    private static WheelView e;
    private static WheelView f;
    private static CheckBox g;
    private static CheckBox h;
    private static SODoc i;

    static {
        Double dValueOf = Double.valueOf(-1234.1d);
        String str = String.format("%.2f", dValueOf);
        Double dValueOf2 = Double.valueOf(1234.1d);
        f4636a = new String[]{str, String.format("%.2f (red)", dValueOf2), String.format("%.2f (red)", dValueOf), String.format("(%.2f)", dValueOf2), String.format("(%.2f) (red)", dValueOf2)};
        f4637b = new String[]{"DEC", "DEC;[Red]DEC", "DEC;[Red]\\-DEC", "DEC_);(DEC)", "DEC_);[Red](DEC)"};
        String str2 = String.format("%d", 0);
        String str3 = String.format("%.1f", Double.valueOf(0.1d));
        String str4 = String.format("%.2f", Double.valueOf(0.12d));
        String str5 = String.format("%.3f", Double.valueOf(0.123d));
        String str6 = String.format("%.4f", Double.valueOf(0.1234d));
        String str7 = String.format("%.5f", Double.valueOf(0.12345d));
        String str8 = String.format("%.6f", Double.valueOf(0.123456d));
        String str9 = String.format("%.7f", Double.valueOf(0.1234567d));
        String str10 = String.format("%.8f", Double.valueOf(0.12345678d));
        Double dValueOf3 = Double.valueOf(0.123456789d);
        c = new String[]{str2, str3, str4, str5, str6, str7, str8, str9, str10, String.format("%.9f", dValueOf3), String.format("%.10f", dValueOf3)};
        d = new String[]{"0", "0.0", "0.00", "0.000", "0.0000", "0.00000", "0.000000", "0.0000000", "0.00000000", "0.000000000", "0.0000000000"};
    }

    private static void c() {
        String selectedCellFormat = i.getSelectedCellFormat();
        g.setChecked(selectedCellFormat.contains("#,##"));
        String strReplace = selectedCellFormat.replace("#,##", "");
        h.setChecked(strReplace.contains("E+00"));
        String strReplace2 = strReplace.replace("E+00", "");
        for (int i2 = 0; i2 < d.length; i2++) {
            int i3 = 0;
            while (true) {
                String[] strArr = f4637b;
                if (i3 < strArr.length) {
                    if (strArr[i3].replace("DEC", d[i2]).equals(strReplace2)) {
                        e.setCurrentItem(i3);
                        f.setCurrentItem(i2);
                        return;
                    }
                    i3++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d() {
        String strD = d[f.getCurrentItem()];
        if (g.isChecked()) {
            strD = h.D(strD, "#,##");
        }
        if (h.isChecked()) {
            strD = h.D(strD, "E+00");
        }
        i.setSelectedCellFormat(f4637b[e.getCurrentItem()].replace("DEC", strD));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e() {
        e.b();
        f.b();
        e = null;
        f = null;
        g = null;
        h = null;
        i = null;
    }

    public static void show(Context context, View view, SODoc sODoc) {
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_number, null);
        i = sODoc;
        e = (WheelView) viewInflate.findViewById(R.id.left_wheel);
        d dVar = new d(context, f4636a);
        Resources resources = context.getResources();
        int i2 = R.color.sodk_editor_wheel_item_text_color;
        dVar.c = resources.getColor(i2);
        e.setViewAdapter(dVar);
        e.setVisibleItems(5);
        f = (WheelView) viewInflate.findViewById(R.id.right_wheel);
        c cVar = new c(context, c);
        cVar.c = context.getResources().getColor(i2);
        f.setViewAdapter(cVar);
        f.setVisibleItems(5);
        g = (CheckBox) viewInflate.findViewById(R.id.thousand_sep_checkbox);
        h = (CheckBox) viewInflate.findViewById(R.id.scientific_checkbox);
        c();
        f.d(new kankan.wheel.widget.d() { // from class: com.artifex.sonui.editor.EditNumberFormatNumber.1
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView) {
                EditNumberFormatNumber.d();
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView) {
            }
        });
        e.d(new kankan.wheel.widget.d() { // from class: com.artifex.sonui.editor.EditNumberFormatNumber.2
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView) {
                EditNumberFormatNumber.d();
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView) {
            }
        });
        h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.artifex.sonui.editor.EditNumberFormatNumber.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditNumberFormatNumber.d();
            }
        });
        g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.artifex.sonui.editor.EditNumberFormatNumber.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditNumberFormatNumber.d();
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatNumber.5
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                EditNumberFormatNumber.e();
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }
}
