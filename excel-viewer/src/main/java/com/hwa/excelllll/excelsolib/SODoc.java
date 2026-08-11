package com.hwa.excelllll.excelsolib;

import android.util.Log;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;

import com.hwa.excelllll.SOLib;

import java.util.ArrayList;

public class SODoc {
    private boolean isSearching;
    private long internalHandle;
    private String searchTerm;
    private boolean searchMatchCase;
    private boolean searchMatchWholeWord;
    private SearchListener searchListener;
    public final int DocType_DOC = 4;
    public final int DocType_DOCX = 5;
    public final int DocType_Error = -1;
    public final int DocType_Other = 7;
    public final int DocType_PDF = 6;
    public final int DocType_PPT = 2;
    public final int DocType_PPTX = 3;
    public final int DocType_XLS = 0;
    public final int DocType_XLSX = 1;
    private final int SEARCH_RESULT_FOUND = 0;
    private final int SEARCH_RESULT_NOT_FOUND = 1;
    protected int docType = 0;
    private final int SEARCH_RESULT_COMPLETED = 2;
    private final int SEARCH_RESULT_CANCELLED = 3;
    private final int SEARCH_RESULT_ERROR = 4;
    private final int SEARCH_RESULT_PROGRESS = 5;
    private final int SEARCH_RESULT_ENDED = 6;
    private int lastClipboardGeneration = 0;
    private int flowMode = 1;
    private ArrayList<SOPage> pagesList = new ArrayList<>();
    private int totalPagesCount = 0;
    private int activePageIndex = 0;

    public SODoc(long handle) {
        this.internalHandle = handle;
    }

    private native void destroy();

    private native String getSelectionNaturalDimensionsInternal();

    private native void insertImageAtSelection(String imagePath);

    private native void insertImageCenterPage(int pageIndex, String imagePath);

    private native void nativeCloseSearch();

    private native void nativeInsertAutoshapeCenterPage(int pageIndex, String shapeType, String shapeData, boolean isCentered, float width, float height);

    private native int nativeSearch(String query, boolean matchCase, boolean matchWholeWord);

    private native void nativeSetSearchStart(int pageIndex, float x, float y);

    private native int saveToInternal(String targetPath, SODocSaveListener saveListener);

    private native int saveToPDFInternal(String targetPath, boolean includeNotes, SODocSaveListener saveListener);

    private void searchProgress(final int resultType, final int pageIndex, final float left, final float top, final float right, final float bottom) {
        SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SODoc.1
            class SaveCompletionRunnable implements Runnable {
                final SODocSaveListener saveListener;
                final /* synthetic */ int status;
                final /* synthetic */ int error;

                public SaveCompletionRunnable(SODocSaveListener saveListener, int status, int error) {
                    this.saveListener = saveListener;
                    this.status = status;
                    this.error = error;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.saveListener.onComplete(this.status, this.error);
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                if (resultType != 5) {
                    SODoc.this.isSearching = false;
                }
                int typeCode = resultType;
                if (typeCode == 0) {
                    if (SODoc.this.searchListener != null) {
                        SODoc.this.searchListener.onTextFound(pageIndex, new RectF(left, top, right, bottom));
                        return;
                    }
                    return;
                }
                if (typeCode == 1) {
                    if (SODoc.this.searchListener != null) {
                        SODoc.this.searchListener.onTextNotFound();
                        return;
                    }
                    return;
                }
                if (typeCode == 2) {
                    if (SODoc.this.searchListener != null) {
                        SODoc.this.searchListener.onSearchComplete();
                        return;
                    }
                    return;
                }
                if (typeCode == 3) {
                    if (SODoc.this.searchListener != null) {
                        SODoc.this.searchListener.onSearchCancelled();
                        return;
                    }
                    return;
                }
                SODoc doc = SODoc.this;
                if (typeCode == 5) {
                    if (doc.searchListener != null) {
                        SODoc.this.searchListener.onSearchProgress(pageIndex);
                    }
                } else if (typeCode != 6) {
                    if (doc.searchListener != null) {
                        SODoc.this.searchListener.onSearchError();
                    }
                } else if (doc.searchListener != null) {
                    SODoc.this.searchListener.onSearchEnded();
                }
            }
        });
    }

    private native void setSelectionListStyle(int listStyle);

    public void setSelectionListStyleNone() {
        setSelectionListStyle(0);
    }

    public void setSelectionListStyleDisc() {
        setSelectionListStyle(1);
    }

    public void setSelectionListStyleDecimal() {
        setSelectionListStyle(2);
    }

    public void alignSelectionLeft() {
        setSelectionAlignment(0);
    }

    public void alignSelectionCenter() {
        setSelectionAlignment(1);
    }

    public void alignSelectionRight() {
        setSelectionAlignment(2);
    }

    public void justifySelection() {
        setSelectionAlignment(3);
    }

    public void cutSelection() {
        if (getSelectionCanBeCopied() && getSelectionCanBeDeleted()) {
            selectionCutToClip();
            this.lastClipboardGeneration = ArtifexEngine.getClipboardGeneration() + 1;
            ArtifexEngine.setClipboardText(getClipboardAsText());
        }
    }

    public void copySelection() {
        if (getSelectionCanBeCopied()) {
            selectionCopyToClip();
            this.lastClipboardGeneration = ArtifexEngine.getClipboardGeneration() + 1;
            ArtifexEngine.setClipboardText(getClipboardAsText());
        }
    }

    public boolean canPaste() {
        return clipboardHasData() || ArtifexEngine.hasClipboardText();
    }

    public int getFlowMode() {
        return this.flowMode;
    }

    public PointF getSelectionNaturalDimensions() {
        PointF pointF = new PointF(0.0f, 0.0f);
        String selectionNaturalDimensionsInternal = getSelectionNaturalDimensionsInternal();
        if (selectionNaturalDimensionsInternal != null) {
            String[] dimensions = selectionNaturalDimensionsInternal.split(",");
            if (dimensions.length == 2) {
                try {
                    pointF.set(Float.parseFloat(dimensions[0]), Float.parseFloat(dimensions[1]));
                    return pointF;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return pointF;
    }

    public void releasePages() {
        ArrayList<SOPage> pages = this.pagesList;
        if (pages == null || pages.isEmpty()) {
            return;
        }
        int pageIndex = 0;
        while (true) {
            int size = this.pagesList.size();
            ArrayList<SOPage> activePages = this.pagesList;
            if (pageIndex >= size) {
                activePages.clear();
                return;
            }
            SOPage page = activePages.get(pageIndex);
            if (page != null) {
                page.release();
            }
            pageIndex++;
        }
    }

    public void deleteSelectionOrCharacter() {
        if (getSelectionCanBeDeleted()) {
            selectionDelete();
        } else {
            deleteChar();
        }
    }

    public int getTrackedPageCount() {
        return this.totalPagesCount;
    }

    public int getActivePageIndex() {
        return this.activePageIndex;
    }

    public void pasteSelection(Context context, int pasteMode) {
        if (!getSelectionCanBePasteTarget() || selectionIsAutoshapeOrImage()) {
            return;
        }
        boolean hasClipboardData = clipboardHasData();
        if (!ArtifexEngine.hasClipboardText() || (hasClipboardData && (!hasClipboardData || ArtifexEngine.getClipboardGeneration() <= this.lastClipboardGeneration))) {
            selectionPaste(pasteMode);
        } else {
            setSelectionText(ArtifexEngine.getClipboardText(context), 0, true);
        }
    }

    public native void abortLoad();

    public native void acceptTrackedChange();

    public native void addBlankPage(int pageIndex);

    public native void addColumnsLeft();

    public native void addColumnsRight();

    public native void addHighlightAnnotation();

    public native void addRowsAbove();

    public native void addRowsBelow();

    public void setSearchQuery(String query) {
        if (this.isSearching) {
            Log.e("ExcelCore", "Search already in progess");
        } else {
            this.searchTerm = new String(query);
        }
    }

    public void setSearchMatchCase(boolean matchCase) {
        if (this.isSearching) {
            Log.e("ExcelCore", "Search already in progess");
        } else {
            this.searchMatchCase = matchCase;
        }
    }

    public native void cancelSearch();

    public native void clearSelection();

    public native boolean clipboardHasData();

    public native void createInkAnnotation(int pageIndex, SOPoint[] points, float width, int color);

    public native void createTextAnnotationAt(PointF point, int color);

    public void setSearchMatchWholeWord(boolean matchWholeWord) {
        if (this.isSearching) {
            Log.e("ExcelCore", "Search already in progess");
        } else {
            this.searchMatchWholeWord = matchWholeWord;
        }
    }

    public native void deleteChar();

    public native void deleteColumns();

    public native void deleteHighlightAnnotation();

    public native void deletePage(int pageIndex);

    public native void deleteRows();

    public native boolean docSupportsReview();

    public native void duplicatePage(int pageIndex);

    public void setActivePageIndex(int pageIndex) {
        this.activePageIndex = pageIndex;
    }

    public native int enumerateToc(SOEnumerateTocListener tocListener);

    public void finalize() throws Throwable {
        try {
            destroy();
        } finally {
            super.finalize();
        }
    }

    public native String getAuthor();

    public native String[] getBgColorList();

    public native String getClipboardAsText();

    public native int getCurrentEdit();

    public native String getFontList();

    public native boolean getHasBeenModified();

    public native int[] getIndentationLevel();

    public native int getNumEdits();

    public native SOPage getPage(int pageIndex, SOPageListener pageListener);

    public native String getSelectedCellFormat();

    public native float getSelectedColumnWidth();

    public native float getSelectedRowHeight();

    public native String getSelectedTrackedChangeAuthor();

    public native String getSelectedTrackedChangeComment();

    public native String getSelectedTrackedChangeDate();

    public native int getSelectedTrackedChangeType();

    public native int getSelectionAlignment();

    public native int getSelectionAlignmentV();

    public native String getSelectionAnnotationAuthor();

    public native String getSelectionAnnotationComment();

    public native String getSelectionAnnotationDate();

    public native SOBitmap getSelectionAsBitmap();

    public native String getSelectionAsText();

    public native boolean getSelectionCanBeAbsolutelyPositioned();

    public native boolean getSelectionCanBeCopied();

    public native boolean getSelectionCanBeDeleted();

    public native boolean getSelectionCanBePasteTarget();

    public native boolean getSelectionCanBeResized();

    public native boolean getSelectionCanBeRotated();

    public native boolean getSelectionCanHaveTextAltered();

    public native String getSelectionFontName();

    public native double getSelectionFontSize();

    public native boolean getSelectionHasAssociatedPopup();

    public native boolean getSelectionIsAlignCenter();

    public native boolean getSelectionIsAlignJustify();

    public native boolean getSelectionIsAlignLeft();

    public native boolean getSelectionIsAlignRight();

    public native boolean getSelectionIsAlterableTextSelection();

    public native boolean getSelectionIsBold();

    public native boolean getSelectionIsItalic();

    public native boolean getSelectionIsLinethrough();

    public native boolean getSelectionIsUnderlined();

    public native int getSelectionLineType();

    public native float getSelectionLineWidth();

    public native boolean getSelectionListStyleIsDecimal();

    public native boolean getSelectionListStyleIsDisc();

    public native float getSelectionRotation();

    public native boolean getShowingTrackedChanges();

    public native boolean getTableCellsMerged();

    public native boolean getTrackingChanges();

    public boolean supportsEditing() {
        return true;
    }

    public boolean supportsSelection() {
        return true;
    }

    public void release() {
        destroy();
    }

    public native void movePage(int fromIndex, int toIndex);

    public native boolean nextTrackedChange();

    public void closeSearch() {
        nativeCloseSearch();
        this.isSearching = false;
    }

    public boolean isSearching() {
        return this.isSearching;
    }

    public native boolean previousTrackedChange();

    public native void processKeyCommand(int commandId);

    public native boolean providePassword(String password);

    public int startSearch() {
        String query = this.searchTerm;
        if (query == null) {
            Log.e("ExcelCore", "No Search Text specified");
            return 0;
        }
        if (this.isSearching) {
            Log.e("ExcelCore", "Search already in progess");
            return 0;
        }
        this.isSearching = true;
        try {
            int searchResult = nativeSearch(query, this.searchMatchCase, this.searchMatchWholeWord);
            if (searchResult != 0) {
                this.isSearching = false;
            }
            return searchResult;
        } catch (Throwable th) {
            this.isSearching = false;
            throw th;
        }
    }

    public int getDocType() {
        return this.docType;
    }

    public native void rejectTrackedChange();

    public native void selectionCopyToClip();

    public native void selectionCutToClip();

    public native void selectionDelete();

    public native boolean selectionIsAutoshapeOrImage();

    public native boolean selectionIsReviewable();

    public native void selectionPaste(int mode);

    public native SOSelectionTableRange selectionTableRange();

    public native boolean setAuthor(String authorName);

    public native void setCurrentEdit(int editIndex);

    public native void setFlowModeInternal(int mode, float scale);

    public native void setIndentationLevel(int level);

    public native void setSelectedCellFormat(String format);

    public native void setSelectedColumnWidth(float width);

    public native void setSelectedRowHeight(float height);

    public native void setSelectionAlignment(int alignment);

    public native void setSelectionAlignmentV(int verticalAlignment);

    public native void setSelectionAnnotationComment(String comment);

    public native void setSelectionArrangeBack();

    public native void setSelectionArrangeBackwards();

    public native void setSelectionArrangeForwards();

    public native void setSelectionArrangeFront();

    public native void setSelectionBackgroundColor(String color);

    public native void setSelectionBackgroundTransparent();

    public native void setSelectionFillColor(String color);

    public native void setSelectionFontColor(String color);

    public native void setSelectionFontName(String fontName);

    public native void setSelectionFontSize(double fontSize);

    public native void setSelectionIsBold(boolean isBold);

    public native void setSelectionIsItalic(boolean isItalic);

    public native void setSelectionIsLinethrough(boolean isLinethrough);

    public native void setSelectionIsUnderlined(boolean isUnderlined);

    public native void setSelectionLineColor(String color);

    public native void setSelectionLineType(int lineType);

    public native void setSelectionLineWidth(float lineWidth);

    public native void setSelectionRotation(float rotationAngle);

    public native void setSelectionText(String text, int mode, boolean isFormatted);

    public native void setShowingTrackedChanges(boolean show);

    public native void setTableCellsMerged(boolean merged);

    public native void setTrackingChanges(boolean track);

    public boolean supportsReflow() {
        return false;
    }

    public boolean usesLegacyDateFormat() {
        return false;
    }

    public String getDateFormatPattern() {
        return "dd/MM/yyyy HH:mm:ss";
    }

    public void setDocType(int type) {
        this.docType = type;
    }

    public void insertImageAtSelectionFromPath(String imagePath) {
        insertImageAtSelection(imagePath);
    }

    public void setTrackedPageCount(int pageCount) {
        this.totalPagesCount = pageCount;
    }

    public void removePage(SOPage page) {
        this.pagesList.remove(page);
    }

    public void saveAsPdf(String path, boolean includeNotes, final SODocSaveListener saveListener) {
        if (saveToPDFInternal(path, includeNotes, new SODocSaveListener() { // from class: com.hwa.excel.excelsolib.SODoc.3
            @Override // com.hwa.excelllll.excelsolib.SODocSaveListener
            public void onComplete(final int status, final int errorDetail) {
                SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SODoc.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        saveListener.onComplete(status, errorDetail);
                    }
                });
            }
        }) == 0) {
            return;
        }
        Log.e("ExcelCore", "Search operation failed");
    }

    public void setOptionalStringParameter(String value) {
    }

    public void setOptionalBooleanParameter(boolean enabled) {
    }

    public void setFlowMode(int mode, float scale) {
        this.flowMode = mode;
        setFlowModeInternal(mode, scale);
    }

    public void setSearchStart(int pageIndex, float x, float y) {
        if (!this.isSearching) {
            nativeSetSearchStart(pageIndex, x, y);
        } else {
            Log.e("ExcelCore", "Search already in progess");
        }
    }

    public void insertImageOnPage(int pageIndex, String imagePath) {
        insertImageCenterPage(pageIndex, imagePath);
    }

    public void insertAutoShapeOnPage(int pageIndex, String shapeType, String shapeData) {
        nativeInsertAutoshapeCenterPage(pageIndex, shapeType, shapeData, true, 0.0f, 0.0f);
    }

    public void addPage(SOPage page) {
        this.pagesList.add(page);
    }

    public void setSearchListener(SearchListener listener) {
        if (!this.isSearching) {
            this.searchListener = listener;
        } else {
            Log.e("ExcelCore", "Search already in progess");
        }
    }

    public void save(String path, final SODocSaveListener saveListener) {
        if (saveToInternal(path, new SODocSaveListener() { // from class: com.hwa.excel.excelsolib.SODoc.2
            @Override // com.hwa.excelllll.excelsolib.SODocSaveListener
            public void onComplete(final int status, final int errorDetail) {
                SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SODoc.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        saveListener.onComplete(status, errorDetail);
                    }
                });
            }
        }) == 0) {
            return;
        }
        Log.e("ExcelCore", "Search operation failed");
    }
}
