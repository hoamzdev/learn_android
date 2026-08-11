package com.hwa.excelllll.sonui.editor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class BaseActivity extends AppCompatActivity {
    private static BaseActivity mCurrentActivity;
    private static PermissionResultHandler mPermissionResultHandler;
    private static ResultHandler mResultHandler;
    private static ResumeHandler mResumeHandler;

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public interface PermissionResultHandler {
        boolean handle(int i, String[] strArr, int[] iArr);
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public interface ResultHandler {
        boolean handle(int i, int i2, Intent intent);
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public interface ResumeHandler {
        void handle();
    }

    public static BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    private void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    public static void setPermissionResultHandler(PermissionResultHandler permissionResultHandler) {
        mPermissionResultHandler = permissionResultHandler;
    }

    public static void setResultHandler(ResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    public static void setResumeHandler(ResumeHandler resumeHandler) {
        mResumeHandler = resumeHandler;
    }

    public boolean isSlideShow() {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        ResultHandler resultHandler = mResultHandler;
        if (resultHandler == null || !resultHandler.handle(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        mCurrentActivity = null;
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        PermissionResultHandler permissionResultHandler = mPermissionResultHandler;
        if (permissionResultHandler == null || !permissionResultHandler.handle(i, strArr, iArr)) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        mCurrentActivity = this;
        super.onResume();
        ResumeHandler resumeHandler = mResumeHandler;
        if (resumeHandler != null) {
            resumeHandler.handle();
        }
    }

    private void setLocale(String str) {
        setLocale(new Locale(str));
    }
}
