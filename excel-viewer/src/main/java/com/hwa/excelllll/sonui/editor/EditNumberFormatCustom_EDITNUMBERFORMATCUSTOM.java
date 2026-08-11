package com.hwa.excelllll.sonui.editor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;
import kankan.wheel.widget.d;

/* JADX INFO: loaded from: classes.dex */
public class EditNumberFormatCustom {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String[] f4609a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final String[] f4610b;
    private static String[] c;
    private static String[] d;
    private static LinearLayout e = null;
    private static SOTextView f = null;
    private static c<String> g = null;
    private static WheelView h = null;
    private static SODoc i;

    public interface TextListener {
        void setText(String str);
    }

    static {
        String[] strArr = {"General", "0", "[$£-809]#,##0", "yyyy-mm-dd;@", "# ?/?"};
        f4610b = strArr;
        d = (String[]) strArr.clone();
    }

    private static void a(Context context, SODoc sODoc, WheelView wheelView) {
        String selectedCellFormat = sODoc.getSelectedCellFormat();
        int i2 = 0;
        while (true) {
            String[] strArr = d;
            if (i2 >= strArr.length) {
                c = b(c, selectedCellFormat);
                d = b(d, selectedCellFormat);
                g.d = c;
                h.e(true);
                h.setCurrentItem(d.length - 1);
                b(context, true, e, f);
                b(i, h);
                return;
            }
            if (selectedCellFormat.equals(strArr[i2])) {
                wheelView.setCurrentItem(i2);
                if (i2 + 1 > f4610b.length) {
                    b(context, true, e, f);
                    return;
                }
                return;
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final Context context, String str, String str2, final TextListener textListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.sodk_editor_alert_dialog_style);
        builder.setTitle(str);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.sodk_editor_number_format_prompt, (ViewGroup) null);
        final SOEditText sOEditText = (SOEditText) viewInflate.findViewById(R.id.editTextDialogUserInput);
        sOEditText.setText(str2);
        builder.setView(viewInflate);
        builder.setPositiveButton(R.string.sodk_editor_OK, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                Utilities.hideKeyboard(context, sOEditText);
                textListener.setText(sOEditText.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.sodk_editor_cancel, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                Utilities.hideKeyboard(context, sOEditText);
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public static void show(final Context context, View view, final SODoc sODoc) {
        String[] strArr = {context.getString(R.string.sodk_editor_format_category_general), context.getString(R.string.sodk_editor_format_category_number), context.getString(R.string.sodk_editor_format_category_currency), context.getString(R.string.sodk_editor_format_category_date_and_time), context.getString(R.string.sodk_editor_format_category_fraction)};
        f4609a = strArr;
        c = (String[]) strArr.clone();
        i = sODoc;
        View viewInflate = View.inflate(context, R.layout.sodk_editor_number_format_custom, null);
        h = (WheelView) viewInflate.findViewById(R.id.custom_wheel);
        c<String> cVar = new c<>(context, c);
        g = cVar;
        cVar.c = context.getResources().getColor(R.color.sodk_editor_wheel_item_text_color);
        h.setViewAdapter(g);
        h.setVisibleItems(5);
        e = (LinearLayout) viewInflate.findViewById(R.id.edit_function_wrapper);
        f = (SOTextView) viewInflate.findViewById(R.id.edit_function_indicator);
        ((LinearLayout) viewInflate.findViewById(R.id.create_new_wrapper)).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Context context2 = context;
                EditNumberFormatCustom.b(context2, context2.getString(R.string.sodk_editor_create_new), "", new TextListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.1.1
                    @Override // com.artifex.sonui.editor.EditNumberFormatCustom.TextListener
                    public void setText(String str) {
                        if (str == null || str.isEmpty()) {
                            return;
                        }
                        int iB = EditNumberFormatCustom.b(str);
                        if (iB >= 0) {
                            EditNumberFormatCustom.h.setCurrentItem(iB);
                        } else {
                            String[] unused = EditNumberFormatCustom.c = EditNumberFormatCustom.b(EditNumberFormatCustom.c, str);
                            String[] unused2 = EditNumberFormatCustom.d = EditNumberFormatCustom.b(EditNumberFormatCustom.d, str);
                            EditNumberFormatCustom.g.d = EditNumberFormatCustom.c;
                            EditNumberFormatCustom.h.e(true);
                            EditNumberFormatCustom.h.setCurrentItem(EditNumberFormatCustom.d.length - 1);
                            EditNumberFormatCustom.b(context, true, EditNumberFormatCustom.e, EditNumberFormatCustom.f);
                        }
                        EditNumberFormatCustom.b(sODoc, EditNumberFormatCustom.h);
                    }
                });
            }
        });
        e.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                final int currentItem = EditNumberFormatCustom.h.getCurrentItem();
                if (currentItem >= EditNumberFormatCustom.f4609a.length) {
                    String str = EditNumberFormatCustom.c[currentItem];
                    Context context2 = context;
                    EditNumberFormatCustom.b(context2, context2.getString(R.string.sodk_editor_edit_function), str, new TextListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.2.1
                        @Override // com.artifex.sonui.editor.EditNumberFormatCustom.TextListener
                        public void setText(String str2) {
                            WheelView wheelView;
                            if (str2 == null || str2.isEmpty()) {
                                return;
                            }
                            int iB = EditNumberFormatCustom.b(str2);
                            if (iB >= 0) {
                                wheelView = EditNumberFormatCustom.h;
                            } else {
                                EditNumberFormatCustom.c[currentItem] = str2;
                                EditNumberFormatCustom.d[currentItem] = str2;
                                EditNumberFormatCustom.g.d = EditNumberFormatCustom.c;
                                EditNumberFormatCustom.h.e(true);
                                wheelView = EditNumberFormatCustom.h;
                                iB = currentItem;
                            }
                            wheelView.setCurrentItem(iB);
                            EditNumberFormatCustom.b(sODoc, EditNumberFormatCustom.h);
                        }
                    });
                }
            }
        });
        b(context, false, e, f);
        a(context, sODoc, h);
        h.d(new d() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.3
            @Override // kankan.wheel.widget.d
            public void b(WheelView wheelView) {
                EditNumberFormatCustom.b(context, EditNumberFormatCustom.h.getCurrentItem() >= EditNumberFormatCustom.f4609a.length, EditNumberFormatCustom.e, EditNumberFormatCustom.f);
                EditNumberFormatCustom.b(sODoc, EditNumberFormatCustom.h);
            }

            @Override // kankan.wheel.widget.d
            public void a(WheelView wheelView) {
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.EditNumberFormatCustom.4
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                EditNumberFormatCustom.h.b();
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(String str) {
        int i2 = 0;
        while (true) {
            String[] strArr = d;
            if (i2 >= strArr.length) {
                return -1;
            }
            if (str.equals(strArr[i2])) {
                return i2;
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, boolean z, LinearLayout linearLayout, SOTextView sOTextView) {
        linearLayout.setEnabled(z);
        sOTextView.setTextColor(context.getColor(z ? R.color.sodk_editor_xls_custom_num_edit_enabled_color : R.color.sodk_editor_xls_custom_num_edit_disabled_color));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(SODoc sODoc, WheelView wheelView) {
        sODoc.setSelectedCellFormat(d[wheelView.getCurrentItem()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] b(String[] strArr, String str) {
        String[] strArr2 = new String[strArr.length + 1];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        strArr2[strArr.length] = str;
        return strArr2;
    }
}
