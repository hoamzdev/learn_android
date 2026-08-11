package com.hwa.excelllll.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xlsxreader.excelreader.R;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class EditBtn extends ConstraintLayout {

    /* JADX INFO: renamed from: q */
    public final View f48284q;

    public EditBtn(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.layout_edit_buton, (ViewGroup) this, true);
        if (attributeSet != null) {
            this.f48284q = findViewById(R.id.viewBg);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EditBtn);
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.EditBtn_ImageEdt, R.drawable.ic_preview_highlight);
            if (resourceId > 0) {
                ((ImageView) findViewById(R.id.imgEdit)).setImageResource(resourceId);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        setClickable(true);
        setFocusable(true);
    }

    public void setChoose(boolean z) {
        View view = this.f48284q;
        if (z) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
    }

    public void setEnable(boolean z) {
        setAlpha(z ? 1.0f : 0.4f);
        setClickable(z);
        setFocusable(z);
    }

    public EditBtn(Context context) {
        super(context);
    }

    public EditBtn(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
