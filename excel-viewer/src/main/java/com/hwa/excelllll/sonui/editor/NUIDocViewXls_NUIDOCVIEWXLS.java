package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.SOSelectionTableRange;
import com.xlsxreader.excelreader.customviews.EditBtn;
import defpackage.h;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class NUIDocViewXls extends NUIDocView {
    private int E;
    private boolean G;
    private boolean H;
    private ListPopupWindow I;
    private String J;
    private boolean K;
    private SOTextView btnFx;

    public NUIDocViewXls(Context context) {
        super(context);
        this.E = -1;
        this.G = false;
        this.H = false;
        this.I = null;
        this.J = null;
        this.K = false;
        a(context);
    }

    private int a(ListAdapter listAdapter) {
        int iMakeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int measuredHeight = 0;
        int i = 0;
        FrameLayout frameLayout = null;
        View view = null;
        for (int i2 = 0; i2 < count; i2++) {
            int itemViewType = listAdapter.getItemViewType(i2);
            if (itemViewType != i) {
                view = null;
                i = itemViewType;
            }
            if (frameLayout == null) {
                frameLayout = new FrameLayout(getContext());
            }
            view = listAdapter.getView(i2, view, frameLayout);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            measuredHeight += view.getMeasuredHeight();
        }
        return measuredHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(activity());
        listPopupWindow.setBackgroundDrawable(activity().getDrawable(com.xlsxreader.excelreader.R.drawable.sodk_editor_formula_popup));
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(this.btnFx);
        listPopupWindow.setHorizontalOffset(30);
        listPopupWindow.setVerticalOffset(30);
        final ChooseFormulaAdapter chooseFormulaAdapter = new ChooseFormulaAdapter(activity(), str);
        listPopupWindow.setAdapter(chooseFormulaAdapter);
        Point screenSize = Utilities.getScreenSize(getContext());
        listPopupWindow.setContentWidth(screenSize.x / 2);
        int[] iArr = new int[2];
        this.btnFx.getLocationOnScreen(iArr);
        listPopupWindow.setHeight(Math.min(((screenSize.y - Utilities.screenToWindow(iArr, getContext())[1]) - this.btnFx.getHeight()) - 60, a(chooseFormulaAdapter)));
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                listPopupWindow.dismiss();
                DocExcelView docExcelView = (DocExcelView) NUIDocViewXls.this.getDocView();
                String editText = docExcelView.getEditText();
                String item = chooseFormulaAdapter.getItem(i);
                if (editText == null || editText.isEmpty()) {
                    item = h.i("=", item);
                }
                docExcelView.insertEditText(item);
                docExcelView.copyEditTextToCell();
            }
        });
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.3
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                NUIDocViewXls.this.I = null;
                NUIDocViewXls.this.J = null;
            }
        });
        listPopupWindow.show();
        this.I = listPopupWindow;
        this.J = str;
        listPopupWindow.getListView().setDivider(null);
        listPopupWindow.getListView().setVerticalScrollBarEnabled(true);
    }

    private void d() {
        ListPopupWindow listPopupWindow = this.I;
        if (listPopupWindow != null) {
            String str = this.J;
            listPopupWindow.dismiss();
            a(str);
        }
    }

    private void e() {
        SOSelectionTableRange sOSelectionTableRangeSelectionTableRange;
        if (getDoc() == null || (sOSelectionTableRangeSelectionTableRange = getDoc().selectionTableRange()) == null || sOSelectionTableRangeSelectionTableRange.rowCount() != 1 || sOSelectionTableRangeSelectionTableRange.columnCount() != 1) {
            return;
        }
        ((DocExcelView) getDocView()).setEditText(getDoc().getSelectionAsText());
    }

    private int getCurrentSheet() {
        DocExcelView docExcelView = (DocExcelView) getDocView();
        if (docExcelView != null) {
            return docExcelView.getCurrentSheet();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentSheet(int i) {
        DocExcelView docExcelView;
        if (i != getCurrentSheet() && (docExcelView = (DocExcelView) getDocView()) != null) {
            docExcelView.copyEditTextToCell();
            getDoc().clearSelection();
            docExcelView.setEditText("");
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.excel_sheets_bar);
        if (getDoc() != null) {
            int iR = getDoc().r();
            int i2 = 0;
            while (i2 < iR) {
                View childAt = linearLayout.getChildAt(i2);
                if (childAt != null) {
                    childAt.setSelected(i2 == i);
                    if (iR == 1) {
                        ((SheetTab2) childAt).showXView(false);
                    }
                }
                i2++;
            }
        }
        if (((DocExcelView) getDocView()) != null) {
            ((DocExcelView) getDocView()).setCurrentSheet(i);
        }
        onSelectionChanged();
        setPageNumberText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpSheetBar() {
        final LinearLayout linearLayout = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.excel_sheets_bar);
        linearLayout.removeAllViews();
        SheetTab2.setEditingEbabled(this.mConfigOptions.c());
        int iR = getDoc().r();
        final Activity activity = activity();
        if (this.mConfigOptions.c()) {
            TextView textView = (TextView) findViewById(com.xlsxreader.excelreader.R.id.btnAdd);
            textView.setText("+");
            textView.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    NUIDocViewXls.this.G = true;
                    if (NUIDocViewXls.this.getDoc() != null) {
                        NUIDocViewXls.this.getDoc().addBlankPage(NUIDocViewXls.this.getDoc().r());
                    }
                }
            });
        }
        for (int i = 1; i <= iR; i++) {
            int i2 = i - 1;
            final String pageTitle = ((DocExcelView) getDocView()).getPageTitle(i2);
            SheetTab2 sheetTab2 = new SheetTab2(activity);
            sheetTab2.setText(pageTitle);
            sheetTab2.setSheetNumber(i2);
            sheetTab2.setOnClickTab(new OnClickListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    NUIDocViewXls.this.a(((SheetTab2) view).getSheetNumber());
                }
            });
            sheetTab2.setOnLongClickTab(new OnLongClickListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.6
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    int sheetNumber = ((SheetTab2) view).getSheetNumber();
                    View childAt = linearLayout.getChildAt(sheetNumber);
                    NUIDocViewXls.this.E = sheetNumber;
                    String str = pageTitle;
                    view.startDragAndDrop(ClipData.newPlainText(str, str), new DragShadowBuilder(childAt), null, 0);
                    return true;
                }
            });
            linearLayout.setOnDragListener(new OnDragListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.7
                private int c = -1;

                private void a(int i3) {
                    this.c = i3;
                    int i4 = 0;
                    while (true) {
                        boolean z = true;
                        if (i4 >= linearLayout.getChildCount() - 1) {
                            return;
                        }
                        SheetTab2 sheetTab3 = (SheetTab2) linearLayout.getChildAt(i4);
                        if (i4 != this.c || i4 == NUIDocViewXls.this.E) {
                            z = false;
                        }
                        sheetTab3.setHighlight(z);
                        i4++;
                    }
                }

                @Override // android.view.View.OnDragListener
                public boolean onDrag(View view, DragEvent dragEvent) {
                    int action = dragEvent.getAction();
                    int i3 = -1;
                    if (action == 2) {
                        for (int i4 = 0; i4 < linearLayout.getChildCount() - 1; i4++) {
                            SheetTab2 sheetTab3 = (SheetTab2) linearLayout.getChildAt(i4);
                            if (dragEvent.getX() > sheetTab3.getLeft() && dragEvent.getX() < sheetTab3.getRight()) {
                                i3 = i4;
                                break;
                            }
                        }
                    } else {
                        if (action == 3) {
                            int i5 = this.c;
                            if (i5 != -1 && i5 != NUIDocViewXls.this.E) {
                                NUIDocViewXls.this.getDoc().movePage(NUIDocViewXls.this.E, this.c);
                                NUIDocViewXls.this.setUpSheetBar();
                                NUIDocViewXls.this.setCurrentSheet(this.c);
                                NUIDocViewXls.this.setSearchStart();
                            }
                        } else if (action != 4) {
                            if (action != 5 && action != 6) {
                                return true;
                            }
                        }
                        NUIDocViewXls.this.E = -1;
                    }
                    a(i3);
                    return true;
                }
            });
            if (this.mConfigOptions.c()) {
                sheetTab2.setOnClickDelete(new OnClickListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.8
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        SheetTab2 sheetTab3 = (SheetTab2) view;
                        String text = sheetTab3.getText();
                        final int sheetNumber = sheetTab3.getSheetNumber();
                        Utilities.yesNoMessage(activity, NUIDocViewXls.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_delete_worksheet_q), NUIDocViewXls.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_do_you_want_to_delete_the_sheet) + text + "\" ?", NUIDocViewXls.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_yes), NUIDocViewXls.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_no), new Runnable() { // from class: com.artifex.sonui.editor.NUIDocViewXls.8.1
                            @Override // java.lang.Runnable
                            public void run() {
                                NUIDocViewXls.this.H = true;
                                NUIDocViewXls.this.getDoc().clearSelection();
                                NUIDocViewXls.this.getDoc().deletePage(sheetNumber);
                            }
                        }, null);
                    }
                });
            }
            linearLayout.addView(sheetTab2);
            sheetTab2.setSelected(getCurrentSheet() == i2);
        }
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        this.btnFx = (SOTextView) createToolbarButton(com.xlsxreader.excelreader.R.id.fx_button);
    }

    @Override // com.artifex.sonui.editor.NUIDocView, com.artifex.sonui.editor.DocViewHost
    public void clickSheetButton(int i, boolean z) {
        SheetTab2 sheetTab2;
        LinearLayout linearLayout = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.excel_sheets_bar);
        if (linearLayout != null && (sheetTab2 = (SheetTab2) linearLayout.getChildAt(i)) != null) {
            sheetTab2.performClick();
        }
        if (z) {
            return;
        }
        setSearchStart();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public DocView createMainView(Activity activity) {
        DocExcelView docExcelView = new DocExcelView(activity);
        if (Utilities.isPhoneDevice(activity)) {
            docExcelView.setScale(1.5f);
        }
        return docExcelView;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void doCut() {
        super.doCut();
        ((DocExcelView) getDocView()).setEditText("");
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void doPaste() {
        super.doPaste();
        ((DocExcelView) getDocView()).setEditText(getDoc().getSelectionAsText());
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void doRedo() {
        super.doRedo();
        e();
        this.K = true;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void doUndo() {
        super.doUndo();
        e();
        this.K = true;
    }

    @Override // com.artifex.sonui.editor.NUIDocView, com.artifex.sonui.editor.DocViewHost
    public int getBorderColor() {
        return getContext().getColor(com.xlsxreader.excelreader.R.color.sodk_editor_header_xls_color);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public int getLayoutId() {
        return com.xlsxreader.excelreader.R.layout.document_view_excel;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public String getPageNumberText() {
        return String.format(getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_sheet_d_of_d), Integer.valueOf(getCurrentSheet() + 1), Integer.valueOf(getPageCount()));
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void handleStartPage() {
        if (getStartPage() <= 0 || getPageCount() < getStartPage()) {
            return;
        }
        setCurrentSheet(getStartPage() - 1);
        setStartPage(0);
    }

    public void onAlignOptionsButton(View view) {
        new AlignmentDialog(getContext(), getDoc(), view).show();
    }

    @Override // com.artifex.sonui.editor.NUIDocView, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view == this.btnFx) {
            onClickFunctionButton(view);
        }
    }

    public void onClickFunctionButton(View view) {
        Utilities.hideKeyboard(getContext());
        View viewInflate = View.inflate(getContext(), com.xlsxreader.excelreader.R.layout.sodk_editor_formula_categories, null);
        GridView gridView = (GridView) viewInflate.findViewById(com.xlsxreader.excelreader.R.id.grid);
        final ChooseFormulaCategoryAdapter chooseFormulaCategoryAdapter = new ChooseFormulaCategoryAdapter(activity());
        gridView.setAdapter((ListAdapter) chooseFormulaCategoryAdapter);
        final NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        nUIPopupWindow.setFocusable(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.9
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                nUIPopupWindow.dismiss();
                NUIDocViewXls.this.a(chooseFormulaCategoryAdapter.getItem(i));
            }
        });
        nUIPopupWindow.showAsDropDown(view, 30, 30);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onDocCompleted() {
        int pageCount;
        int currentSheet;
        super.onDocCompleted();
        setPageCount(getPageCount());
        setUpSheetBar();
        if (!this.G) {
            if (this.H) {
                if (getCurrentSheet() == 0) {
                    setCurrentSheet(0);
                } else {
                    pageCount = getCurrentSheet();
                }
                this.G = false;
                this.H = false;
            }
            pageCount = getPageCount();
            if (pageCount <= 0 || getCurrentSheet() < pageCount) {
                currentSheet = getCurrentSheet();
            }
            setCurrentSheet(currentSheet);
            this.G = false;
            this.H = false;
        }
        pageCount = getDoc().r();
        currentSheet = pageCount - 1;
        setCurrentSheet(currentSheet);
        this.G = false;
        this.H = false;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onFullScreenHide() {
        findViewById(com.xlsxreader.excelreader.R.id.fx_bar).setVisibility(8);
        super.onFullScreenHide();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onOrientationChange() {
        super.onOrientationChange();
        d();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onPageLoaded(int i) {
        setUpSheetBar();
        super.onPageLoaded(i);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onSelectionChanged() {
        super.onSelectionChanged();
        setPageCount(getPageCount());
        if (this.K) {
            setUpSheetBar();
            this.K = false;
        }
    }

    @Override // com.artifex.sonui.editor.NUIDocView, com.artifex.sonui.editor.DocViewHost
    public void onShowKeyboard(final boolean z) {
        if (!isActivityActive() || getPageCount() <= 0) {
            return;
        }
        this.keyboardShown = z;
        onShowKeyboardPreventPush();
        DocView docView = getDocView();
        if (docView != null) {
            docView.onShowKeyboard(z);
        }
        if (isLandscapePhone()) {
            showUI(!z);
            if (z) {
                requestLayout();
                final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.10
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                        ((DocExcelView) NUIDocViewXls.this.getDocView()).scrollSelectedCellAboveKeyboard();
                    }
                });
            }
        } else if (z && getDocView() != null) {
            ((DocExcelView) getDocView()).scrollSelectedCellAboveKeyboard();
        }
        d();
        final ViewTreeObserver viewTreeObserver2 = getViewTreeObserver();
        viewTreeObserver2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.11
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                viewTreeObserver2.removeOnGlobalLayoutListener(this);
                DocView docView2 = NUIDocViewXls.this.getDocView();
                if (docView2 != null) {
                    docView2.onShowKeyboard(z);
                }
                NUIDocViewXls.this.layoutNow();
            }
        });
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void preSave() {
        if (((DocExcelView) getDocView()) != null) {
            ((DocExcelView) getDocView()).copyEditTextToCell();
        }
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void prepareToGoBack() {
        SODocSession session;
        SOFileState fileState;
        DocView docView = getDocView();
        if (docView == null || docView.getDoc() == null || !((DocExcelView) docView).copyEditTextToCell() || (session = getSession()) == null || (fileState = session.getFileState()) == null) {
            return;
        }
        fileState.setHasChanges(true);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void setSearchStart() {
        int currentSheet = getCurrentSheet();
        if (currentSheet >= 0) {
            getDoc().a(currentSheet, 0.0f, 0.0f);
        }
    }

    @Override // com.artifex.sonui.editor.NUIDocView, com.artifex.sonui.editor.DocViewHost
    public boolean showKeyboard() {
        return false;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void showUI(boolean z) {
        super.showUI(z);
        if (this.mConfigOptions.A() && z && this.mConfigOptions.c()) {
            findViewById(com.xlsxreader.excelreader.R.id.fx_bar).setVisibility(0);
        }
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void updateEditUIAppearance() {
        boolean z;
        SOSelectionLimits selectionLimits = getDocView().getSelectionLimits();
        boolean z2 = false;
        if (selectionLimits != null) {
            boolean isActive = selectionLimits.getIsActive();
            z = isActive && !selectionLimits.getIsCaret();
            if (isActive) {
                selectionLimits.getIsCaret();
            }
        } else {
            z = false;
        }
        SODoc doc = this.mSession.getDoc();
        this.btnTextBold.setEnabled(z);
        this.btnTextBold.setSelected(z && doc.getSelectionIsBold());
        this.btnTextItalic.setEnabled(z);
        this.btnTextItalic.setSelected(z && doc.getSelectionIsItalic());
        this.btnTextUnder.setEnabled(z);
        this.btnTextUnder.setSelected(z && doc.getSelectionIsUnderlined());
        this.btnTextStrikeTrough.setEnabled(z);
        EditBtn editBtn = this.btnTextStrikeTrough;
        if (z && doc.getSelectionIsLinethrough()) {
            z2 = true;
        }
        editBtn.setSelected(z2);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void updateUIAppearance() {
        super.updateUIAppearance();
        SOSelectionLimits selectionLimits = getDocView().getSelectionLimits();
        if (selectionLimits != null) {
            boolean isActive = selectionLimits.getIsActive();
            if (!isActive || selectionLimits.getIsCaret()) {
            }
            if (!isActive || selectionLimits.getIsCaret()) {
            }
        }
        getDocView().onSelectionChanged();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public boolean usePagesView() {
        return false;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void createEditButtons2() {
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void createInputView() {
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void createInsertButtons() {
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void focusInputView() {
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void resetInputView() {
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void updateReviewUIAppearance() {
    }

    public NUIDocViewXls(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.E = -1;
        this.G = false;
        this.H = false;
        this.I = null;
        this.J = null;
        this.K = false;
        a(context);
    }

    public NUIDocViewXls(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.E = -1;
        this.G = false;
        this.H = false;
        this.I = null;
        this.J = null;
        this.K = false;
        a(context);
    }

    private void a(Context context) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        setCurrentSheet(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str) {
        ListPopupWindow listPopupWindow = this.I;
        if (listPopupWindow != null) {
            listPopupWindow.dismiss();
        }
        if (getKeyboardHeight() > 0) {
            Utilities.hideKeyboard(getContext());
            final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocViewXls.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                    NUIDocViewXls.this.b(str);
                }
            });
            return;
        }
        b(str);
    }
}
