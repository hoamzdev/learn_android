package com.hwa.excelllll.sonui;

import android.content.Intent;
import android.os.Bundle;
import com.artifex.sonui.editor.NUIActivity;
import com.artifex.sonui.editor.Utilities;

/* JADX INFO: loaded from: classes.dex */
public class AppNUIActivity extends NUIActivity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static g f4370a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static h f4371b;
    private d c;
    private a d;

    public static void a() {
        if (f4371b == null) {
            h hVar = new h();
            f4371b = hVar;
            Utilities.setPersistentStorage(hVar);
        }
        if (f4370a == null) {
            g gVar = new g();
            f4370a = gVar;
            Utilities.setDataLeakHandlers(gVar);
        }
    }

    @Override // com.artifex.sonui.editor.NUIActivity, com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        a aVar = this.d;
        if (aVar == null || i != aVar.a()) {
            super.onActivityResult(i, i2, intent);
            return;
        }
        boolean zA = this.d.a(i, i2, intent, this);
        if (this.mNUIView != null) {
            this.mNUIView.setVisibility(zA ? 0 : 4);
        }
    }

    @Override // com.artifex.sonui.editor.NUIActivity, com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        a();
        super.onCreate(bundle);
    }

    @Override // com.artifex.sonui.editor.NUIActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        d dVar = this.c;
        if (dVar != null) {
            dVar.a();
        }
        super.onDestroy();
    }

    @Override // com.artifex.sonui.editor.NUIActivity, com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        d dVar = this.c;
        if (dVar != null) {
            dVar.b();
        }
        setConfigurableButtons();
        super.onResume();
    }
}
