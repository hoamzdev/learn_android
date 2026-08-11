package com.hwa.excelllll.sonui.editor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.c;

/* JADX INFO: loaded from: classes.dex */
public class ListWheelDialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static boolean f4674a;

    public interface a {
        void a();

        void a(String str);
    }

    public static void show(Context context, final String[] strArr, String str, final a aVar) {
        View viewInflate = View.inflate(context, R.layout.sodk_editor_wheel_chooser_dialog, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.wheel);
        wheelView.setViewAdapter(new c(context, strArr));
        wheelView.setVisibleItems(5);
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                i = -1;
                break;
            } else if (str.equals(strArr[i])) {
                break;
            } else {
                i++;
            }
        }
        if (i >= 0) {
            wheelView.setCurrentItem(i);
        }
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(context).setView(viewInflate).create();
        f4674a = false;
        viewInflate.findViewById(R.id.sodk_editor_cancel_button).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.ListWheelDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean unused = ListWheelDialog.f4674a = true;
                alertDialogCreate.dismiss();
                aVar.a();
            }
        });
        viewInflate.findViewById(R.id.sodk_editor_update_button).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.ListWheelDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean unused = ListWheelDialog.f4674a = true;
                alertDialogCreate.dismiss();
                aVar.a(strArr[wheelView.getCurrentItem()]);
            }
        });
        alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.artifex.sonui.editor.ListWheelDialog.3
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && (i2 == 61 || i2 == 66)) {
                    dialogInterface.dismiss();
                }
                return true;
            }
        });
        alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.artifex.sonui.editor.ListWheelDialog.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                aVar.a();
            }
        });
        Window window = alertDialogCreate.getWindow();
        window.setLayout(-2, -2);
        window.setGravity(17);
        alertDialogCreate.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialogCreate.show();
    }

    public static boolean wasDismissedWithButton() {
        return f4674a;
    }
}
