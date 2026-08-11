package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import defpackage.h;

/* JADX INFO: loaded from: classes.dex */
public class NUIView extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private NUIDocView f4683a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private OnDoneListener f4684b;

    public interface OnDoneListener {
        void done();
    }

    public NUIView(Context context) {
        super(context);
        this.f4684b = null;
        a(context);
    }

    private void a(Uri uri, String str) {
        a(h.i("SomeFileName.", com.artifex.solib.a.a(getContext(), uri, str)));
    }

    private void a(String str) {
        throw null;
    }

    public boolean doKeyDown(int i, KeyEvent keyEvent) {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            return nUIDocView.doKeyDown(i, keyEvent);
        }
        return false;
    }

    public void endDocSession(boolean z) {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.endDocSession(z);
        }
    }

    public boolean isDocModified() {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            return nUIDocView.documentHasBeenModified();
        }
        return false;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.onActivityResult(i, i2, intent);
        }
    }

    public void onBackPressed() {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.onBackPressed();
        }
    }

    public void onConfigurationChange(Configuration configuration) {
        this.f4683a.onConfigurationChange(configuration);
    }

    public void onDestroy() {
        this.f4683a.onDestroy();
    }

    public void onPause() {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.onPause();
        }
        Utilities.hideKeyboard(getContext());
    }

    public void onResume() {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.onResume();
        }
    }

    public void releaseBitmaps() {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.releaseBitmaps();
        }
    }

    public void setConfigurableButtons() {
        NUIDocView nUIDocView = this.f4683a;
        if (nUIDocView != null) {
            nUIDocView.setConfigurableButtons();
        }
    }

    public void setOnDoneListener(OnDoneListener onDoneListener) {
        this.f4684b = onDoneListener;
    }

    public void start(Uri uri, boolean z, int i, String str, String str2) {
        a(uri, str2);
        addView(this.f4683a);
        this.f4683a.start(uri, z, i, str, this.f4684b);
    }

    public NUIView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4684b = null;
        a(context);
    }

    public NUIView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4684b = null;
        a(context);
    }

    private void a(Context context) {
    }

    public void start(SODocSession sODocSession, int i, String str) {
        a(sODocSession.getUserPath());
        addView(this.f4683a);
        this.f4683a.start(sODocSession, i, str, this.f4684b);
    }

    public void start(SOFileState sOFileState, int i) {
        a(sOFileState.getOpenedPath());
        addView(this.f4683a);
        this.f4683a.start(sOFileState, i, this.f4684b);
    }
}
