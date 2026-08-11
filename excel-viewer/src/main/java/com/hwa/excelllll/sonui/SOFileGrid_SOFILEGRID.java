package com.hwa.excelllll.sonui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/* JADX INFO: loaded from: classes.dex */
public class SOFileGrid extends GridView implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Listener f4478a;

    public interface Listener {
        void a(int i);
    }

    public SOFileGrid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnItemClickListener(this);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        Listener listener = this.f4478a;
        if (listener != null) {
            listener.a(i);
        }
    }

    public void setListener(Listener listener) {
        this.f4478a = listener;
    }
}
