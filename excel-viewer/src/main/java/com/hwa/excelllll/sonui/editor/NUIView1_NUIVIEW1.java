package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import com.artifex.solib.k;
import defpackage.h;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class NUIView1 extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private NUIDocView f4685a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private OnDoneListener f4686b;

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public interface OnDoneListener {
        void done();
    }

    public NUIView1(Context context) {
        super(context);
        this.f4686b = null;
        a(context);
    }

    private void a(String str) {
        Object nUIDocViewXls;
        if (com.artifex.solib.a.c(str, "svg") || com.artifex.solib.a.c(str, "epub") || com.artifex.solib.a.c(str, "xps") || com.artifex.solib.a.c(str, "fb2") || com.artifex.solib.a.c(str, "xhtml") || com.artifex.solib.a.c(str, "cbz")) {
            nUIDocViewXls = new NUIDocViewMuPdf(getContext());
        } else {
            nUIDocViewXls = k.c((Activity) getContext(), str) ? new NUIDocViewXls(getContext()) : new NUIDocViewOther(getContext());
        }
        this.f4685a = (NUIDocView) nUIDocViewXls;
    }

    public boolean doKeyDown(int i, KeyEvent keyEvent) {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            return nUIDocView.doKeyDown(i, keyEvent);
        }
        return false;
    }

    public void endDocSession(boolean z) {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.endDocSession(z);
        }
    }

    public boolean isDocModified() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            return nUIDocView.documentHasBeenModified();
        }
        return false;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.onActivityResult(i, i2, intent);
        }
    }

    public void onBackPressed() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.onBackPressed();
        }
    }

    public void onConfigurationChange(Configuration configuration) {
        this.f4685a.onConfigurationChange(configuration);
    }

    public void onDestroy() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.onDestroy();
        }
    }

    public void onPause() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.onPause();
        }
        Utilities.hideKeyboard(getContext());
    }

    public void onResume() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.onResume();
        }
    }

    public void releaseBitmaps() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.releaseBitmaps();
        }
    }

    public void setConfigurableButtons() {
        NUIDocView nUIDocView = this.f4685a;
        if (nUIDocView != null) {
            nUIDocView.setConfigurableButtons();
        }
    }

    public void setOnDoneListener(OnDoneListener onDoneListener) {
        this.f4686b = onDoneListener;
    }

    public void start(Uri uri, boolean z, int i, String str, String str2) {
        a(uri, str2);
        addView(this.f4685a);
        this.f4685a.start(uri, z, i, str, this.f4686b);
    }

    public NUIView1(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4686b = null;
        a(context);
    }

    public NUIView1(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4686b = null;
        a(context);
    }

    public void start(SODocSession sODocSession, int i, String str) {
        a(sODocSession.getUserPath());
        addView(this.f4685a);
        this.f4685a.start(sODocSession, i, str, this.f4686b);
    }

    public void start(SOFileState sOFileState, int i) {
        a(sOFileState.getOpenedPath());
        addView(this.f4685a);
        this.f4685a.start(sOFileState, i, this.f4686b);
    }

    private void a(Uri uri, String str) {
        a(h.i("SomeFileName.", com.artifex.solib.a.a(getContext(), uri, str)));
    }

    private void a(Context context) {
    }
}
