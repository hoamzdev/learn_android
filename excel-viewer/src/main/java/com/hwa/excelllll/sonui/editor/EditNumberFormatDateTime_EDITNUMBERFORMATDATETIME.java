package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import java.util.HashMap;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.d;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatDateTime {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static HashMap<String, String[]> f4624a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static String[] f4625b;
    private static String[] c;
    private static String[] d;

    private static void a(Context context) {
        String strC = com.artifex.solib.a.c(context, "date_time.json");
        if (strC == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(strC);
            JSONArray jSONArray = jSONObject.getJSONArray("countries");
            f4625b = new String[jSONArray.length()];
            c = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = jSONArray.getString(i);
                f4625b[i] = string;
                c[i] = a(context, string.replace(" ", "_"));
            }
            f4624a = new HashMap<>();
            JSONObject jSONObject2 = jSONObject.getJSONObject("formats");
            int i2 = 0;
            while (true) {
                String[] strArr = f4625b;
                if (i2 >= strArr.length) {
                    d = f4624a.get(strArr[0]);
                    return;
                }
                JSONArray jSONArray2 = jSONObject2.getJSONArray(strArr[i2]);
                String[] strArr2 = new String[jSONArray2.length()];
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    strArr2[i3] = jSONArray2.getString(i3);
                }
                f4624a.put(f4625b[i2], strArr2);
                i2++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] d(String[] strArr) {
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String strSubstring = strArr[i];
            int iLastIndexOf = strSubstring.lastIndexOf("]");
            if (iLastIndexOf >= 0 && strSubstring.indexOf("[h]") != 0) {
                strSubstring = strSubstring.substring(iLastIndexOf + 1);
            }
            int iIndexOf = strSubstring.indexOf(";@");
            if (iIndexOf >= 0) {
                strSubstring = strSubstring.substring(0, iIndexOf);
            }
            strArr2[i] = strSubstring;
        }
        return strArr2;
    }

    public static void show(Context context, View view, final SODoc sODoc) {
        a(context);
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_datetime, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.country_wheel);
        c cVar = new c(context, c);
        Resources resources = context.getResources();
        int i = R.color.sodk_editor_wheel_item_text_color;
        cVar.c = resources.getColor(i);
        wheelView.setViewAdapter(cVar);
        wheelView.setVisibleItems(5);
        final WheelView wheelView2 = (WheelView) viewInflate.findViewById(R.id.format_wheel);
        final c cVar2 = new c(context, d);
        cVar2.c = context.getResources().getColor(i);
        wheelView2.setViewAdapter(cVar2);
        wheelView2.setVisibleItems(5);
        a(sODoc, wheelView, wheelView2);
        wheelView.d(new d() { // from class: com.artifex.sonui.editor.EditNumberFormatDateTime.1
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView3) {
                String[] unused = EditNumberFormatDateTime.d = (String[]) EditNumberFormatDateTime.f4624a.get(EditNumberFormatDateTime.f4625b[wheelView.getCurrentItem()]);
                cVar2.d = EditNumberFormatDateTime.d(EditNumberFormatDateTime.d);
                wheelView2.e(true);
                wheelView2.setCurrentItem(0);
                sODoc.setSelectedCellFormat(EditNumberFormatDateTime.d[0]);
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView3) {
            }
        });
        wheelView2.d(new d() { // from class: com.artifex.sonui.editor.EditNumberFormatDateTime.2
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView3) {
                sODoc.setSelectedCellFormat(EditNumberFormatDateTime.d[wheelView2.getCurrentItem()]);
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView3) {
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatDateTime.3
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                wheelView.b();
                wheelView2.b();
                String[] unused = EditNumberFormatDateTime.f4625b = null;
                String[] unused2 = EditNumberFormatDateTime.d = null;
                HashMap unused3 = EditNumberFormatDateTime.f4624a = null;
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }

    private static String a(Context context, String str) {
        String string;
        int identifier = context.getResources().getIdentifier(str, "string", context.getPackageName());
        return (identifier == 0 || (string = context.getString(identifier)) == null || string.isEmpty()) ? str : string;
    }

    private static void a(SODoc sODoc, WheelView wheelView, WheelView wheelView2) {
        String selectedCellFormat = sODoc.getSelectedCellFormat();
        int i = 0;
        while (true) {
            String[] strArr = f4625b;
            if (i >= strArr.length) {
                wheelView.setCurrentItem(0);
                wheelView2.setCurrentItem(0);
                return;
            }
            String[] strArr2 = f4624a.get(strArr[i]);
            for (int i2 = 0; i2 < strArr2.length; i2++) {
                if (strArr2[i2].equals(selectedCellFormat)) {
                    wheelView.setCurrentItem(i);
                    wheelView2.setCurrentItem(i2);
                    return;
                }
            }
            i++;
        }
    }
}
