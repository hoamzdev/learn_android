package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class SOEditText extends EditText {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private SOEditTextOnSelectionChangeListener f4757a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SOEditTextOnTextChangeListener f4758b;

    public SOEditText(Context context) {
        super(context);
        this.f4757a = null;
        this.f4758b = null;
    }

    @Override // android.widget.TextView
    public void onSelectionChanged(int i, int i2) {
        SOEditTextOnSelectionChangeListener sOEditTextOnSelectionChangeListener = this.f4757a;
        if (sOEditTextOnSelectionChangeListener != null) {
            sOEditTextOnSelectionChangeListener.onSelectionChanged(i, i2);
        }
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        SOEditTextOnTextChangeListener sOEditTextOnTextChangeListener = this.f4758b;
        if (sOEditTextOnTextChangeListener != null) {
            sOEditTextOnTextChangeListener.onTextChanged(charSequence);
        }
    }

    public void setOnEditorActionListener(final SOEditTextOnEditorActionListener sOEditTextOnEditorActionListener) {
        setOnEditorActionListener(new OnEditorActionListener() { // from class: com.artifex.sonui.editor.SOEditText.1
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return sOEditTextOnEditorActionListener.onEditorAction((SOEditText) textView, i, keyEvent);
            }
        });
    }

    public void setOnSelectionChangeListener(SOEditTextOnSelectionChangeListener sOEditTextOnSelectionChangeListener) {
        this.f4757a = sOEditTextOnSelectionChangeListener;
    }

    public void setOnTextChangeListener(SOEditTextOnTextChangeListener sOEditTextOnTextChangeListener) {
        this.f4758b = sOEditTextOnTextChangeListener;
    }

    public SOEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4757a = null;
        this.f4758b = null;
    }

    public SOEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4757a = null;
        this.f4758b = null;
    }

    public SOEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.f4757a = null;
        this.f4758b = null;
    }
}
