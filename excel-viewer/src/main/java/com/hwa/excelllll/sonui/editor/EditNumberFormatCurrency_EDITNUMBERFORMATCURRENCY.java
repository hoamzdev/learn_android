package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import com.artifex.mupdf.fitz.Document;
import com.artifex.solib.SODoc;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.apps.d;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatCurrency {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String[] f4607a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static String[] f4608b;
    private static final String[] c;
    private static final String[] d;
    private static WheelView e;
    private static WheelView f;
    private static CheckBox g;
    private static SODoc h;

    static {
        Double dValueOf = Double.valueOf(-1234.1d);
        String str = String.format("%.2f", dValueOf);
        Double dValueOf2 = Double.valueOf(1234.1d);
        c = new String[]{str, String.format("%.2f (red)", dValueOf2), String.format("%.2f (red)", dValueOf), String.format("(%.2f)", dValueOf2), String.format("(%.2f) (red)", dValueOf2)};
        d = new String[]{"DEC", "DEC;[Red]DEC", "DEC;[Red]\\-DEC", "DEC_);(DEC)", "DEC_);[Red](DEC)"};
    }

    private static void a(Context context) {
        String strC = com.artifex.solib.a.c(context, "currencies.json");
        if (strC == null) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONObject(strC).getJSONArray("currencies");
            f4607a = new String[jSONArray.length()];
            f4608b = new String[jSONArray.length()];
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
                f4607a[i] = string;
                f4608b[i] = string2;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private static void c() {
        String selectedCellFormat = h.getSelectedCellFormat();
        g.setChecked(selectedCellFormat.contains("#,##0.00"));
        String strReplace = selectedCellFormat.replace("#,##0.00", "#,##0");
        for (int i = 0; i < f4608b.length; i++) {
            int i2 = 0;
            while (true) {
                String[] strArr = d;
                if (i2 < strArr.length) {
                    if (strArr[i2].replace("DEC", f4608b[i]).equals(strReplace)) {
                        e.setCurrentItem(i);
                        f.setCurrentItem(i2);
                        return;
                    }
                    i2++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d() {
        String strReplace = f4608b[e.getCurrentItem()];
        if (g.isChecked()) {
            strReplace = strReplace.replace("#,##0", "#,##0.00");
        }
        h.setSelectedCellFormat(d[f.getCurrentItem()].replace("DEC", strReplace));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e() {
        e.b();
        f.b();
        e = null;
        f = null;
        g = null;
        h = null;
        f4607a = null;
        f4608b = null;
    }

    public static void show(Context context, View view, SODoc sODoc) {
        a(context);
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_currency, null);
        h = sODoc;
        e = (WheelView) viewInflate.findViewById(R.id.left_wheel);
        c cVar = new c(context, f4607a);
        Resources resources = context.getResources();
        int i = R.color.sodk_editor_wheel_item_text_color;
        cVar.c = resources.getColor(i);
        e.setViewAdapter(cVar);
        e.setVisibleItems(5);
        f = (WheelView) viewInflate.findViewById(R.id.right_wheel);
        d dVar = new d(context, c);
        dVar.c = context.getResources().getColor(i);
        f.setViewAdapter(dVar);
        f.setVisibleItems(5);
        g = (CheckBox) viewInflate.findViewById(R.id.decimal_places_checkbox);
        c();
        f.d(new kankan.wheel.widget.d() { // from class: com.artifex.sonui.editor.EditNumberFormatCurrency.1
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView) {
                EditNumberFormatCurrency.d();
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView) {
            }
        });
        e.d(new kankan.wheel.widget.d() { // from class: com.artifex.sonui.editor.EditNumberFormatCurrency.2
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView) {
                EditNumberFormatCurrency.d();
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView) {
            }
        });
        g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCurrency.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditNumberFormatCurrency.d();
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCurrency.4
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                EditNumberFormatCurrency.e();
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }
}
