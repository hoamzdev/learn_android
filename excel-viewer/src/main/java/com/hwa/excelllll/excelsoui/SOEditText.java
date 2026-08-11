package com.hwa.excelllll.excelsoui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class SOEditText extends androidx.appcompat.widget.AppCompatEditText {
    private SOEditTextOnSelectionChangeListener selectionChangeListener;
    private SOEditTextOnTextChangeListener textChangeListener;

    public SOEditText(Context context) {
        super(context);
        this.selectionChangeListener = null;
        this.textChangeListener = null;
    }

    @Override // android.widget.TextView
    public void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        SOEditTextOnSelectionChangeListener listener = this.selectionChangeListener;
        if (listener != null) {
            listener.onSelectionChanged(selStart, selEnd);
        }
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        SOEditTextOnTextChangeListener listener = this.textChangeListener;
        if (listener != null) {
            listener.onTextChanged(text);
        }
    }

    public void setOnEditorActionListener(final SOEditTextOnEditorActionListener listener) {
        setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.artifex.sonui.editor.SOEditText.1
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                return listener.onEditorAction((SOEditText) textView, actionId, event);
            }
        });
    }

    public void setOnSelectionChangeListener(SOEditTextOnSelectionChangeListener listener) {
        this.selectionChangeListener = listener;
    }

    public void setOnTextChangeListener(SOEditTextOnTextChangeListener listener) {
        this.textChangeListener = listener;
    }

    public SOEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.selectionChangeListener = null;
        this.textChangeListener = null;
    }

    public SOEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.selectionChangeListener = null;
        this.textChangeListener = null;
    }

    public SOEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
        this.selectionChangeListener = null;
        this.textChangeListener = null;
    }
}
