package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.artifex.solib.SODoc;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FontListAdapter extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f4642a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private List<String> f4643b;

    public FontListAdapter(Context context) {
        this.f4642a = context;
    }

    public void enumerateFonts(SODoc sODoc) {
        this.f4643b = Arrays.asList(sODoc.getFontList().split(","));
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f4643b.size();
    }

    @Override // android.widget.Adapter
    public String getItem(int i) {
        return this.f4643b.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        String str = this.f4643b.get(i);
        if (view == null) {
            view = LayoutInflater.from(this.f4642a).inflate(R.layout.sodk_editor_font_list_entry, (ViewGroup) null);
        }
        SOTextView sOTextView = (SOTextView) view.findViewById(R.id.name);
        sOTextView.setText(str);
        sOTextView.setTypeface(Typeface.create(str.toLowerCase(), 0));
        return view;
    }
}
