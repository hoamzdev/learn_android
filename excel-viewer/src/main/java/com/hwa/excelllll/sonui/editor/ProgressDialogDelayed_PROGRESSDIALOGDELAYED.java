package com.hwa.excelllll.sonui.editor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public class ProgressDialogDelayed extends ProgressDialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f4743a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f4744b;

    public ProgressDialogDelayed(Context context, int i) {
        super(context, R.style.sodk_editor_alert_dialog_style);
        this.f4743a = false;
        this.f4744b = i;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (super.isShowing()) {
            super.dismiss();
        }
        this.f4743a = true;
    }

    @Override // android.app.Dialog
    public void show() {
        new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.ProgressDialogDelayed.1
            @Override // java.lang.Runnable
            public void run() {
                if (ProgressDialogDelayed.this.f4743a) {
                    return;
                }
                ProgressDialogDelayed.super.show();
            }
        }, this.f4744b);
    }
}
