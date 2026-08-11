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
public class ItemSelectSort extends LinearLayout {

    /* JADX INFO: renamed from: a */
    public final TextView f48297a;

    /* JADX INFO: renamed from: b */
    public final ImageView f48298b;

    /* JADX INFO: renamed from: c */
    public final Context f48299c;

    public ItemSelectSort(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f48299c = context;
        LayoutInflater.from(context).inflate(R.layout.item_select_sort, (ViewGroup) this, true);
        this.f48297a = (TextView) findViewById(R.id.itemSelectSort_tvTitle);
        this.f48298b = (ImageView) findViewById(R.id.itemSelectSort_img);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ItemSelectSort);
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ItemSelectSort_imgSort, R.drawable.ic_sort_date);
            if (resourceId > 0) {
                ((ImageView) findViewById(R.id.itemSelectSort_img)).setImageResource(resourceId);
            }
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.ItemSelectSort_textNameSort);
            if (string != null) {
                ((TextView) findViewById(R.id.itemSelectSort_tvTitle)).setText(string);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        setClickable(true);
        setFocusable(true);
    }

    /* JADX INFO: renamed from: a */
    public final void m21860a(int i) {
        this.f48298b.setColorFilter(this.f48299c.getColor(i));
    }

    public void setImgSort(int i) {
        this.f48298b.setImageResource(i);
    }

    public void setTextColorSort(int i) {
        this.f48297a.setTextColor(this.f48299c.getColor(i));
    }

    public ItemSelectSort(Context context) {
        super(context);
        this.f48299c = context;
    }
}
