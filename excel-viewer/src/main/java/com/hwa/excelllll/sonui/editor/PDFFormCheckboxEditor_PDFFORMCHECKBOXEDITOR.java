package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import com.artifex.solib.c;
import com.artifex.solib.h;
import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class PDFFormCheckboxEditor extends PDFFormEditor {
    public PDFFormCheckboxEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void a(Canvas canvas) {
        double factor = this.mPageView.getFactor();
        double dConvertDpToPixel = Utilities.convertDpToPixel(1.0f);
        Double.isNaN(dConvertDpToPixel);
        int iA = (int) e6.a(dConvertDpToPixel, dConvertDpToPixel, dConvertDpToPixel, factor);
        if (iA < 2) {
            iA = 2;
        }
        Paint paint = new Paint();
        paint.setColor(-65536);
        paint.setStrokeWidth(iA);
        paint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        int i = (-iA) / 2;
        rect.inset(i, i);
        rect.offset(3, 1);
        canvas.drawRect(rect, paint);
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public SOEditText getEditText() {
        return (SOEditText) findViewById(R.id.pdf_checkbox_editor);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mStopped) {
            return;
        }
        super.onDraw(canvas);
        if (this.mDocViewAtRest) {
            a(canvas);
        }
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void setupInput() {
        this.mEditText.requestFocus();
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void singleTap(float f, float f2) {
        toggle();
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void start(DocMuPdfPageView docMuPdfPageView, int i, c cVar, DocView docView, h hVar, Rect rect, PDFFormEditor.EditorListener editorListener) {
        super.start(docMuPdfPageView, i, cVar, docView, hVar, rect, editorListener);
        this.mEditText.setFilters(new InputFilter[]{new InputFilter() { // from class: com.artifex.sonui.editor.PDFFormCheckboxEditor.1
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
                if (charSequence.toString().equals("\n")) {
                    new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.PDFFormCheckboxEditor.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PDFFormCheckboxEditor.this.stop();
                            PDFFormCheckboxEditor.this.mEditorListener.onStopped();
                        }
                    });
                    return null;
                }
                if (!charSequence.toString().equals(" ")) {
                    return null;
                }
                PDFFormCheckboxEditor.this.toggle();
                return null;
            }
        }});
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public boolean stop() {
        if (this.mStopped) {
            return true;
        }
        super.stop();
        SOEditText sOEditText = this.mEditText;
        if (sOEditText != null) {
            sOEditText.setOnKeyListener(null);
            this.mEditText.setFilters(new InputFilter[0]);
        }
        return true;
    }

    public void toggle() {
        this.mWidget.i();
        this.mDoc.a(this.mPageNumber);
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void doubleTap(float f, float f2) {
    }
}
