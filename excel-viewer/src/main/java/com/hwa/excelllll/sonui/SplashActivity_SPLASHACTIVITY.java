package com.hwa.excelllll.sonui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.onesignal.debug.internal.crash.AnrConstants;

/* JADX INFO: loaded from: classes.dex */
public class SplashActivity extends BaseActivity {
    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(Res.i("sodk_splash"));
        new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                SplashActivity.this.startActivity(new Intent(this, (Class<?>) ExplorerActivity.class));
                SplashActivity.this.finish();
            }
        }, AnrConstants.DEFAULT_CHECK_INTERVAL_MS);
    }
}
