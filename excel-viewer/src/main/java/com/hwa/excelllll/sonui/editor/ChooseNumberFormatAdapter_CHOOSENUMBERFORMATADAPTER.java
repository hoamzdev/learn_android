package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/* JADX INFO: loaded from: classes.dex */
public class ChooseNumberFormatAdapter extends BaseAdapter {
    public static final int NUMBER_FORMATS_ACCOUNTING = 6;
    public static final int NUMBER_FORMATS_CURRENCY = 4;
    public static final int NUMBER_FORMATS_CUSTOM = 7;
    public static final int NUMBER_FORMATS_DATETIME = 1;
    public static final int NUMBER_FORMATS_FRACTION = 3;
    public static final int NUMBER_FORMATS_GENERAL = 0;
    public static final int NUMBER_FORMATS_NUMBER = 2;
    public static final int NUMBER_FORMATS_PERCENTAGE = 5;
    public static final int NUM_CATEGORIES = 8;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String[] f4521a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Context f4522b;
    private int c;
    private int d;
    private int e;

    public ChooseNumberFormatAdapter(Activity activity, int i) {
        Context baseContext = activity.getBaseContext();
        this.f4522b = baseContext;
        String[] strArr = new String[8];
        this.f4521a = strArr;
        strArr[0] = activity.getString(R.string.sodk_editor_format_category_general);
        this.f4521a[1] = activity.getString(R.string.sodk_editor_format_category_date_and_time);
        this.f4521a[2] = activity.getString(R.string.sodk_editor_format_category_number);
        this.f4521a[3] = activity.getString(R.string.sodk_editor_format_category_fraction);
        this.f4521a[4] = activity.getString(R.string.sodk_editor_format_category_currency);
        this.f4521a[5] = activity.getString(R.string.sodk_editor_format_category_percentage);
        this.f4521a[6] = activity.getString(R.string.sodk_editor_format_category_accounting);
        this.f4521a[7] = activity.getString(R.string.sodk_editor_format_category_custom);
        this.c = 0;
        View viewInflate = LayoutInflater.from(baseContext).inflate(R.layout.sodk_editor_number_formats_view, (ViewGroup) null);
        SOTextView sOTextView = (SOTextView) viewInflate.findViewById(R.id.name);
        for (int i2 = 0; i2 < 8; i2++) {
            sOTextView.setText(this.f4521a[i2]);
            sOTextView.measure(0, 0);
            int measuredWidth = sOTextView.getMeasuredWidth();
            if (measuredWidth > this.c) {
                this.c = measuredWidth;
            }
        }
        SOTextView sOTextView2 = (SOTextView) viewInflate.findViewById(R.id.arrow);
        sOTextView2.measure(0, 0);
        int measuredWidth2 = sOTextView2.getMeasuredWidth();
        this.e = measuredWidth2;
        this.d = (this.c + measuredWidth2) * i;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f4521a.length;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewGroup.getLayoutParams().width = this.d;
        String str = this.f4521a[i];
        if (view == null) {
            view = LayoutInflater.from(this.f4522b).inflate(R.layout.sodk_editor_number_formats_view, (ViewGroup) null);
        }
        SOTextView sOTextView = (SOTextView) view.findViewById(R.id.name);
        if (sOTextView != null) {
            sOTextView.setText(str);
            sOTextView.getLayoutParams().width = this.c;
        }
        SOTextView sOTextView2 = (SOTextView) view.findViewById(R.id.arrow);
        if (sOTextView2 != null) {
            sOTextView2.getLayoutParams().width = this.e;
            if (str.equals(this.f4521a[0])) {
                sOTextView2.setText("");
            }
        }
        return view;
    }

    @Override // android.widget.Adapter
    public String getItem(int i) {
        return this.f4521a[i];
    }
}
