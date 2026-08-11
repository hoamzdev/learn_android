package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DocPdfView extends DocView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    DocPdfPageView f4563a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    ArrayList<DocPdfPageView> f4564b;
    private NoteEditor c;
    private boolean d;
    private boolean e;
    private float f;
    private float g;
    private int h;
    private float i;

    public DocPdfView(Context context) {
        super(context);
        this.c = null;
        this.d = false;
        this.e = false;
        this.f4563a = null;
        this.f4564b = new ArrayList<>();
        this.h = -65536;
        this.i = 4.5f;
    }

    private void a(float f, float f2) {
        Point pointEventToScreen = eventToScreen(f, f2);
        DocPdfPageView docPdfPageView = (DocPdfPageView) findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
        this.f4563a = docPdfPageView;
        if (docPdfPageView != null) {
            docPdfPageView.startDrawInk(pointEventToScreen.x, pointEventToScreen.y, this.h, this.i);
            if (!this.f4564b.contains(this.f4563a)) {
                this.f4564b.add(this.f4563a);
            }
            this.mHostActivity.selectionupdated();
        }
        this.f = f;
        this.g = f2;
    }

    private void b(float f, float f2) {
        float fAbs = Math.abs(f - this.f);
        float fAbs2 = Math.abs(f2 - this.g);
        if (fAbs >= 2.0f || fAbs2 >= 2.0f) {
            Point pointEventToScreen = eventToScreen(f, f2);
            DocPdfPageView docPdfPageView = this.f4563a;
            if (docPdfPageView != null) {
                docPdfPageView.continueDrawInk(pointEventToScreen.x, pointEventToScreen.y);
            }
            this.f = f;
            this.g = f2;
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean canEditText() {
        return false;
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean canSelectionSpanPages() {
        return false;
    }

    public void clearInk() {
        ArrayList<DocPdfPageView> arrayList = this.f4564b;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            DocPdfPageView docPdfPageView = arrayList.get(i);
            i++;
            docPdfPageView.clearInk();
        }
        this.f4564b.clear();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void doDoubleTap(float f, float f2) {
        if (this.mConfigOptions.c() && !((NUIDocView) this.mHostActivity).isFullScreen()) {
            doDoubleTap2(f, f2);
        }
    }

    public boolean getDrawMode() {
        return this.e;
    }

    public int getInkLineColor() {
        return this.h;
    }

    public float getInkLineThickness() {
        return this.i;
    }

    public boolean getNoteMode() {
        return this.d;
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean handleFullscreenTap(float f, float f2) {
        Point pointEventToScreen = eventToScreen(f, f2);
        DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
        if (docPageViewFindPageViewContainingPoint == null) {
            return false;
        }
        Point pointScreenToPage = docPageViewFindPageViewContainingPoint.screenToPage(pointEventToScreen);
        return docPageViewFindPageViewContainingPoint.handleFullscreenTap(pointScreenToPage.x, pointScreenToPage.y);
    }

    public boolean hasNotSavedInk() {
        ArrayList<DocPdfPageView> arrayList = this.f4564b;
        return arrayList != null && arrayList.size() > 0;
    }

    public void onDrawMode() {
        boolean z = this.e;
        this.e = !z;
        this.d = false;
        if (z) {
            saveInk();
        }
        getDoc().clearSelection();
        onSelectionChanged();
    }

    @Override // com.artifex.sonui.editor.DocView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (finished()) {
            return;
        }
        this.c.move();
    }

    public void onNoteMode() {
        this.d = !this.d;
        this.e = false;
        clearAreaSelection();
        onSelectionChanged();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onReloadFile() {
        for (int i = 0; i < getPageCount(); i++) {
            ((DocMuPdfPageView) getOrCreateChild(i)).onReloadFile();
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onSelectionChanged() {
        super.onSelectionChanged();
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean onSingleTap(float f, float f2, DocPageView docPageView) {
        DocPdfPageView docPdfPageView = (DocPdfPageView) docPageView;
        if (!this.d || docPdfPageView == null) {
            return false;
        }
        docPdfPageView.createNote(f, f2);
        this.d = false;
        return true;
    }

    @Override // com.artifex.sonui.editor.DocView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (finished()) {
            return true;
        }
        if (!getDrawMode()) {
            return super.onTouchEvent(motionEvent);
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            a(x, y);
        } else if (action == 1) {
            b();
        } else if (action == 2) {
            b(x, y);
        }
        return true;
    }

    public void resetModes() {
        this.e = false;
        saveInk();
        this.d = false;
        if (this.c.isVisible()) {
            this.c.saveData();
            Utilities.hideKeyboard(getContext());
            this.c.hide();
        }
        clearAreaSelection();
        onSelectionChanged();
    }

    public void saveInk() {
        ArrayList<DocPdfPageView> arrayList = this.f4564b;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            DocPdfPageView docPdfPageView = arrayList.get(i);
            i++;
            docPdfPageView.saveInk();
        }
        this.f4564b.clear();
        this.f4563a = null;
    }

    public void saveNoteData() {
        NoteEditor noteEditor = this.c;
        if (noteEditor != null) {
            noteEditor.saveData();
        }
    }

    public void setInkLineColor(int i) {
        this.h = i;
        ArrayList<DocPdfPageView> arrayList = this.f4564b;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            DocPdfPageView docPdfPageView = arrayList.get(i2);
            i2++;
            docPdfPageView.setInkLineColor(i);
        }
    }

    public void setInkLineThickness(float f) {
        this.i = f;
        ArrayList<DocPdfPageView> arrayList = this.f4564b;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            DocPdfPageView docPdfPageView = arrayList.get(i);
            i++;
            docPdfPageView.setInkLineThickness(f);
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void setup(RelativeLayout relativeLayout) {
        super.setup(relativeLayout);
        this.c = new NoteEditor((Activity) getContext(), this, this.mHostActivity, new NoteEditor.NoteDataHandler() { // from class: com.artifex.sonui.editor.DocPdfView.1
            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public String getAuthor() {
                return DocPdfView.this.getDoc().getSelectionAnnotationAuthor();
            }

            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public String getComment() {
                return DocPdfView.this.getDoc().getSelectionAnnotationComment();
            }

            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public String getDate() {
                return DocPdfView.this.getDoc().getSelectionAnnotationDate();
            }

            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public void setComment(String str) {
                DocPdfView.this.getDoc().setSelectionAnnotationComment(str);
            }
        });
    }

    @Override // com.artifex.sonui.editor.DocView
    public void updateReview() {
        if (getDoc() == null) {
            Log.e("DocPdfView", "getDoc() returned NULL in updateReview");
            return;
        }
        if (!getDoc().getSelectionHasAssociatedPopup()) {
            if (this.c.isVisible()) {
                Utilities.hideKeyboard(getContext());
                this.c.hide();
                return;
            }
            return;
        }
        this.c.show(getSelectionLimits(), this.mSelectionStartPage);
        this.c.move();
        this.c.setCommentEditable(true);
        requestLayout();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void showKeyboardAfterDoubleTap(Point point) {
    }

    public DocPdfView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = null;
        this.d = false;
        this.e = false;
        this.f4563a = null;
        this.f4564b = new ArrayList<>();
        this.h = -65536;
        this.i = 4.5f;
    }

    public DocPdfView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = null;
        this.d = false;
        this.e = false;
        this.f4563a = null;
        this.f4564b = new ArrayList<>();
        this.h = -65536;
        this.i = 4.5f;
    }

    private void b() {
        DocPdfPageView docPdfPageView = this.f4563a;
        if (docPdfPageView != null) {
            docPdfPageView.endDrawInk();
        }
    }
}
