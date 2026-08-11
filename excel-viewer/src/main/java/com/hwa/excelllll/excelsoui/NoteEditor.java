package com.hwa.excelllll.excelsoui;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.RelativeLayout;
import com.hwa.excelllll.R;
import com.hwa.excelllll.excelsolib.SODoc;
import com.hwa.excelllll.excelsolib.SOSelectionLimits;

public class NoteEditor {
    private View editorView;
    private SOEditText editText;
    private SOTextView dateTextView;
    private SOTextView authorTextView;
    private View coverView;
    private DocView docView;
    private int scrollOffset = 0;
    private SOSelectionLimits selectionLimits;
    private DocPageView pageView;
    private NoteDataHandler dataHandler;

    public interface NoteDataHandler {
        String getAuthor();

        String getComment();

        String getDate();

        void setComment(String comment);
    }

    public NoteEditor(final Activity activity, DocView docView, DocViewHost docViewHost, NoteDataHandler noteDataHandler) {
        this.docView = docView;
        this.dataHandler = noteDataHandler;
        this.editorView = activity.findViewById(R.id.doc_note_editor);
        this.coverView = activity.findViewById(R.id.doc_cover);
        this.editText = (SOEditText) activity.findViewById(R.id.doc_note_editor_text);
        this.dateTextView = (SOTextView) activity.findViewById(R.id.doc_note_editor_date);
        this.authorTextView = (SOTextView) activity.findViewById(R.id.doc_note_editor_author);
        this.editText.setEnabled(false);
        this.coverView.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.NoteEditor.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Utilities.hideKeyboard(activity);
                NoteEditor.this.editText.clearFocus();
                NoteEditor.this.coverView.setVisibility(View.GONE);
            }
        });
        this.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.artifex.sonui.editor.NoteEditor.2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean hasFocus) {
                View cover = NoteEditor.this.coverView;
                if (!hasFocus) {
                    cover.setVisibility(View.GONE);
                    NoteEditor.this.saveData();
                    NoteEditor.this.docView.smoothScrollBy(0, -NoteEditor.this.scrollOffset);
                    NoteEditor.this.scrollOffset = 0;
                    return;
                }
                cover.setVisibility(View.VISIBLE);
                Rect rect = new Rect();
                NoteEditor.this.docView.getGlobalVisibleRect(rect);
                rect.offset(0, -rect.top);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) NoteEditor.this.editorView.getLayoutParams();
                NoteEditor.this.scrollOffset = rect.top - layoutParams.topMargin;
                NoteEditor.this.docView.smoothScrollBy(0, NoteEditor.this.scrollOffset);
            }
        });
    }

    public void focus() {
        this.editText.requestFocus();
    }

    public Rect getRect() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.editorView.getLayoutParams();
        Rect rect = new Rect();
        int leftMargin = layoutParams.leftMargin;
        rect.left = leftMargin;
        rect.top = layoutParams.topMargin;
        rect.right = this.editorView.getMeasuredWidth() + leftMargin;
        rect.bottom = this.editorView.getMeasuredHeight() + rect.top;
        return rect;
    }

    public void hide() {
        this.editorView.setVisibility(View.GONE);
        this.coverView.setVisibility(View.GONE);
    }

    public boolean isVisible() {
        return this.editorView.getVisibility() == View.VISIBLE;
    }

    public void move() {
        View view = this.editorView;
        if (view == null || this.selectionLimits == null || view.getVisibility() != View.VISIBLE || this.pageView == null) {
            return;
        }
        RectF box = this.selectionLimits.getBox();
        Point pointPageToView = this.pageView.pageToView((int) box.left, (int) box.bottom);
        pointPageToView.offset(this.pageView.getLeft(), this.pageView.getTop());
        pointPageToView.offset(-this.docView.getScrollX(), -this.docView.getScrollY());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.editorView.getLayoutParams();
        layoutParams.leftMargin = pointPageToView.x;
        layoutParams.topMargin = pointPageToView.y;
        this.editorView.setLayoutParams(layoutParams);
    }

    public void preMoving() {
        if (this.editText.isEnabled()) {
            saveData();
            this.editText.setEnabled(false);
        }
    }

    public void saveData() {
        this.dataHandler.setComment(this.editText.getText().toString());
    }

    public void setCommentEditable(boolean editable) {
        this.editText.setEnabled(editable);
    }

    public void show(SOSelectionLimits limits, DocPageView pageView) {
        this.selectionLimits = limits;
        this.pageView = pageView;
        SODoc doc = this.docView.getDoc();
        String author = this.dataHandler.getAuthor();
        String date = this.dataHandler.getDate();
        String comment = this.dataHandler.getComment();
        if (author == null || author.isEmpty()) {
            this.authorTextView.setVisibility(View.GONE);
        } else {
            this.authorTextView.setVisibility(View.VISIBLE);
            this.authorTextView.setText(author);
        }
        if (date == null || date.isEmpty()) {
            this.dateTextView.setVisibility(View.GONE);
        } else {
            this.dateTextView.setVisibility(View.VISIBLE);
            this.dateTextView.setText(Utilities.formatDateForLocale(this.docView.getContext(), date, doc.getDateFormatPattern()));
        }
        this.editText.setText(comment);
        this.editorView.setVisibility(View.VISIBLE);
    }
}
