package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class DocPowerPointView extends DocView {
    public DocPowerPointView(Context context) {
        super(context);
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean shouldPreclearSelection() {
        return true;
    }

    public DocPowerPointView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DocPowerPointView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.artifex.sonui.editor.DocView
    public void setupNoteEditor() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void updateReview() {
    }
}
