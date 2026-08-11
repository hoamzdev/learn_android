package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import com.artifex.mupdf.fitz.Document;
import com.artifex.solib.SODoc;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.d;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatAccounting {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String[] f4600a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static String[] f4601b;

    private static void a(Context context) {
        String strC = com.artifex.solib.a.c(context, "currencies.json");
        if (strC == null) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONObject(strC).getJSONArray("currencies");
            f4600a = new String[jSONArray.length()];
            f4601b = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("description");
                String string2 = jSONObject.getString(Document.META_FORMAT);
                String string3 = jSONObject.getString("token");
                if (string3 != null && !string3.isEmpty()) {
                    int identifier = context.getResources().getIdentifier(string3, "string", context.getPackageName());
                    if (identifier != 0) {
                        string = context.getString(identifier);
                    }
                }
                f4600a[i] = string;
                f4601b[i] = string2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void b(SODoc sODoc, WheelView wheelView, CheckBox checkBox) {
        String selectedCellFormat = sODoc.getSelectedCellFormat();
        checkBox.setChecked(selectedCellFormat.contains("#,##0.00"));
        String strReplace = selectedCellFormat.replace("#,##0.00", "#,##0");
        int i = 0;
        while (true) {
            String[] strArr = f4601b;
            if (i >= strArr.length) {
                return;
            }
            if (strReplace.equals(strArr[i])) {
                wheelView.setCurrentItem(i);
                return;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(SODoc sODoc, WheelView wheelView, CheckBox checkBox) {
        String strReplace = f4601b[wheelView.getCurrentItem()];
        if (checkBox.isChecked()) {
            strReplace = strReplace.replace("#,##0", "#,##0.00");
        }
        sODoc.setSelectedCellFormat(strReplace);
    }

    public static void show(Context context, View view, final SODoc sODoc) {
        a(context);
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_accounting, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.cur_wheel);
        c cVar = new c(context, f4600a);
        cVar.c = context.getResources().getColor(R.color.sodk_editor_wheel_item_text_color);
        wheelView.setViewAdapter(cVar);
        wheelView.setVisibleItems(5);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.decimal_places_checkbox);
        b(sODoc, wheelView, checkBox);
        wheelView.d(new d() { // from class: com.artifex.sonui.editor.EditNumberFormatAccounting.1
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView2) {
                EditNumberFormatAccounting.c(sODoc, wheelView, checkBox);
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView2) {
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.artifex.sonui.editor.EditNumberFormatAccounting.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditNumberFormatAccounting.c(sODoc, wheelView, checkBox);
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatAccounting.3
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                wheelView.b();
                String[] unused = EditNumberFormatAccounting.f4600a = null;
                String[] unused2 = EditNumberFormatAccounting.f4601b = null;
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }
}
