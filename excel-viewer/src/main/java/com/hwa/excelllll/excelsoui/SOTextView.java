package com.hwa.excelllll.excelsoui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

public class SOTextView extends androidx.appcompat.widget.AppCompatTextView {
    public SOTextView(Context context) {
        super(context);
    }

    public void setOnEditorActionListener(final SOTextViewOnEditorActionListener listener) {
        setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.artifex.sonui.editor.SOTextView.1
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                return listener.onEditorAction((SOTextView) textView, actionId, event);
            }
        });
    }

    public SOTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SOTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SOTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
    }
}
