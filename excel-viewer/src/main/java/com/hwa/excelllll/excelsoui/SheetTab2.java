package com.hwa.excelllll.excelsoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.hwa.excelllll.R;

public class SheetTab2 extends LinearLayout {
    private static boolean editingEnabled;
    private int sheetNumber;
    private boolean highlighted;

    public SheetTab2(Context context) {
        super(context);
        this.sheetNumber = 0;
        this.highlighted = false;
        LayoutInflater.from(context).inflate(R.layout.sodk_editor_sheet_tab, this);
    }

    private View getOuterView() {
        return findViewById(R.id.sheetTab);
    }

    private Space getSpacerView() {
        return (Space) findViewById(R.id.spacer);
    }

    private ImageView getXView() {
        return (ImageView) findViewById(R.id.button2);
    }

    public static void setEditingEnabled(boolean enabled) {
        editingEnabled = enabled;
    }

    public SOTextView getNameView() {
        return (SOTextView) findViewById(R.id.button1);
    }

    public int getSheetNumber() {
        return this.sheetNumber;
    }

    public String getText() {
        return getNameView().getText().toString();
    }

    @SuppressLint("DrawAllocation")
    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.highlighted) {
            @SuppressLint("DrawAllocation") Paint paint = new Paint();
            paint.setColor(getContext().getColor(R.color.sodk_editor_excel_sheet_tab_highlight_color));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Utilities.convertDpToPixel(getContext().getResources().getInteger(R.integer.sodk_editor_selected_page_border_width)));
            canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override // android.view.View
    public boolean performClick() {
        return getOuterView().performClick();
    }

    public void setHighlight(boolean highlighted) {
        this.highlighted = highlighted;
        invalidate();
    }

    public void setOnClickDelete(final View.OnClickListener onClickListener) {
        getXView().setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.SheetTab2.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickListener.onClick(SheetTab2.this);
            }
        });
    }

    public void setOnClickTab(final View.OnClickListener onClickListener) {
        getOuterView().setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.SheetTab2.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickListener.onClick(SheetTab2.this);
            }
        });
    }

    public void setOnLongClickTab(final View.OnLongClickListener onLongClickListener) {
        getOuterView().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.artifex.sonui.editor.SheetTab2.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                onLongClickListener.onLongClick(SheetTab2.this);
                return true;
            }
        });
    }

    @Override // android.view.View
    public void setSelected(boolean selected) {
        getNameView().setSelected(selected);
        getXView().setSelected(selected);
        if (selected) {
            setBackgroundResource(R.drawable.sodk_editor_sheet_tab_selected);
            showXView(editingEnabled);
        }
        setBackgroundResource(R.drawable.sodk_editor_sheet_tab);
        showXView(editingEnabled);
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public void setText(String text) {
        getNameView().setText(text);
    }

    public void showXView(boolean visible) {
        ImageView xView;
        int visibility;
        if (visible) {
            xView = getXView();
            visibility = VISIBLE;
        } else {
            xView = getXView();
            visibility = GONE;
        }
        xView.setVisibility(visibility);
        getSpacerView().setVisibility(visibility);
    }

    public SheetTab2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sheetNumber = 0;
        this.highlighted = false;
    }
}
