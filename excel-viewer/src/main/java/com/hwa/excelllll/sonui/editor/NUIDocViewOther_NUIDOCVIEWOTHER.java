package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.xlsxreader.excelreader.customviews.BottomBtn;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class NUIDocViewOther extends NUIDocView {
    private BottomBtn btnPrintTab;

    public NUIDocViewOther(Context context) {
        super(context);
        a();
    }

    private String getFileExtension() {
        String internalPath;
        if (this.mStartUri != null) {
            return com.artifex.solib.a.a(getContext(), this.mStartUri);
        }
        SODocSession sODocSession = this.mSession;
        if (sODocSession != null) {
            internalPath = sODocSession.getUserPath();
        } else {
            SOFileState sOFileState = this.mState;
            internalPath = sOFileState != null ? sOFileState.getInternalPath() : "";
        }
        return com.artifex.solib.a.g(internalPath);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void afterFirstLayoutComplete() {
        super.afterFirstLayoutComplete();
        this.btnPrintTab = (BottomBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btnPrintTab);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public PageAdapter createAdapter() {
        return new PageAdapter(activity(), this, 1);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public DocView createMainView(Activity activity) {
        return new DocView(activity);
    }

    @Override // com.artifex.sonui.editor.NUIDocView, com.artifex.sonui.editor.DocViewHost
    public int getBorderColor() {
        return getContext().getColor(com.xlsxreader.excelreader.R.color.sodk_editor_selected_page_border_color);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public int getLayoutId() {
        return com.xlsxreader.excelreader.R.layout.document_view_other;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public boolean hasRedo() {
        return false;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public boolean hasSearch() {
        String fileExtension = getFileExtension();
        return fileExtension != null && fileExtension.compareToIgnoreCase("txt") == 0;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public boolean hasUndo() {
        return false;
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public boolean inputViewHasFocus() {
        return false;
    }

    @Override // com.artifex.sonui.editor.NUIDocView, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view == this.btnPrintTab) {
            onPrintButton();
        }
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onFullScreenHide() {
        layoutNow();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onPause() {
        onPauseCommon();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onRedoButton(View view) {
        super.onRedoButton(view);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onResume() {
        onResumeCommon();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void onUndoButton(View view) {
        super.onUndoButton(view);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void scaleHeader() {
        scaleToolbar();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void showUI(boolean z) {
        layoutNow();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void updateUIAppearance() {
        updateUndoUIAppearance();
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void updateUndoUIAppearance() {
        this.btnUndo.setVisibility(8);
        this.btnRedo.setVisibility(8);
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public boolean usePagesView() {
        return false;
    }

    public NUIDocViewOther(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public NUIDocViewOther(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
    }

    @Override // com.artifex.sonui.editor.NUIDocView
    public void createEditButtons() {
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
    public void createRecyclerColor() {
    }
}
