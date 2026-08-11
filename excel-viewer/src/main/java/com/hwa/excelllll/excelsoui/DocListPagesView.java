package com.hwa.excelllll.excelsoui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/** Container marker used when document pages are displayed as a page list. */
public class DocListPagesView extends ViewGroup {
    public DocListPagesView(Context context) {
        super(context);
    }

    public DocListPagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DocListPagesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // Child pages are positioned by their owning document view.
    }
}
