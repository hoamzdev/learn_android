package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.artifex.solib.k;

/* JADX INFO: loaded from: classes.dex */
public class ChooseFormulaAdapter extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final String[] f4517a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Context f4518b;

    public ChooseFormulaAdapter(Activity activity, String str) {
        this.f4518b = activity.getBaseContext();
        this.f4517a = k.b(activity).getFormulae(str);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f4517a.length;
    }

    @Override // android.widget.Adapter
    public String getItem(int i) {
        return this.f4517a[i].split("\\|")[0];
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        String[] strArrSplit = this.f4517a[i].split("\\|");
        if (view == null) {
            view = LayoutInflater.from(this.f4518b).inflate(R.layout.sodk_editor_formula_view, (ViewGroup) null);
        }
        ((SOTextView) view.findViewById(R.id.formula)).setText(strArrSplit[0]);
        ((SOTextView) view.findViewById(R.id.description)).setText(strArrSplit[1]);
        return view;
    }
}
