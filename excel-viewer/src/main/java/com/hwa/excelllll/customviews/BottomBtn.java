package com.hwa.excelllll.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xlsxreader.excelreader.R;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class BottomBtn extends LinearLayout {
    public BottomBtn(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_button, (ViewGroup) this, true);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomBtn);
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.BottomBtn_Image, R.drawable.ic_flip_black);
            if (resourceId > 0) {
                ((ImageView) findViewById(R.id.imgButton)).setImageResource(resourceId);
            }
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.BottomBtn_Name);
            if (string != null) {
                ((TextView) findViewById(R.id.tvButton)).setText(string);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        setClickable(true);
        setFocusable(true);
    }

    public void setEnable(boolean z) {
        setAlpha(z ? 1.0f : 0.4f);
        setClickable(z);
        setFocusable(z);
    }

    public BottomBtn(Context context) {
        super(context);
    }
}
