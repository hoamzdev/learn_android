package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.artifex.solib.k;

/* JADX INFO: loaded from: classes.dex */
public class ChooseFormulaCategoryAdapter extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final String[] f4519a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Context f4520b;

    public ChooseFormulaCategoryAdapter(Activity activity) {
        this.f4520b = activity.getBaseContext();
        this.f4519a = k.b(activity).getFormulaeCategories();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f4519a.length;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        String str = this.f4519a[i];
        if (view == null) {
            view = LayoutInflater.from(this.f4520b).inflate(R.layout.sodk_editor_formula_category_view, (ViewGroup) null);
        }
        ((SOTextView) view.findViewById(R.id.name)).setText(str);
        return view;
    }

    @Override // android.widget.Adapter
    public String getItem(int i) {
        return this.f4519a[i];
    }
}
