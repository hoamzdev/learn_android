package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.RelativeLayout;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOSelectionLimits;

/* JADX INFO: loaded from: classes.dex */
public class NoteEditor {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private View f4687a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private SOEditText f4688b;
    private SOTextView c;
    private SOTextView d;
    private View e;
    private DocView f;
    private int g = 0;
    private SOSelectionLimits h;
    private DocPageView i;
    private NoteDataHandler j;

    public interface NoteDataHandler {
        String getAuthor();

        String getComment();

        String getDate();

        void setComment(String str);
    }

    public NoteEditor(final Activity activity, DocView docView, DocViewHost docViewHost, NoteDataHandler noteDataHandler) {
        this.f = docView;
        this.j = noteDataHandler;
        this.f4687a = activity.findViewById(R.id.doc_note_editor);
        this.e = activity.findViewById(R.id.doc_cover);
        this.f4688b = (SOEditText) activity.findViewById(R.id.doc_note_editor_text);
        this.c = (SOTextView) activity.findViewById(R.id.doc_note_editor_date);
        this.d = (SOTextView) activity.findViewById(R.id.doc_note_editor_author);
        this.f4688b.setEnabled(false);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.NoteEditor.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Utilities.hideKeyboard(activity);
                NoteEditor.this.f4688b.clearFocus();
                NoteEditor.this.e.setVisibility(8);
            }
        });
        this.f4688b.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.artifex.sonui.editor.NoteEditor.2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                View view2 = NoteEditor.this.e;
                if (!z) {
                    view2.setVisibility(8);
                    NoteEditor.this.saveData();
                    NoteEditor.this.f.smoothScrollBy(0, -NoteEditor.this.g);
                    NoteEditor.this.g = 0;
                    return;
                }
                view2.setVisibility(0);
                Rect rect = new Rect();
                NoteEditor.this.f.getGlobalVisibleRect(rect);
                rect.offset(0, -rect.top);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) NoteEditor.this.f4687a.getLayoutParams();
                NoteEditor.this.g = rect.top - layoutParams.topMargin;
                NoteEditor.this.f.smoothScrollBy(0, NoteEditor.this.g);
            }
        });
    }

    public void focus() {
        this.f4688b.requestFocus();
    }

    public Rect getRect() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.f4687a.getLayoutParams();
        Rect rect = new Rect();
        int i = layoutParams.leftMargin;
        rect.left = i;
        rect.top = layoutParams.topMargin;
        rect.right = this.f4687a.getMeasuredWidth() + i;
        rect.bottom = this.f4687a.getMeasuredHeight() + rect.top;
        return rect;
    }

    public void hide() {
        this.f4687a.setVisibility(8);
        this.e.setVisibility(8);
    }

    public boolean isVisible() {
        return this.f4687a.getVisibility() == 0;
    }

    public void move() {
        View view = this.f4687a;
        if (view == null || this.h == null || view.getVisibility() != 0 || this.i == null) {
            return;
        }
        RectF box = this.h.getBox();
        Point pointPageToView = this.i.pageToView((int) box.left, (int) box.bottom);
        pointPageToView.offset(this.i.getLeft(), this.i.getTop());
        pointPageToView.offset(-this.f.getScrollX(), -this.f.getScrollY());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.f4687a.getLayoutParams();
        layoutParams.leftMargin = pointPageToView.x;
        layoutParams.topMargin = pointPageToView.y;
        this.f4687a.setLayoutParams(layoutParams);
    }

    public void preMoving() {
        if (this.f4688b.isEnabled()) {
            saveData();
            this.f4688b.setEnabled(false);
        }
    }

    public void saveData() {
        this.j.setComment(this.f4688b.getText().toString());
    }

    public void setCommentEditable(boolean z) {
        this.f4688b.setEnabled(z);
    }

    public void show(SOSelectionLimits sOSelectionLimits, DocPageView docPageView) {
        this.h = sOSelectionLimits;
        this.i = docPageView;
        SODoc doc = this.f.getDoc();
        String author = this.j.getAuthor();
        String date = this.j.getDate();
        String comment = this.j.getComment();
        if (author == null || author.isEmpty()) {
            this.d.setVisibility(8);
        } else {
            this.d.setVisibility(0);
            this.d.setText(author);
        }
        if (date == null || date.isEmpty()) {
            this.c.setVisibility(8);
        } else {
            this.c.setVisibility(0);
            this.c.setText(Utilities.formatDateForLocale(this.f.getContext(), date, doc.z()));
        }
        this.f4688b.setText(comment);
        this.f4687a.setVisibility(0);
    }
}
