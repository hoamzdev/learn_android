package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.c;
import com.artifex.solib.h;
import com.artifex.solib.k;
import com.artifex.solib.r;
import defpackage.e6;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class PDFFormTextEditor extends PDFFormEditor {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    boolean f4701a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private Rect[] f4702b;
    private int c;
    private int d;
    private SOEditTextOnEditorActionListener e;
    private SelectionHandle f;
    private SelectionHandle g;
    private boolean h;
    private PopupWindow i;
    protected boolean mWaitingForRender;

    public PDFFormTextEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4702b = null;
        this.c = -1;
        this.d = -1;
        this.f = null;
        this.g = null;
        this.f4701a = false;
        this.h = false;
        this.mWaitingForRender = false;
    }

    private void a(Canvas canvas) {
        if (this.c == this.d) {
            Paint paint = new Paint();
            paint.setColor(-65536);
            Utilities.convertDpToPixel(4.0f);
            Rect caretRect = getCaretRect();
            Rect rect = new Rect(caretRect);
            if (new Rect(0, 0, getWidth(), getHeight()).intersect(caretRect)) {
                canvas.drawRect(rect, paint);
                return;
            }
            return;
        }
        Paint paint2 = new Paint();
        paint2.setColor(-12303292);
        paint2.setAlpha(50);
        Rect[] rectArr = this.f4702b;
        if (rectArr == null || rectArr.length <= 0) {
            return;
        }
        int iMin = Math.min(this.d, rectArr.length);
        for (int i = this.c; i < iMin; i++) {
            Rect rect2 = this.f4702b[i];
            canvas.drawRect(a(new Rect(rect2.left, rect2.top, rect2.right, rect2.bottom)), paint2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, int i2) {
        this.c = i;
        this.d = i2;
        Rect[] rectArr = this.f4702b;
        if (rectArr == null || rectArr.length <= 0) {
            return;
        }
        if (!this.f4701a) {
            if (i == i2) {
                this.f.hide();
                this.g.hide();
            } else {
                Rect rectA = a(rectArr[i]);
                this.f.setPoint(rectA.left, rectA.top);
                Rect rectA2 = a(this.f4702b[Math.min(this.d - 1, this.f4702b.length - 1)]);
                this.g.setPoint(rectA2.right, rectA2.bottom);
                this.f.show();
                this.g.show();
            }
        }
        invalidate();
    }

    private boolean c() {
        this.mWidget.a(false);
        this.mDoc.a(new c.a() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.12
            @Override // com.artifex.solib.c.a
            public void a(String str) {
                Utilities.showMessage((Activity) PDFFormTextEditor.this.getContext(), "", str);
                PDFFormTextEditor.this.mEditText.requestFocus();
            }
        });
        boolean zA = this.mWidget.a(this.mEditText.getText().toString());
        if (zA) {
            this.mDoc.a(this.mPageNumber);
            return zA;
        }
        this.mWidget.a(true);
        return zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        int selectionStart = this.mEditText.getSelectionStart();
        int selectionEnd = this.mEditText.getSelectionEnd();
        if (selectionStart != selectionEnd) {
            String string = this.mEditText.getText().toString();
            this.mEditText.setText(string.substring(0, selectionStart).concat(string.substring(selectionEnd)));
            a(selectionStart, selectionStart);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        PopupWindow popupWindow = this.i;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        this.i = null;
    }

    private Rect getCaretRect() {
        int iConvertDpToPixel = Utilities.convertDpToPixel(4.0f);
        Rect rect = new Rect();
        Rect[] rectArr = this.f4702b;
        if (rectArr == null || rectArr.length == 0) {
            rect.set(2, 0, iConvertDpToPixel + 2, getHeight());
            return rect;
        }
        int i = this.c;
        Rect rect2 = i < rectArr.length ? rectArr[i] : rectArr[rectArr.length - 1];
        int i2 = rect2.left;
        rect.set(i2, rect2.top, i2, rect2.bottom);
        Rect rectA = a(rect);
        rectA.right = rectA.left + iConvertDpToPixel;
        return rectA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSelectedText() {
        return this.mEditText.getText().subSequence(this.mEditText.getSelectionStart(), this.mEditText.getSelectionEnd()).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWidgetText(String str) {
        this.mWidget.a(str + ' ');
        this.mDoc.a(this.mPageNumber);
        this.mWaitingForRender = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.f.dispatchTouchEvent(motionEvent) || this.g.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void doubleTap(float f, float f2) {
        Rect[] rectArr = this.f4702b;
        if (rectArr == null || rectArr.length <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            Rect[] rectArr2 = this.f4702b;
            if (i >= rectArr2.length) {
                singleTap(f, f2);
                return;
            }
            int i2 = rectArr2[i].left;
            Rect rect = this.f4702b[i];
            if (a(new Rect(i2, rect.top, rect.right, rect.bottom)).contains((int) f, (int) f2)) {
                String string = this.mEditText.getText().toString();
                if (string.length() > 0) {
                    int iMin = Math.min(i, string.length() - 1);
                    while (iMin > 0 && string.charAt(iMin) != ' ' && string.charAt(iMin) != '\n') {
                        iMin--;
                    }
                    if (string.charAt(iMin) == ' ' || string.charAt(iMin) == '\n') {
                        iMin++;
                    }
                    int iMin2 = Math.min(i, string.length() - 1);
                    while (iMin2 < string.length() - 1 && string.charAt(iMin2) != ' ' && string.charAt(iMin2) != '\n') {
                        iMin2++;
                    }
                    if (string.charAt(iMin2) == ' ' || string.charAt(iMin2) == '\n') {
                        iMin2--;
                    }
                    a(iMin, iMin2 + 1);
                    invalidate();
                    showMenu();
                    return;
                }
            }
            i++;
        }
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public SOEditText getEditText() {
        return (SOEditText) findViewById(R.id.pdf_text_editor);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mStopped) {
            return;
        }
        super.onDraw(canvas);
        if (!this.mDocViewAtRest || this.mWaitingForRender) {
            return;
        }
        a(canvas);
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void onGlobalLayout() {
        super.onGlobalLayout();
        this.f.setMayDraw(this.mDocViewAtRest);
        this.g.setMayDraw(this.mDocViewAtRest);
        b();
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void onRenderComplete() {
        super.onRenderComplete();
        if (this.mWaitingForRender) {
            a(new Runnable() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.10
                @Override // java.lang.Runnable
                public void run() {
                    boolean z = PDFFormTextEditor.this.h;
                    PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                    if (z) {
                        PDFFormTextEditor.this.a(0, pDFFormTextEditor.mEditText.getText().length());
                        PDFFormTextEditor.this.h = false;
                    } else {
                        pDFFormTextEditor.b(pDFFormTextEditor.mEditText.getSelectionStart(), PDFFormTextEditor.this.mEditText.getSelectionEnd());
                    }
                    PDFFormTextEditor pDFFormTextEditor2 = PDFFormTextEditor.this;
                    if (pDFFormTextEditor2.mWaitingForRender) {
                        pDFFormTextEditor2.scrollIntoView();
                    }
                    PDFFormTextEditor pDFFormTextEditor3 = PDFFormTextEditor.this;
                    pDFFormTextEditor3.mWaitingForRender = false;
                    pDFFormTextEditor3.invalidate();
                }
            });
        }
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void scrollCaretIntoView() {
        if (this.c == this.d) {
            this.mDocView.scrollBoxIntoView(this.mPageNumber, new RectF(b(getCaretRect())), true, Utilities.convertDpToPixel(50.0f));
        }
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void scrollIntoView() {
        int i = this.c;
        int i2 = this.d;
        if (i == i2) {
            scrollCaretIntoView();
            return;
        }
        int iMin = Math.min(i2 - 1, this.f4702b.length - 1);
        Rect[] rectArr = this.f4702b;
        Rect rect = rectArr[this.c];
        int i3 = rect.left;
        int i4 = rect.top;
        Rect rect2 = rectArr[iMin];
        this.mDocView.scrollBoxIntoView(this.mPageNumber, new RectF(new Rect(i3, i4, rect2.right, rect2.bottom)), true, Utilities.convertDpToPixel(50.0f));
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void setInitialValue() {
        this.mWidget.a(true);
        String strTrim = this.mWidget.b().trim();
        this.mEditText.setText(strTrim);
        setWidgetText(strTrim);
        this.mWaitingForRender = true;
        this.h = true;
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void setupInput() {
        int i;
        int iG = this.mWidget.g();
        if (iG != 1) {
            i = 4;
            if (iG != 3 && iG != 4) {
                i = 1;
            }
        } else {
            i = 2;
        }
        this.mEditText.setInputType(524288 | i);
        boolean zH = this.mWidget.h();
        this.mEditText.setSingleLine(!zH);
        this.mEditText.setImeOptions((!zH ? 6 : 1) | 301989888);
        int iF = this.mWidget.f();
        this.mEditText.setFilters(iF > 0 ? new InputFilter[]{new InputFilter.LengthFilter(iF)} : new InputFilter[0]);
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void show() {
        this.mEditText.requestFocus();
        super.show();
    }

    public void showMenu() {
        if (this.i != null) {
            e();
        }
        final ConfigOptions configOptionsA = ConfigOptions.a();
        if (configOptionsA == null || configOptionsA.i() || configOptionsA.j()) {
            View viewInflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.sodk_editor_form_edittext_popup, (ViewGroup) null);
            PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2, true);
            this.i = popupWindow;
            popupWindow.setOutsideTouchable(true);
            this.i.setFocusable(false);
            this.i.showAsDropDown(this, 0, this.g.getVisibility() == 0 ? this.g.getHeight() : 0);
            this.i.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.13
                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    PDFFormTextEditor.this.i = null;
                }
            });
            View viewFindViewById = viewInflate.findViewById(R.id.select_all);
            View viewFindViewById2 = viewInflate.findViewById(R.id.cut);
            View viewFindViewById3 = viewInflate.findViewById(R.id.copy);
            View viewFindViewById4 = viewInflate.findViewById(R.id.paste);
            int selectionStart = this.mEditText.getSelectionStart();
            int selectionEnd = this.mEditText.getSelectionEnd();
            int length = this.mEditText.getText().toString().length();
            if (selectionStart == selectionEnd) {
                viewFindViewById2.setVisibility(8);
                viewFindViewById3.setVisibility(8);
            }
            if (selectionStart == 0 && selectionEnd == length) {
                viewFindViewById.setVisibility(8);
            }
            if (configOptionsA != null) {
                if (!configOptionsA.i()) {
                    viewFindViewById4.setVisibility(8);
                }
                if (!configOptionsA.j()) {
                    viewFindViewById3.setVisibility(8);
                }
            }
            viewFindViewById.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PDFFormTextEditor.this.e();
                    PDFFormTextEditor.this.mEditText.selectAll();
                    PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                    pDFFormTextEditor.a(pDFFormTextEditor.mEditText.getSelectionStart(), PDFFormTextEditor.this.mEditText.getSelectionEnd());
                    PDFFormTextEditor.this.invalidate();
                    PDFFormTextEditor.this.showMenu();
                }
            });
            viewFindViewById2.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PDFFormTextEditor.this.e();
                    String selectedText = PDFFormTextEditor.this.getSelectedText();
                    if (configOptionsA.j()) {
                        k.a(selectedText);
                    }
                    PDFFormTextEditor.this.d();
                }
            });
            viewFindViewById3.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PDFFormTextEditor.this.e();
                    k.a(PDFFormTextEditor.this.getSelectedText());
                }
            });
            viewFindViewById4.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PDFFormTextEditor.this.e();
                    PDFFormTextEditor.this.d();
                    String strB = k.b(PDFFormTextEditor.this.getContext());
                    PDFFormTextEditor.this.mEditText.getText().insert(PDFFormTextEditor.this.mEditText.getSelectionStart(), strB);
                }
            });
        }
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void singleTap(float f, float f2) {
        int iA = a(f, f2);
        if (iA < 0 || iA > this.mEditText.getText().length()) {
            return;
        }
        a(iA, iA);
        showMenu();
        invalidate();
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public void start(DocMuPdfPageView docMuPdfPageView, int i, c cVar, DocView docView, h hVar, Rect rect, PDFFormEditor.EditorListener editorListener) {
        this.f = (SelectionHandle) ((Activity) getContext()).findViewById(R.id.pdf_form_text_editor_handle_upper);
        this.g = (SelectionHandle) ((Activity) getContext()).findViewById(R.id.pdf_form_text_editor_handle_lower);
        super.start(docMuPdfPageView, i, cVar, docView, hVar, rect, editorListener);
        Utilities.showKeyboard(getContext());
        this.mEditText.setOnTextChangeListener(new SOEditTextOnTextChangeListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.1
            @Override // com.artifex.sonui.editor.SOEditTextOnTextChangeListener
            public void onTextChanged(CharSequence charSequence) {
                PDFFormTextEditor.this.setWidgetText(charSequence.toString());
            }
        });
        SOEditTextOnEditorActionListener sOEditTextOnEditorActionListener = new SOEditTextOnEditorActionListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.6
            @Override // com.artifex.sonui.editor.SOEditTextOnEditorActionListener
            public boolean onEditorAction(SOEditText sOEditText, int i2, KeyEvent keyEvent) {
                if (i2 != 6) {
                    return false;
                }
                if (!PDFFormTextEditor.this.stop()) {
                    return true;
                }
                PDFFormTextEditor.this.mEditorListener.onStopped();
                return false;
            }
        };
        this.e = sOEditTextOnEditorActionListener;
        this.mEditText.setOnEditorActionListener(sOEditTextOnEditorActionListener);
        this.mEditText.setOnKeyListener(new OnKeyListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.7
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 0) {
                    boolean z = i2 == 61;
                    if (!PDFFormTextEditor.this.mWidget.h() && i2 == 66) {
                        z = true;
                    }
                    if (z && PDFFormTextEditor.this.stop()) {
                        PDFFormTextEditor.this.mEditorListener.onStopped();
                        return true;
                    }
                }
                return false;
            }
        });
        a();
        invalidate();
    }

    @Override // com.artifex.sonui.editor.PDFFormEditor
    public boolean stop() {
        if (this.mStopped) {
            return true;
        }
        e();
        if (!c()) {
            return false;
        }
        super.stop();
        SelectionHandle selectionHandle = this.f;
        if (selectionHandle != null) {
            selectionHandle.hide();
        }
        SelectionHandle selectionHandle2 = this.g;
        if (selectionHandle2 != null) {
            selectionHandle2.hide();
        }
        SOEditText sOEditText = this.mEditText;
        if (sOEditText != null) {
            sOEditText.setOnTextChangeListener(null);
            this.mEditText.setOnEditorActionListener((SOEditTextOnEditorActionListener) null);
            this.mEditText.setOnKeyListener(null);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(Rect rect) {
        Rect rect2 = new Rect(this.mWidgetBounds);
        Rect rect3 = new Rect(rect);
        return new Rect(rect2).intersect(rect3) && rect3.left >= rect2.left && rect3.right <= rect2.right;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        b(this.c, this.d);
    }

    private Rect b(Rect rect) {
        Rect rect2 = new Rect(rect);
        double factor = this.mPageView.getFactor();
        double d = rect2.left;
        Double.isNaN(d);
        rect2.left = (int) e6.z(d, d, d, factor);
        double d2 = rect2.top;
        Double.isNaN(d2);
        rect2.top = (int) e6.z(d2, d2, d2, factor);
        double d3 = rect2.right;
        Double.isNaN(d3);
        rect2.right = (int) e6.z(d3, d3, d3, factor);
        double d4 = rect2.bottom;
        Double.isNaN(d4);
        rect2.bottom = (int) e6.z(d4, d4, d4, factor);
        Rect rect3 = this.mWidgetBounds;
        rect2.offset(rect3.left, rect3.top);
        return rect2;
    }

    private Rect a(Rect rect) {
        Rect rect2 = new Rect(rect);
        double factor = this.mPageView.getFactor();
        Rect rect3 = this.mWidgetBounds;
        rect2.offset(-rect3.left, -rect3.top);
        double d = rect2.left;
        Double.isNaN(d);
        rect2.left = (int) e6.a(d, d, d, factor);
        double d2 = rect2.top;
        Double.isNaN(d2);
        rect2.top = (int) e6.a(d2, d2, d2, factor);
        double d3 = rect2.right;
        Double.isNaN(d3);
        rect2.right = (int) e6.a(d3, d3, d3, factor);
        double d4 = rect2.bottom;
        Double.isNaN(d4);
        rect2.bottom = (int) e6.a(d4, d4, d4, factor);
        return rect2;
    }

    private void a() {
        this.f.setSelectionHandleListener(new SelectionHandle.SelectionHandleListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.8
            @Override // com.artifex.sonui.editor.SelectionHandle.SelectionHandleListener
            public void onDrag(SelectionHandle selectionHandle) {
                Point point = selectionHandle.getPoint();
                PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                pDFFormTextEditor.a(pDFFormTextEditor.f.getKind(), point);
            }

            @Override // com.artifex.sonui.editor.SelectionHandle.SelectionHandleListener
            public void onEndDrag(SelectionHandle selectionHandle) {
                PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                pDFFormTextEditor.f4701a = false;
                pDFFormTextEditor.b();
                PDFFormTextEditor.this.showMenu();
            }

            @Override // com.artifex.sonui.editor.SelectionHandle.SelectionHandleListener
            public void onStartDrag(SelectionHandle selectionHandle) {
                PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                pDFFormTextEditor.f4701a = true;
                pDFFormTextEditor.e();
            }
        });
        this.g.setSelectionHandleListener(new SelectionHandle.SelectionHandleListener() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.9
            @Override // com.artifex.sonui.editor.SelectionHandle.SelectionHandleListener
            public void onDrag(SelectionHandle selectionHandle) {
                Point point = selectionHandle.getPoint();
                PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                pDFFormTextEditor.a(pDFFormTextEditor.g.getKind(), point);
            }

            @Override // com.artifex.sonui.editor.SelectionHandle.SelectionHandleListener
            public void onEndDrag(SelectionHandle selectionHandle) {
                PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                pDFFormTextEditor.f4701a = false;
                pDFFormTextEditor.b();
                PDFFormTextEditor.this.showMenu();
            }

            @Override // com.artifex.sonui.editor.SelectionHandle.SelectionHandleListener
            public void onStartDrag(SelectionHandle selectionHandle) {
                PDFFormTextEditor pDFFormTextEditor = PDFFormTextEditor.this;
                pDFFormTextEditor.f4701a = true;
                pDFFormTextEditor.e();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, int i2) {
        this.mEditText.setSelection(i, i2);
        b(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Point point) {
        int selectionStart = this.mEditText.getSelectionStart();
        int selectionEnd = this.mEditText.getSelectionEnd();
        int iA = a(point.x, point.y);
        if (iA >= 0) {
            if (i == 1) {
                if (point.y < 0) {
                    iA = 0;
                }
                int i2 = selectionEnd - 1;
                if (iA >= i2) {
                    iA = i2;
                }
                a(iA, selectionEnd);
            } else {
                if (i != 2) {
                    return;
                }
                int i3 = selectionStart + 1;
                if (iA <= i3) {
                    iA = i3;
                }
                a(selectionStart, Math.min(iA, this.mEditText.getText().toString().length()));
            }
            invalidate();
        }
    }

    private int a(float f, float f2) {
        int i;
        Rect[] rectArr = this.f4702b;
        if (rectArr == null || rectArr.length <= 0) {
            return -1;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            Rect[] rectArr2 = this.f4702b;
            if (i3 >= rectArr2.length) {
                if (rectArr2.length > 0) {
                    i = -1;
                    while (true) {
                        Rect[] rectArr3 = this.f4702b;
                        if (i2 >= rectArr3.length) {
                            break;
                        }
                        int i4 = rectArr3[i2].left;
                        Rect rect = this.f4702b[i2];
                        Rect rectA = a(new Rect(i4, rect.top, rect.right, rect.bottom));
                        if (f2 >= rectA.top && f2 <= rectA.bottom) {
                            i = i2;
                        }
                        i2++;
                    }
                } else {
                    i = -1;
                }
                return i != -1 ? i : this.f4702b.length - 1;
            }
            int i5 = rectArr2[i3].left;
            Rect rect2 = this.f4702b[i3];
            if (a(new Rect(i5, rect2.top, rect2.right, rect2.bottom)).contains((int) f, (int) f2)) {
                return i3;
            }
            i3++;
        }
    }

    private void a(final Runnable runnable) {
        this.mDoc.d().a(new r.a() { // from class: com.artifex.sonui.editor.PDFFormTextEditor.11

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            Rect[] f4705a;

            @Override // com.artifex.solib.r.a
            public void a() {
                this.f4705a = PDFFormTextEditor.this.mWidget.e();
            }

            @Override // com.artifex.solib.r.a, java.lang.Runnable
            public void run() {
                Rect rect;
                String string = PDFFormTextEditor.this.mEditText.getText().toString();
                ArrayList arrayList = new ArrayList();
                int i = 0;
                String str = "";
                for (char c : string.toCharArray()) {
                    Rect[] rectArr = this.f4705a;
                    if (i >= rectArr.length) {
                        break;
                    }
                    if (c == '\n' || c == '\r') {
                        if (i == 0) {
                            Rect rect2 = this.f4705a[i];
                            int i2 = rect2.left;
                            rect = new Rect(i2, rect2.top, i2, rect2.bottom);
                        } else {
                            int i3 = i - 1;
                            int i4 = rectArr[i3].right;
                            Rect rect3 = this.f4705a[i3];
                            rect = new Rect(i4, rect3.top, rect3.right, rect3.bottom);
                        }
                        arrayList.add(rect);
                        str = str + c;
                    } else {
                        if (i < rectArr.length && PDFFormTextEditor.this.c(rectArr[i])) {
                            arrayList.add(this.f4705a[i]);
                            str = str + c;
                        }
                        i++;
                    }
                }
                while (true) {
                    Rect[] rectArr2 = this.f4705a;
                    if (i >= rectArr2.length) {
                        break;
                    }
                    arrayList.add(rectArr2[i]);
                    i++;
                }
                PDFFormTextEditor.this.f4702b = (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                if (str.equals(string)) {
                    return;
                }
                int selectionStart = PDFFormTextEditor.this.mEditText.getSelectionStart();
                int selectionEnd = PDFFormTextEditor.this.mEditText.getSelectionEnd();
                PDFFormTextEditor.this.mEditText.setText(str);
                if (selectionStart == selectionEnd) {
                    if (selectionStart > str.length()) {
                        selectionStart = str.length();
                    }
                    PDFFormTextEditor.this.a(selectionStart, selectionStart);
                }
            }
        });
    }
}
