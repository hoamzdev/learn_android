package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;

/* JADX INFO: loaded from: classes.dex */
public class SheetTab extends LinearLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static boolean f4775a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f4776b;
    private boolean c;

    public SheetTab(Context context) {
        super(context);
        this.f4776b = 0;
        this.c = false;
        LayoutInflater.from(context).inflate(R.layout.sodk_editor_sheet_tab, this);
    }

    private View getOuterView() {
        return findViewById(R.id.sheetTab);
    }

    private Space getSpacerView() {
        return (Space) findViewById(R.id.spacer);
    }

    private SOTextView getXView() {
        return (SOTextView) findViewById(R.id.button2);
    }

    public static void setEditingEbabled(boolean z) {
        f4775a = z;
    }

    public SOTextView getNameView() {
        return (SOTextView) findViewById(R.id.button1);
    }

    public int getSheetNumber() {
        return this.f4776b;
    }

    public String getText() {
        return getNameView().getText().toString();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.c) {
            Paint paint = new Paint();
            paint.setColor(getContext().getColor(R.color.sodk_editor_excel_sheet_tab_highlight_color));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Utilities.convertDpToPixel(getContext().getResources().getInteger(R.integer.sodk_editor_selected_page_border_width)));
            canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        return getOuterView().performClick();
    }

    public void setHighlight(boolean z) {
        this.c = z;
        invalidate();
    }

    public void setOnClickDelete(final OnClickListener onClickListener) {
        getXView().setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.SheetTab.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickListener.onClick(this);
            }
        });
    }

    public void setOnClickTab(final OnClickListener onClickListener) {
        getOuterView().setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.SheetTab.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickListener.onClick(this);
            }
        });
    }

    public void setOnLongClickTab(final OnLongClickListener onLongClickListener) {
        getOuterView().setOnLongClickListener(new OnLongClickListener() { // from class: com.artifex.sonui.editor.SheetTab.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                onLongClickListener.onLongClick(this);
                return true;
            }
        });
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        getNameView().setSelected(z);
        getXView().setSelected(z);
        if (z) {
            setBackgroundResource(R.drawable.sodk_editor_sheet_tab_selected);
            boolean z2 = f4775a;
            showXView(z2);
        }
        setBackgroundResource(R.drawable.sodk_editor_sheet_tab);
        showXView(z2);
    }

    public void setSheetNumber(int i) {
        this.f4776b = i;
    }

    public void setText(String str) {
        getNameView().setText(str);
    }

    public void showXView(boolean z) {
        SOTextView xView;
        int i;
        if (z) {
            xView = getXView();
            i = 0;
        } else {
            xView = getXView();
            i = 8;
        }
        xView.setVisibility(i);
        getSpacerView().setVisibility(i);
    }

    public SheetTab(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4776b = 0;
        this.c = false;
    }
}
