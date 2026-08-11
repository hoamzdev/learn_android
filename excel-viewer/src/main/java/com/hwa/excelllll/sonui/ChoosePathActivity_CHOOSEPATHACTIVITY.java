package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.Utilities;
import com.xlsxreader.excelreader.R;
import defpackage.i1;
import defpackage.j1;
import defpackage.u0;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class ChoosePathActivity extends BaseActivity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static a f4379a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static int f4380b;
    private static String c;
    private static boolean d;

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public interface a {
        void a();

        void a(FileBrowser fileBrowser);
    }

    public static void a(Activity activity, int i, boolean z, a aVar, String str) {
        f4379a = aVar;
        f4380b = i;
        c = str;
        d = false;
        activity.startActivity(new Intent(activity, (Class<?>) ChoosePathActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(FileBrowser fileBrowser, View view) {
        Utilities.hideKeyboard(this);
        d = true;
        finish();
        a aVar = f4379a;
        if (aVar != null) {
            aVar.a(fileBrowser);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        Utilities.hideKeyboard(this);
        d = true;
        finish();
        a aVar = f4379a;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$2(FileBrowser fileBrowser, View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || i != 66) {
            return false;
        }
        Utilities.hideKeyboard(this);
        d = true;
        finish();
        f4379a.a(fileBrowser);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$3(FileBrowser fileBrowser, SOEditText sOEditText, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        Utilities.hideKeyboard(this);
        d = true;
        finish();
        f4379a.a(fileBrowser);
        return true;
    }

    @Override // android.app.Activity
    public void finish() {
        a aVar;
        if (!d && (aVar = f4379a) != null) {
            aVar.a();
        }
        super.finish();
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.sodk_choose_path);
        String str = c;
        final FileBrowser fileBrowser = (FileBrowser) findViewById(R.id.file_browser);
        fileBrowser.a(this, str);
        AppCompatTextView appCompatTextView = (AppCompatTextView) findViewById(R.id.save_button);
        appCompatTextView.setText(f4380b == 3 ? getString(R.string.sodk_editor_copy) : getString(R.string.sodk_editor_save));
        int i = 0;
        appCompatTextView.setOnClickListener(new i1(i, this, fileBrowser));
        findViewById(R.id.cancel_button).setOnClickListener(new j1(i, this));
        SOEditText editText = fileBrowser.getEditText();
        editText.setOnKeyListener(new View.OnKeyListener() { // from class: k1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                return this.f20925a.lambda$onCreate$2(fileBrowser, view, i2, keyEvent);
            }
        });
        editText.setOnEditorActionListener(new u0(2, this, fileBrowser));
    }
}
