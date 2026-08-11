package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import com.artifex.solib.SODoc;
import defpackage.h;

/* JADX INFO: loaded from: classes.dex */
public class InputView extends View implements KeyEvent.Callback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a f4655a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SODoc f4656b;
    private NUIDocView c;

    public InputView(Context context, SODoc sODoc, NUIDocView nUIDocView) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.f4656b = sODoc;
        this.c = nUIDocView;
        this.f4655a = new a(this, true, (InputMethodManager) context.getSystemService("input_method"));
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        Configuration configuration = getResources().getConfiguration();
        editorInfo.imeOptions = 0;
        if (configuration.keyboard != 3) {
            editorInfo.imeOptions = 268435456;
        }
        editorInfo.inputType = 1;
        editorInfo.initialCapsMode = this.f4655a.getCursorCapsMode(1);
        editorInfo.privateImeOptions = null;
        editorInfo.initialSelStart = Selection.getSelectionStart(this.f4655a.f4657a);
        editorInfo.initialSelEnd = Selection.getSelectionEnd(this.f4655a.f4657a);
        editorInfo.actionLabel = null;
        editorInfo.actionId = 0;
        editorInfo.extras = null;
        editorInfo.hintText = null;
        return this.f4655a;
    }

    public void resetEditable() {
        this.f4655a.a();
    }

    public void setFocus() {
        requestFocus();
    }

    public class a extends BaseInputConnection {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public SpannableStringBuilder f4657a;
        private ExtractedTextRequest c;
        private InputMethodManager d;
        private View e;
        private String f;
        private int g;
        private int h;
        private String i;
        private int j;

        public a(View view, boolean z, InputMethodManager inputMethodManager) {
            super(view, z);
            this.c = null;
            this.f = "";
            this.g = 0;
            this.h = 0;
            this.i = "";
            this.j = 0;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            this.f4657a = spannableStringBuilder;
            spannableStringBuilder.clear();
            this.f4657a.clearSpans();
            Selection.setSelection(this.f4657a, 0);
            this.g = 0;
            this.h = 0;
            this.j = 0;
            this.i = "";
            this.d = inputMethodManager;
            this.e = view;
        }

        private void a(CharSequence charSequence, String str) {
            InputView.this.c.onTyping();
            InputView.this.c.setIsComposing(false);
            StringBuilder sb = new StringBuilder();
            sb.append(charSequence.toString());
            String strP = h.p(sb, this.i, str);
            InputView.this.f4656b.setSelectionText(strP, this.g, true);
            int length = strP.length() + this.g + this.h;
            this.h = length;
            this.j = length;
            this.g = 0;
            this.i = "";
            d();
            b();
        }

        private void b() {
            this.f = "";
            InputView.this.c.setIsComposing(false);
        }

        private void c() {
            this.f4657a.clear();
            this.f4657a.clearSpans();
            this.i = "";
            this.h = 0;
            this.g = 0;
            this.j = 0;
        }

        private void d() {
            ExtractedTextRequest extractedTextRequest = this.c;
            if (extractedTextRequest != null) {
                ExtractedText extractedText = new ExtractedText();
                SpannableStringBuilder spannableStringBuilder = this.f4657a;
                if (spannableStringBuilder != null) {
                    int length = spannableStringBuilder.length();
                    extractedText.partialEndOffset = -1;
                    extractedText.partialStartOffset = -1;
                    int i = extractedTextRequest.flags & 1;
                    SpannableStringBuilder spannableStringBuilder2 = this.f4657a;
                    extractedText.text = i != 0 ? spannableStringBuilder2.subSequence(0, length) : TextUtils.substring(spannableStringBuilder2, 0, length);
                    extractedText.flags = 0;
                    extractedText.startOffset = 0;
                    extractedText.selectionStart = Selection.getSelectionStart(this.f4657a);
                    extractedText.selectionEnd = Selection.getSelectionEnd(this.f4657a);
                } else {
                    extractedText.flags = 0;
                    extractedText.startOffset = 0;
                    extractedText.selectionStart = 0;
                    extractedText.selectionEnd = 0;
                }
                this.d.updateExtractedText(this.e, extractedTextRequest.token, extractedText);
            }
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public void closeConnection() {
            super.closeConnection();
            b();
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public boolean commitText(CharSequence charSequence, int i) {
            a(charSequence, "");
            return super.commitText(charSequence, i);
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public boolean deleteSurroundingText(int i, int i2) {
            InputView.this.c.onTyping();
            int selectionStart = Selection.getSelectionStart(this.f4657a);
            int selectionEnd = Selection.getSelectionEnd(this.f4657a);
            if (selectionStart != -1 && selectionEnd != -1) {
                if (selectionStart > selectionEnd) {
                    selectionStart = selectionEnd;
                }
                int iMin = Math.min(i, selectionStart);
                int iMin2 = Math.min(i2, this.f4657a.length() - selectionEnd);
                try {
                    boolean zDeleteSurroundingText = super.deleteSurroundingText(iMin, iMin2);
                    if (iMin2 == 0) {
                        for (int i3 = 0; i3 < iMin; i3++) {
                            this.h--;
                            this.j--;
                            InputView.this.f4656b.N();
                        }
                    }
                    d();
                    return zDeleteSurroundingText;
                } catch (ArrayIndexOutOfBoundsException unused) {
                    a();
                }
            }
            return true;
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public boolean finishComposingText() {
            boolean z;
            boolean zFinishComposingText = super.finishComposingText();
            String str = this.f + this.i;
            InputView.this.c.setIsComposing(false);
            if (str.length() > 0) {
                z = true;
                InputView.this.f4656b.setSelectionText(str, 0, true);
                this.h = str.length() + this.h;
                this.i = "";
            } else {
                z = false;
            }
            d();
            this.g = 0;
            b();
            if (z) {
                InputView.this.c.onTyping();
            }
            return zFinishComposingText;
        }

        @Override // android.view.inputmethod.BaseInputConnection
        public Editable getEditable() {
            return this.f4657a;
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
            ExtractedText extractedText = new ExtractedText();
            this.c = extractedTextRequest;
            SpannableStringBuilder spannableStringBuilder = this.f4657a;
            if (spannableStringBuilder == null) {
                return null;
            }
            int length = spannableStringBuilder.length();
            extractedText.partialEndOffset = -1;
            extractedText.partialStartOffset = -1;
            int i2 = extractedTextRequest.flags & 1;
            SpannableStringBuilder spannableStringBuilder2 = this.f4657a;
            extractedText.text = i2 != 0 ? spannableStringBuilder2.subSequence(0, length) : TextUtils.substring(spannableStringBuilder2, 0, length);
            extractedText.flags = 0;
            extractedText.startOffset = 0;
            extractedText.selectionStart = Selection.getSelectionStart(this.f4657a);
            extractedText.selectionEnd = Selection.getSelectionEnd(this.f4657a);
            return extractedText;
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            int i;
            int keyCode = keyEvent.getKeyCode();
            if (keyEvent.getAction() == 1) {
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        InputView.this.c.onTyping();
                        b();
                        c();
                        break;
                    default:
                        if (keyCode == 66) {
                            a(this.f, "\n");
                            a();
                        } else if (keyCode == 67 && (i = this.h) > 0) {
                            this.h = i - 1;
                            this.j--;
                        }
                        break;
                }
            }
            return super.sendKeyEvent(keyEvent);
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public boolean setComposingRegion(int i, int i2) {
            if (i < this.f4657a.length() || i2 < this.f4657a.length()) {
                i = Math.min(Math.max(i, 0), this.f4657a.length() - 1);
                i2 = Math.min(Math.max(i2, 0), this.f4657a.length() - 1);
                this.g = Math.min(i, i2) - this.h;
                int iMin = Math.min(this.j, this.f4657a.length());
                if (iMin != this.j) {
                    this.j = iMin;
                }
                this.i = "";
                if (Math.max(i, i2) < this.j - 1) {
                    this.i = this.f4657a.subSequence(Math.max(i, i2), this.j).toString();
                }
            } else {
                this.g = this.f4657a.length() - this.h;
            }
            return super.setComposingRegion(i, i2);
        }

        @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
        public boolean setComposingText(CharSequence charSequence, int i) {
            boolean composingText = super.setComposingText(charSequence, i);
            InputView.this.c.onTyping();
            InputView.this.c.setIsComposing(true);
            this.f = charSequence.toString();
            InputView.this.f4656b.setSelectionText(this.f + this.i, this.g, false);
            this.h = this.h + this.g;
            this.g = 0;
            this.j = this.i.length() + this.f.length() + this.h;
            d();
            return composingText;
        }

        public void a() {
            b();
            c();
            this.d.restartInput(this.e);
        }
    }
}
