package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class NUIDocViewMuPdf extends NUIDocViewPdf {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private NUIDocView.TabData[] f4682b;

    public NUIDocViewMuPdf(Context context) {
        super(context);
        this.f4682b = null;
        a(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.artifex.sonui.editor.NUIDocView, com.artifex.sonui.editor.NUIDocViewMuPdf] */
    public NUIDocView.TabData[] getTabData() {
        NUIDocViewMuPdf nUIDocViewMuPdf;
        if (this.f4682b == null) {
            NUIDocView.TabData[] tabDataArr = new NUIDocView.TabData[4];
            this.f4682b = tabDataArr;
            ?? r8 = this;
            tabDataArr[0] = new NUIDocView.TabData(this, getContext().getString(R.string.sodk_editor_tab_file), R.id.fileTab, R.layout.sodk_editor_tab_left, 0);
            NUIDocView.TabData[] tabDataArr2 = r8.f4682b;
            String string = r8.getContext().getString(R.string.sodk_editor_tab_annotate);
            int i = R.id.annotateTab;
            int i2 = R.layout.sodk_editor_tab;
            tabDataArr2[1] = new NUIDocView.TabData(r8, string, i, i2, 8);
            r8.f4682b[2] = new NUIDocView.TabData(r8, r8.getContext().getString(R.string.sodk_editor_tab_redact), R.id.redactTab, i2, 8);
            r8.f4682b[3] = new NUIDocView.TabData(r8, r8.getContext().getString(R.string.sodk_editor_tab_pages), R.id.pagesTab, R.layout.sodk_editor_tab_right, 0);
            nUIDocViewMuPdf = r8;
        } else {
            nUIDocViewMuPdf = this;
        }
        return nUIDocViewMuPdf.f4682b;
    }

    public void setConfigurableButtons() {
        super.setConfigurableButtons();
        if (this.mSaveButton != null) {
            this.mSaveButton.setVisibility(8);
        }
        if (this.mSaveAsButton != null) {
            this.mSaveAsButton.setVisibility(8);
        }
        if (this.mOpenPdfInButton != null) {
            this.mOpenPdfInButton.setVisibility(8);
        }
        if (this.mSavePdfButton != null) {
            this.mSavePdfButton.setVisibility(8);
        }
        if (this.mPrintButton != null) {
            this.mPrintButton.setVisibility(8);
        }
        if (this.mOpenInButton != null) {
            this.mOpenInButton.setVisibility(8);
        }
    }

    public NUIDocViewMuPdf(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4682b = null;
        a(context);
    }

    public NUIDocViewMuPdf(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4682b = null;
        a(context);
    }

    public void checkXFA() {
    }

    private void a(Context context) {
    }
}
