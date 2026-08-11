package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOSelectionTableRange;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DocExcelView extends DocView implements SOEditTextOnEditorActionListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Map<String, Integer> f4528a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    Map<String, Integer> f4529b;
    Map<String, Float> c;
    private Context d;
    private HorizontalRuler e;
    private VerticalRuler f;
    private SOTextView g;
    private SOEditText h;
    private boolean i;
    private int j;
    private boolean k;
    private boolean l;
    private DocPageView m;
    private Runnable n;

    public DocExcelView(Context context) {
        super(context);
        this.e = null;
        this.f = null;
        this.i = false;
        this.j = 0;
        this.f4528a = new HashMap();
        this.f4529b = new HashMap();
        this.c = new HashMap();
        this.k = false;
        this.l = true;
        this.m = null;
        this.n = null;
    }

    private float a(int i) {
        DocPageView docPageView = (DocPageView) getOrCreateChild(i);
        float[] verticalRuler = docPageView.getVerticalRuler();
        if (verticalRuler == null || verticalRuler.length < 2) {
            float fMax = Math.max(docPageView.getPage().sizeAtZoom(1.0d).x / 786.0f, 1.0f);
            return Utilities.isPhoneDevice(getContext()) ? fMax * 2.0f : fMax;
        }
        int iMin = Math.min(verticalRuler.length, 101);
        float f = 0.0f;
        for (int i2 = 1; i2 < iMin; i2++) {
            f += verticalRuler[i2] - verticalRuler[i2 - 1];
        }
        int iPageToView = docPageView.pageToView((int) (f / (iMin - 1)));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return 0.15f / (iPageToView / displayMetrics.densityDpi);
    }

    private void b() {
        if (getPageCount() == 0) {
            return;
        }
        if (this.e == null) {
            this.e = (HorizontalRuler) ((Activity) this.d).findViewById(R.id.horizontal_ruler);
        }
        if (this.f == null) {
            this.f = (VerticalRuler) ((Activity) this.d).findViewById(R.id.vertical_ruler);
        }
        DocPageView docPageView = (DocPageView) getOrCreateChild(0);
        if (docPageView != null) {
            this.e.setScale((float) docPageView.getZoomScale());
            this.e.setOffsetX(getScrollX());
            this.e.setGraduations(docPageView.getHorizontalRuler());
            this.e.update();
            this.f.setScale((float) docPageView.getZoomScale());
            this.f.setOffsetY(getScrollY());
            this.f.setGraduations(docPageView.getVerticalRuler());
            this.f.update();
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean clearAreaSelection() {
        return false;
    }

    public boolean copyEditTextToCell() {
        if (this.h.isEnabled()) {
            String selectionAsText = getDoc().getSelectionAsText();
            if (selectionAsText == null) {
                selectionAsText = "";
            }
            String editText = getEditText();
            String str = editText != null ? editText : "";
            if (!str.equals(selectionAsText)) {
                getDoc().setSelectionText(str, 0, true);
                return true;
            }
        }
        return false;
    }

    public void copySelectionToEdit() {
        SODoc doc = getDoc();
        SOSelectionTableRange sOSelectionTableRangeSelectionTableRange = doc.selectionTableRange();
        if (sOSelectionTableRangeSelectionTableRange != null) {
            setEditText((sOSelectionTableRangeSelectionTableRange.rowCount() == 1 && sOSelectionTableRangeSelectionTableRange.columnCount() == 1) ? doc.getSelectionAsText() : "");
        }
    }

    public int countColumns() {
        return ((DocPageView) getOrCreateChild(0)).getHorizontalRuler().length - 1;
    }

    public int countRows() {
        return ((DocPageView) getOrCreateChild(0)).getVerticalRuler().length - 1;
    }

    public void deleteSelectedColumns() {
        SODoc doc = getDoc();
        SOSelectionTableRange sOSelectionTableRangeSelectionTableRange = doc.selectionTableRange();
        int iFirstColumn = sOSelectionTableRangeSelectionTableRange.firstColumn();
        int iColumnCount = sOSelectionTableRangeSelectionTableRange.columnCount();
        int iCountColumns = countColumns();
        doc.deleteColumns();
        if (((iFirstColumn + 1) + iColumnCount) - 1 >= iCountColumns) {
            doc.clearSelection();
        }
    }

    public void deleteSelectedRows() {
        SODoc doc = getDoc();
        SOSelectionTableRange sOSelectionTableRangeSelectionTableRange = doc.selectionTableRange();
        int iFirstRow = sOSelectionTableRangeSelectionTableRange.firstRow();
        int iRowCount = sOSelectionTableRangeSelectionTableRange.rowCount();
        int iCountRows = countRows();
        doc.deleteRows();
        if (((iFirstRow + 1) + iRowCount) - 1 >= iCountRows) {
            doc.clearSelection();
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void doSingleTap(final float f, final float f2) {
        this.i = true;
        if (copyEditTextToCell()) {
            this.n = new Runnable() { // from class: com.artifex.sonui.editor.DocExcelView.2
                @Override // java.lang.Runnable
                public void run() {
                    DocExcelView.this.a(f, f2);
                }
            };
        } else {
            a(f, f2);
        }
    }

    public int getCurrentSheet() {
        return this.j;
    }

    public String getEditText() {
        return this.h.getText().toString();
    }

    @Override // com.artifex.sonui.editor.DocView
    public int getPageCount() {
        return super.getPageCount() == 0 ? 0 : 1;
    }

    public String getPageTitle(int i) {
        SOPage page;
        DocPageView docPageView = (DocPageView) super.getViewFromAdapter(i);
        return (docPageView == null || (page = docPageView.getPage()) == null) ? "" : page.getPageTitle();
    }

    @Override // com.artifex.sonui.editor.DocView
    public View getViewFromAdapter(int i) {
        if (this.m == null) {
            this.m = new DocPageView((Activity) this.d, getDoc());
        }
        this.m.setupPage(this.j, getWidth(), 1);
        return this.m;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void handleStartPage() {
        if (getStartPage() > 0) {
            setCurrentSheet(getStartPage() - 1);
            setStartPage(0);
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void initialize(Context context) {
        super.initialize(context);
        this.d = context;
        SOEditText sOEditText = (SOEditText) ((Activity) context).findViewById(R.id.text_input);
        this.h = sOEditText;
        sOEditText.setImeActionLabel(this.d.getString(R.string.sodk_editor_ime_action_label_done), 66);
        this.h.setOnEditorActionListener(this);
        this.g = (SOTextView) ((Activity) this.d).findViewById(R.id.fx_button);
        this.h.setCustomSelectionActionModeCallback(Utilities.editFieldDlpHandler);
    }

    public void insertEditText(String str) {
        int iMax = Math.max(this.h.getSelectionStart(), 0);
        int iMax2 = Math.max(this.h.getSelectionEnd(), 0);
        this.h.getText().replace(Math.min(iMax, iMax2), Math.max(iMax, iMax2), str, 0, str.length());
    }

    @Override // com.artifex.sonui.editor.DocView
    public float maxScale() {
        return 50.0f;
    }

    @Override // com.artifex.sonui.editor.SOEditTextOnEditorActionListener
    public boolean onEditorAction(SOEditText sOEditText, int i, KeyEvent keyEvent) {
        Utilities.hideKeyboard(getContext());
        this.h.clearFocus();
        getDoc().setSelectionText(this.h.getText().toString(), 0, true);
        return true;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onFoundText(int i, RectF rectF) {
        this.l = false;
        this.mHostActivity.clickSheetButton(i, true);
        this.l = true;
        scrollBoxIntoView(i, rectF, true);
    }

    @Override // com.artifex.sonui.editor.DocView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (finished()) {
            return;
        }
        handleStartPage();
        if (canEditText() && !NUIDocView.currentNUIDocView().isFullScreen()) {
            ((LinearLayout) ((Activity) this.d).findViewById(R.id.fx_bar)).setVisibility(0);
        }
        b();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onLayoutChanged() {
        super.onLayoutChanged();
        Runnable runnable = this.n;
        if (runnable != null) {
            runnable.run();
            this.n = null;
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onSelectionChanged() {
        super.onSelectionChanged();
        boolean selectionCanHaveTextAltered = getDoc().getSelectionCanHaveTextAltered();
        this.h.setEnabled(selectionCanHaveTextAltered);
        this.g.setEnabled(selectionCanHaveTextAltered);
        SOEditText sOEditText = this.h;
        if (selectionCanHaveTextAltered) {
            sOEditText.setFocusableInTouchMode(true);
            if (!NUIDocView.currentNUIDocView().isLandscapePhone() && this.i) {
                this.h.requestFocus();
                Utilities.showKeyboard(getContext());
            }
            copySelectionToEdit();
        } else {
            sOEditText.clearFocus();
            Utilities.hideKeyboard(getContext());
            this.h.setText("");
        }
        this.i = false;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onShowKeyboard(boolean z) {
        if (!z) {
            SODoc doc = getDoc();
            SOSelectionTableRange sOSelectionTableRangeSelectionTableRange = doc != null ? doc.selectionTableRange() : null;
            if (sOSelectionTableRangeSelectionTableRange != null && sOSelectionTableRangeSelectionTableRange.rowCount() == 1 && sOSelectionTableRangeSelectionTableRange.columnCount() == 1) {
                copyEditTextToCell();
            }
        }
        super.onShowKeyboard(z);
    }

    @Override // com.artifex.sonui.editor.DocView
    public void scrollBoxIntoView(int i, RectF rectF) {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        rect.offset(0, -rect.top);
        Point pointPageToView = ((DocPageView) getOrCreateChild(i)).pageToView((int) rectF.left, (int) rectF.bottom);
        int scrollY = pointPageToView.y - getScrollY();
        pointPageToView.y = scrollY;
        int i2 = rect.top;
        if (scrollY < i2 || scrollY > rect.bottom) {
            smoothScrollBy(0, ((i2 + rect.bottom) / 2) - scrollY);
        }
    }

    public void scrollSelectedCellAboveKeyboard() {
        DocPageView docPageView = (DocPageView) getOrCreateChild(getCurrentSheet());
        Rect rectPageToScreen = docPageView.pageToScreen(docPageView.selectionLimits().getBox());
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int keyboardHeight = getResources().getDisplayMetrics().heightPixels - this.mHostActivity.getKeyboardHeight();
        rect.bottom = keyboardHeight;
        int i = rectPageToScreen.top;
        int i2 = rect.top;
        if (i < i2 || rectPageToScreen.bottom > keyboardHeight) {
            smoothScrollBy(0, (((i2 + keyboardHeight) / 2) - (rectPageToScreen.height() / 2)) - ((i + rectPageToScreen.bottom) / 2));
        }
    }

    public void setCurrentSheet(int i) {
        final Integer num = 0;
        if (this.j < super.getPageCount()) {
            String pageTitle = getPageTitle(this.j);
            if (this.c.containsKey(pageTitle)) {
                this.f4528a.put(pageTitle, Integer.valueOf(getScrollX()));
                this.f4529b.put(pageTitle, Integer.valueOf(getScrollY()));
                this.c.put(pageTitle, Float.valueOf(this.mScale));
            }
        }
        if (i == this.j && this.k) {
            return;
        }
        this.k = true;
        DocPageView docPageView = (DocPageView) getOrCreateChild(0);
        if (docPageView != null) {
            docPageView.stopRender();
        }
        this.j = i;
        String pageTitle2 = getPageTitle(i);
        final Integer num2 = this.f4528a.get(pageTitle2);
        if (num2 == null) {
            num2 = num;
        }
        Integer num3 = this.f4529b.get(pageTitle2);
        num = num3 != null ? num3 : 0;
        Float fValueOf = this.c.get(pageTitle2);
        if (fValueOf == null) {
            fValueOf = Float.valueOf(a(i));
        }
        this.c.put(pageTitle2, fValueOf);
        clearChildViews();
        removeAllViewsInLayout();
        DocPageView docPageView2 = (DocPageView) getOrCreateChild(0);
        if (docPageView2 != null) {
            LinearLayout linearLayout = (LinearLayout) ((Activity) getContext()).findViewById(R.id.hruler_holder);
            float[] horizontalRuler = docPageView2.getHorizontalRuler();
            linearLayout.setVisibility((horizontalRuler == null || horizontalRuler.length <= 0) ? 8 : 0);
            VerticalRuler verticalRuler = (VerticalRuler) ((Activity) getContext()).findViewById(R.id.vertical_ruler);
            float[] verticalRuler2 = docPageView2.getVerticalRuler();
            verticalRuler.setVisibility((verticalRuler2 == null || verticalRuler2.length <= 0) ? 8 : 0);
        }
        updateDragHandles();
        this.mScale = fValueOf.floatValue();
        scaleChildren();
        final boolean z = this.l;
        post(new Runnable() { // from class: com.artifex.sonui.editor.DocExcelView.1
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    DocExcelView.this.setScrollX(num2.intValue());
                    DocExcelView.this.setScrollY(num.intValue());
                }
                DocExcelView.this.requestLayout();
            }
        });
    }

    public void setEditText(String str) {
        this.h.setText(str);
        SOEditText sOEditText = this.h;
        sOEditText.setSelection(sOEditText.getText().length());
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean shouldLayout() {
        return true;
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean tapToFocus() {
        return false;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void focusInputView() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onLongPressRelease() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void resetInputView() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void setupNoteEditor() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void updateReview() {
    }

    @Override // com.artifex.sonui.editor.DocView, android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onLongPressMoving(MotionEvent motionEvent) {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void showKeyboardAndScroll(Point point) {
    }

    public DocExcelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = null;
        this.f = null;
        this.i = false;
        this.j = 0;
        this.f4528a = new HashMap();
        this.f4529b = new HashMap();
        this.c = new HashMap();
        this.k = false;
        this.l = true;
        this.m = null;
        this.n = null;
    }

    public DocExcelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = null;
        this.f = null;
        this.i = false;
        this.j = 0;
        this.f4528a = new HashMap();
        this.f4529b = new HashMap();
        this.c = new HashMap();
        this.k = false;
        this.l = true;
        this.m = null;
        this.n = null;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void scrollBoxIntoView(int i, RectF rectF, boolean z) {
        super.scrollBoxIntoView(i, rectF, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(float f, float f2) {
        super.doSingleTap(f, f2);
        setEditText(getDoc().getSelectionAsText());
    }
}
