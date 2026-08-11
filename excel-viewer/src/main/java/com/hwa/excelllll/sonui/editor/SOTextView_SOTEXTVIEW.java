package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class SOTextView extends TextView {
    public SOTextView(Context context) {
        super(context);
    }

    public void setOnEditorActionListener(final SOTextViewOnEditorActionListener sOTextViewOnEditorActionListener) {
        setOnEditorActionListener(new OnEditorActionListener() { // from class: com.artifex.sonui.editor.SOTextView.1
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return sOTextViewOnEditorActionListener.onEditorAction((SOTextView) textView, i, keyEvent);
            }
        });
    }

    public SOTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SOTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SOTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
