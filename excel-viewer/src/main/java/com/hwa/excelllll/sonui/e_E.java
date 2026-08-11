package com.hwa.excelllll.sonui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.a.o.Res;
import com.artifex.sonui.editor.SOTextView;
import com.artifex.sonui.editor.Utilities;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class e extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final LinkedList<f> f4502a = new LinkedList<>();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final LayoutInflater f4503b;
    private boolean c;

    public e(LayoutInflater layoutInflater, boolean z) {
        this.c = true;
        this.f4503b = layoutInflater;
        this.c = z;
    }

    private int a(AppFile appFile) {
        return appFile.d ? appFile.getFolderResourceId() : appFile.j();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f4502a.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        try {
            return this.f4502a.get(i);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.f4503b.inflate(Res.i("sodk_picker_entry"), (ViewGroup) null);
        }
        ChooseDocListItemView chooseDocListItemView = (ChooseDocListItemView) view;
        chooseDocListItemView.a();
        chooseDocListItemView.setUseControls(this.c);
        f fVar = this.f4502a.get(i);
        fVar.f4939b = i;
        ((ImageView) view.findViewById(Res.g("icon"))).setImageResource(a(fVar.f4938a));
        Utilities.setFilenameText((SOTextView) view.findViewById(Res.g("name")), fVar.f4938a.f4253b);
        view.setTag(fVar);
        view.findViewById(Res.g("control_delete")).setTag(fVar);
        view.findViewById(Res.g("control_rename")).setTag(fVar);
        view.findViewById(Res.g("control_copy")).setTag(fVar);
        view.findViewById(Res.g("control_share")).setTag(fVar);
        view.findViewById(Res.g("control_logout")).setTag(fVar);
        return view;
    }

    public void a() {
        this.f4502a.clear();
        notifyDataSetChanged();
    }

    public void a(f fVar) {
        this.f4502a.add(fVar);
        notifyDataSetChanged();
    }
}
