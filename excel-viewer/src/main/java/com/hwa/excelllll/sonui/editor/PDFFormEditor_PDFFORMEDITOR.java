package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.artifex.solib.c;
import com.artifex.solib.h;

/* JADX INFO: loaded from: classes.dex */
public class PDFFormEditor extends RelativeLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ViewTreeObserver f4694a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private ViewTreeObserver.OnGlobalLayoutListener f4695b;
    protected c mDoc;
    protected DocView mDocView;
    protected boolean mDocViewAtRest;
    protected SOEditText mEditText;
    protected EditorListener mEditorListener;
    protected int mPageNumber;
    protected DocMuPdfPageView mPageView;
    protected boolean mStopped;
    protected h mWidget;
    protected Rect mWidgetBounds;

    public interface EditorListener {
        void onStopped();
    }

    public PDFFormEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDoc = null;
        this.mDocView = null;
        this.mEditText = null;
        this.mWidget = null;
        this.mPageView = null;
        this.mEditorListener = null;
        this.mWidgetBounds = null;
        this.mDocViewAtRest = true;
        this.mStopped = false;
        setWillNotDraw(false);
    }

    private void a() {
        if (getVisibility() != 0) {
            return;
        }
        Rect rect = new Rect();
        rect.set(this.mWidgetBounds);
        this.mPageView.pageToView(rect, rect);
        rect.offset(this.mPageView.getLeft(), this.mPageView.getTop());
        rect.offset(-this.mDocView.getScrollX(), -this.mDocView.getScrollY());
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.leftMargin = rect.left;
        layoutParams.topMargin = rect.top;
        setLayoutParams(layoutParams);
        this.mEditText.setLayoutParams(new LayoutParams(rect.width(), rect.height()));
        invalidate();
    }

    public SOEditText getEditText() {
        return null;
    }

    public void onGlobalLayout() {
        this.mDocViewAtRest = this.mDocView.isAtRest();
        invalidate();
    }

    public void onRenderComplete() {
        a();
        invalidate();
    }

    public void scrollIntoView() {
        this.mDocView.scrollBoxIntoView(this.mPageNumber, new RectF(this.mWidgetBounds), true);
    }

    public void show() {
        setVisibility(0);
        this.mEditText.requestFocus();
        a();
    }

    public void start(DocMuPdfPageView docMuPdfPageView, int i, c cVar, DocView docView, h hVar, Rect rect, EditorListener editorListener) {
        this.mDoc = cVar;
        this.mWidget = hVar;
        this.mPageView = docMuPdfPageView;
        this.mDocView = docView;
        this.mEditText = getEditText();
        this.mEditorListener = editorListener;
        this.mPageNumber = i;
        this.mStopped = false;
        this.mWidgetBounds = new Rect(rect);
        setupInput();
        show();
        setInitialValue();
        this.f4694a = this.mDocView.getViewTreeObserver();
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.PDFFormEditor.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                this.onGlobalLayout();
            }
        };
        this.f4695b = onGlobalLayoutListener;
        this.f4694a.addOnGlobalLayoutListener(onGlobalLayoutListener);
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.artifex.sonui.editor.PDFFormEditor.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                PDFFormEditor.this.doubleTap(motionEvent.getX(), motionEvent.getY());
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                PDFFormEditor.this.singleTap(motionEvent.getX(), motionEvent.getY());
                return true;
            }
        });
        this.mEditText.setOnTouchListener(new OnTouchListener() { // from class: com.artifex.sonui.editor.PDFFormEditor.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    public boolean stop() {
        this.mStopped = true;
        this.mPageView.invalidate();
        SOEditText sOEditText = this.mEditText;
        if (sOEditText != null) {
            sOEditText.clearFocus();
            this.mEditText.setOnTouchListener(null);
        }
        c cVar = this.mDoc;
        if (cVar != null) {
            cVar.a((c.a) null);
        }
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.setShowKeyboardListener(null);
        }
        ViewTreeObserver viewTreeObserver = this.f4694a;
        if (viewTreeObserver != null) {
            viewTreeObserver.removeOnGlobalLayoutListener(this.f4695b);
            this.f4694a = null;
            this.f4695b = null;
        }
        setVisibility(8);
        return true;
    }

    public void scrollCaretIntoView() {
    }

    public void setInitialValue() {
    }

    public void setupInput() {
    }

    public void doubleTap(float f, float f2) {
    }

    public void singleTap(float f, float f2) {
    }
}
