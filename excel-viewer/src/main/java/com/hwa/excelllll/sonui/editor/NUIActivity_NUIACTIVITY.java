package com.hwa.excelllll.sonui.editor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.artifex.solib.ConfigOptions;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class NUIActivity extends BaseActivity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static SODocSession f4680a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private Intent f4681b = null;
    private long c = 0;
    private int d = -1;
    protected NUIView1 mNUIView;

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Intent intent, boolean z) {
        boolean z2;
        int i;
        SOFileState sOFileStateFromString;
        String string;
        String string2;
        Bundle extras = intent.getExtras();
        boolean z3 = extras != null && extras.getBoolean("SESSION", false);
        SODocSession sODocSession = f4680a;
        setContentView(com.xlsxreader.excelreader.R.layout.sodk_editor_doc_view_activity);
        NUIView1 nUIView1 = (NUIView1) findViewById(com.xlsxreader.excelreader.R.id.doc_view);
        this.mNUIView = nUIView1;
        nUIView1.setOnDoneListener(new NUIView1.OnDoneListener() { // from class: com.artifex.sonui.editor.NUIActivity.2
            @Override // com.artifex.sonui.editor.NUIView1.OnDoneListener
            public void done() {
                NUIActivity.super.finish();
            }
        });
        if (extras != null) {
            int i2 = extras.getInt("START_PAGE");
            sOFileStateFromString = SOFileState.fromString(extras.getString("STATE"), SOFileDatabase.getDatabase());
            string = extras.getString("FOREIGN_DATA", null);
            boolean z4 = extras.getBoolean("IS_TEMPLATE", true);
            string2 = extras.getString("CUSTOM_DOC_DATA");
            z2 = z4;
            z3 = (sOFileStateFromString != null || z || (sOFileStateFromString = SOFileState.getAutoOpen(this)) == null) ? z3 : false;
            i = i2;
        } else {
            z2 = true;
            i = 1;
            sOFileStateFromString = null;
            string = null;
            string2 = null;
        }
        if (string2 == null) {
            Utilities.setSessionLoadListener(null);
        }
        if (z3) {
            this.mNUIView.start(sODocSession, i, string);
            return;
        }
        NUIView1 nUIView2 = this.mNUIView;
        if (sOFileStateFromString != null) {
            nUIView2.start(sOFileStateFromString, i);
        } else {
            nUIView2.start(intent.getData(), z2, i, string2, intent.getType());
        }
    }

    public static void setSession(SODocSession sODocSession) {
        f4680a = sODocSession;
    }

    public Intent childIntent() {
        return this.f4681b;
    }

    public void doResumeActions() {
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.onResume();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.mNUIView == null) {
            super.finish();
        } else {
            Utilities.dismissCurrentAlert();
            onBackPressed();
        }
    }

    public void initialise() {
        a();
    }

    public boolean isDocModified() {
        NUIView1 nUIView1 = this.mNUIView;
        return nUIView1 != null && nUIView1.isDocModified();
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.onActivityResult(i, i2, intent);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        getOnBackPressedDispatcher().a(new OnBackPressedCallback(this, true) { // from class: com.artifex.sonui.editor.NUIActivity.3
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
            }
        }, this);
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.onBackPressed();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mNUIView.onConfigurationChange(configuration);
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-8193));
        initialise();
        getWindow().setStatusBarColor(getApplicationContext().getColor(com.xlsxreader.excelreader.R.color.color_excel));
        new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView()).c(false);
        if (Build.VERSION.SDK_INT >= 36) {
            ViewCompat.v(findViewById(com.xlsxreader.excelreader.R.id.main), new OnApplyWindowInsetsListener(this) { // from class: com.artifex.sonui.editor.NUIActivity.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    Insets insetsD = windowInsetsCompat.d(647);
                    view.setPadding(insetsD.f1667a, insetsD.f1668b, insetsD.c, insetsD.d);
                    return WindowInsetsCompat.f1821b;
                }
            });
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.onDestroy();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        long eventTime = keyEvent.getEventTime();
        if (this.d == i && eventTime - this.c <= 100) {
            return true;
        }
        this.c = eventTime;
        this.d = i;
        return this.mNUIView.doKeyDown(i, keyEvent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        ConfigOptions.a();
        if (isDocModified()) {
            if (isFinishing() || isDestroyed()) {
                return;
            }
            Utilities.yesNoMessage(this, getString(com.xlsxreader.excelreader.R.string.sodk_editor_new_intent_title), getString(com.xlsxreader.excelreader.R.string.sodk_editor_new_intent_body), getString(com.xlsxreader.excelreader.R.string.sodk_editor_new_intent_yes_button), getString(com.xlsxreader.excelreader.R.string.sodk_editor_new_intent_no_button), new Runnable() { // from class: com.artifex.sonui.editor.NUIActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    NUIView1 nUIView1 = NUIActivity.this.mNUIView;
                    if (nUIView1 != null) {
                        nUIView1.endDocSession(true);
                    }
                    NUIActivity.this.a(intent);
                    NUIActivity.this.a(intent, true);
                }
            }, new Runnable(this) { // from class: com.artifex.sonui.editor.NUIActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    SODocSession.SODocSessionLoadListenerCustom sessionLoadListener = Utilities.getSessionLoadListener();
                    if (sessionLoadListener != null) {
                        sessionLoadListener.onSessionReject();
                    }
                    Utilities.setSessionLoadListener(null);
                }
            });
            return;
        }
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.endDocSession(true);
        }
        a(intent);
        a(intent, true);
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.onPause();
            this.mNUIView.releaseBitmaps();
        }
        if (!isFinishing() && !isDestroyed() && Utilities.isChromebook(this) && getWindow().getWindowManager() != null) {
            PrintHelperPdf.setPrinting(false);
        }
        super.onPause();
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        this.f4681b = null;
        super.onResume();
        doResumeActions();
    }

    public void setConfigurableButtons() {
        NUIView1 nUIView1 = this.mNUIView;
        if (nUIView1 != null) {
            nUIView1.setConfigurableButtons();
        }
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        this.f4681b = intent;
        super.startActivity(intent, null);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        this.f4681b = intent;
        super.startActivityForResult(intent, i);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        this.f4681b = intent;
        super.startActivityForResult(intent, i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Intent intent) {
        ConfigOptions configOptionsA = ConfigOptions.a();
        Bundle extras = intent.getExtras();
        if (intent.hasExtra("ENABLE_SAVE")) {
            configOptionsA.n(extras.getBoolean("ENABLE_SAVE", true));
        }
        if (intent.hasExtra("ENABLE_SAVEAS")) {
            configOptionsA.b(extras.getBoolean("ENABLE_SAVEAS", true));
        }
        if (intent.hasExtra("ENABLE_SAVEAS_PDF")) {
            configOptionsA.c(extras.getBoolean("ENABLE_SAVEAS_PDF", true));
        }
        if (intent.hasExtra("ENABLE_CUSTOM_SAVE")) {
            configOptionsA.o(extras.getBoolean("ENABLE_CUSTOM_SAVE", true));
        }
        if (intent.hasExtra("ALLOW_AUTO_OPEN")) {
            configOptionsA.q(extras.getBoolean("ALLOW_AUTO_OPEN", true));
        }
    }

    private void a() {
        boolean z;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            z = extras.getBoolean("SESSION", false);
            a(intent);
        } else {
            z = false;
        }
        if (z && f4680a == null) {
            super.finish();
        } else {
            a(intent, false);
        }
    }
}
