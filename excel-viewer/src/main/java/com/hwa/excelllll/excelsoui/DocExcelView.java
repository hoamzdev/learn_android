package com.hwa.excelllll.excelsoui;

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

import com.hwa.excelllll.R;
import com.hwa.excelllll.excelsolib.SODoc;
import com.hwa.excelllll.excelsolib.SOPage;
import com.hwa.excelllll.excelsolib.SOSelectionTableRange;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DocExcelView extends DocView implements SOEditTextOnEditorActionListener {
    Map<String, Integer> horizontalScrollBySheet;
    Map<String, Integer> verticalScrollBySheet;
    Map<String, Float> zoomBySheet;
    private Context activityContext;
    private HorizontalRuler horizontalRuler;
    private VerticalRuler verticalRuler;
    private SOTextView formulaButton;
    private SOEditText formulaInput;
    private boolean tapPending;
    private int currentSheet;
    private boolean sheetChanged;
    private boolean restoreSheetState;
    private DocPageView cachedPageView;
    private Runnable pendingTapAction;

    public DocExcelView(Context context) {
        super(context);
        this.horizontalRuler = null;
        this.verticalRuler = null;
        this.tapPending = false;
        this.currentSheet = 0;
        this.horizontalScrollBySheet = new HashMap<>();
        this.verticalScrollBySheet = new HashMap<>();
        this.zoomBySheet = new HashMap<>();
        this.sheetChanged = false;
        this.restoreSheetState = true;
        this.cachedPageView = null;
        this.pendingTapAction = null;
    }

    private float calculateInitialScaleForSheet(int sheetIndex) {
        DocPageView docPageView = (DocPageView) getOrCreateChild(sheetIndex);
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
        return 0.15f / ((float) iPageToView / displayMetrics.densityDpi);
    }

    private void updateRulers() {
        if (getPageCount() == 0) {
            return;
        }
        if (this.horizontalRuler == null) {
            this.horizontalRuler = (HorizontalRuler) ((Activity) this.activityContext).findViewById(R.id.horizontal_ruler);
        }
        if (this.verticalRuler == null) {
            this.verticalRuler = (VerticalRuler) ((Activity) this.activityContext).findViewById(R.id.vertical_ruler);
        }
        DocPageView docPageView = (DocPageView) getOrCreateChild(0);
        if (docPageView != null) {
            this.horizontalRuler.setScale((float) docPageView.getZoomScale());
            this.horizontalRuler.setOffsetX(getScrollX());
            this.horizontalRuler.setGraduations(docPageView.getHorizontalRuler());
            this.horizontalRuler.update();
            this.verticalRuler.setScale((float) docPageView.getZoomScale());
            this.verticalRuler.setOffsetY(getScrollY());
            this.verticalRuler.setGraduations(docPageView.getVerticalRuler());
            this.verticalRuler.update();
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean clearAreaSelection() {
        return false;
    }

    public boolean copyEditTextToCell() {
        if (this.formulaInput.isEnabled()) {
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
        this.tapPending = true;
        if (copyEditTextToCell()) {
            this.pendingTapAction = new Runnable() { // from class: com.artifex.sonui.editor.DocExcelView.2
                @Override // java.lang.Runnable
                public void run() {
                    DocExcelView.this.handleSpreadsheetTap(f, f2);
                }
            };
        } else {
            handleSpreadsheetTap(f, f2);
        }
    }

    public int getCurrentSheet() {
        return this.currentSheet;
    }

    public String getEditText() {
        return this.formulaInput.getText().toString();
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
        if (this.cachedPageView == null) {
            this.cachedPageView = new DocPageView((Activity) this.activityContext, getDoc());
        }
        this.cachedPageView.setupPage(this.currentSheet, getWidth(), 1);
        return this.cachedPageView;
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
        this.activityContext = context;
        SOEditText sOEditText = (SOEditText) ((Activity) context).findViewById(R.id.text_input);
        this.formulaInput = sOEditText;
        sOEditText.setImeActionLabel(this.activityContext.getString(R.string.sodk_editor_ime_action_label_done), 66);
        this.formulaInput.setOnEditorActionListener(this);
        this.formulaButton = (SOTextView) ((Activity) this.activityContext).findViewById(R.id.fx_button);
        this.formulaInput.setCustomSelectionActionModeCallback(Utilities.editFieldDlpHandler);
    }

    public void insertEditText(String str) {
        int iMax = Math.max(this.formulaInput.getSelectionStart(), 0);
        int iMax2 = Math.max(this.formulaInput.getSelectionEnd(), 0);
        Objects.requireNonNull(this.formulaInput.getText()).replace(Math.min(iMax, iMax2), Math.max(iMax, iMax2), str, 0, str.length());
    }

    @Override // com.artifex.sonui.editor.DocView
    public float maxScale() {
        return 50.0f;
    }

    @Override // com.artifex.sonui.editor.SOEditTextOnEditorActionListener
    public boolean onEditorAction(SOEditText sOEditText, int i, KeyEvent keyEvent) {
        Utilities.hideKeyboard(getContext());
        this.formulaInput.clearFocus();
        getDoc().setSelectionText(Objects.requireNonNull(this.formulaInput.getText()).toString(), 0, true);
        return true;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onFoundText(int i, RectF rectF) {
        this.restoreSheetState = false;
        this.mHostActivity.clickSheetButton(i, true);
        this.restoreSheetState = true;
        scrollBoxIntoView(i, rectF, true);
    }

    @Override // com.artifex.sonui.editor.DocView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (finished()) {
            return;
        }
        handleStartPage();
        if (canEditText()) {
            ((LinearLayout) ((Activity) this.activityContext).findViewById(R.id.fx_bar)).setVisibility(View.VISIBLE);
        }
        updateRulers();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onLayoutChanged() {
        super.onLayoutChanged();
        Runnable runnable = this.pendingTapAction;
        if (runnable != null) {
            runnable.run();
            this.pendingTapAction = null;
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onSelectionChanged() {
        super.onSelectionChanged();
        boolean selectionCanHaveTextAltered = getDoc().getSelectionCanHaveTextAltered();
        this.formulaInput.setEnabled(selectionCanHaveTextAltered);
        this.formulaButton.setEnabled(selectionCanHaveTextAltered);
        SOEditText sOEditText = this.formulaInput;
        if (selectionCanHaveTextAltered) {
            sOEditText.setFocusableInTouchMode(true);
            if (!Utilities.isLandscapePhone(getContext()) && this.tapPending) {
                this.formulaInput.requestFocus();
                Utilities.showKeyboard(getContext());
            }
            copySelectionToEdit();
        } else {
            sOEditText.clearFocus();
            Utilities.hideKeyboard(getContext());
            this.formulaInput.setText("");
        }
        this.tapPending = false;
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

    public void setCurrentSheet(int sheetIndex) {
        if (this.currentSheet < super.getPageCount()) {
            String pageTitle = getPageTitle(this.currentSheet);
            if (this.zoomBySheet.containsKey(pageTitle)) {
                this.horizontalScrollBySheet.put(pageTitle, getScrollX());
                this.verticalScrollBySheet.put(pageTitle, getScrollY());
                this.zoomBySheet.put(pageTitle, this.mScale);
            }
        }
        if (sheetIndex == this.currentSheet && this.sheetChanged) {
            return;
        }
        this.sheetChanged = true;
        DocPageView docPageView = (DocPageView) getOrCreateChild(0);
        if (docPageView != null) {
            docPageView.stopRender();
        }
        this.currentSheet = sheetIndex;
        String pageTitle2 = getPageTitle(sheetIndex);
        Integer savedScrollX = this.horizontalScrollBySheet.get(pageTitle2);
        Integer savedScrollY = this.verticalScrollBySheet.get(pageTitle2);
        final int targetScrollX = savedScrollX != null ? savedScrollX : 0;
        final int targetScrollY = savedScrollY != null ? savedScrollY : 0;
        Float fValueOf = this.zoomBySheet.get(pageTitle2);
        if (fValueOf == null) {
            fValueOf = calculateInitialScaleForSheet(sheetIndex);
        }
        this.zoomBySheet.put(pageTitle2, fValueOf);
        clearChildViews();
        removeAllViewsInLayout();
        DocPageView docPageView2 = (DocPageView) getOrCreateChild(0);
        if (docPageView2 != null) {
            LinearLayout linearLayout = (LinearLayout) ((Activity) getContext()).findViewById(R.id.hruler_holder);
            float[] horizontalRuler = docPageView2.getHorizontalRuler();
            linearLayout.setVisibility((horizontalRuler == null || horizontalRuler.length == 0) ? View.GONE : View.VISIBLE);
            VerticalRuler verticalRuler = (VerticalRuler) ((Activity) getContext()).findViewById(R.id.vertical_ruler);
            float[] verticalRuler2 = docPageView2.getVerticalRuler();
            verticalRuler.setVisibility((verticalRuler2 == null || verticalRuler2.length == 0) ? View.GONE : View.VISIBLE);
        }
        updateDragHandles();
        this.mScale = fValueOf;
        scaleChildren();
        final boolean z = this.restoreSheetState;
        post(new Runnable() { // from class: com.artifex.sonui.editor.DocExcelView.1
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    DocExcelView.this.setScrollX(targetScrollX);
                    DocExcelView.this.setScrollY(targetScrollY);
                }
                DocExcelView.this.requestLayout();
            }
        });
    }

    public void setEditText(String str) {
        this.formulaInput.setText(str);
        SOEditText sOEditText = this.formulaInput;
        sOEditText.setSelection(Objects.requireNonNull(sOEditText.getText()).length());
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
        this.horizontalRuler = null;
        this.verticalRuler = null;
        this.tapPending = false;
        this.currentSheet = 0;
        this.horizontalScrollBySheet = new HashMap<>();
        this.verticalScrollBySheet = new HashMap<>();
        this.zoomBySheet = new HashMap<>();
        this.sheetChanged = false;
        this.restoreSheetState = true;
        this.cachedPageView = null;
        this.pendingTapAction = null;
    }

    public DocExcelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.horizontalRuler = null;
        this.verticalRuler = null;
        this.tapPending = false;
        this.currentSheet = 0;
        this.horizontalScrollBySheet = new HashMap<>();
        this.verticalScrollBySheet = new HashMap<>();
        this.zoomBySheet = new HashMap<>();
        this.sheetChanged = false;
        this.restoreSheetState = true;
        this.cachedPageView = null;
        this.pendingTapAction = null;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void scrollBoxIntoView(int i, RectF rectF, boolean z) {
        super.scrollBoxIntoView(i, rectF, z);
    }
    public void handleSpreadsheetTap(float x, float y) {
        super.doSingleTap(x, y);
        setEditText(getDoc().getSelectionAsText());
    }
}
