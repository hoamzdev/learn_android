package com.hwa.excelllll.sonui;

import android.content.Context;
import android.supportv1.v7.widget.LinearLayoutManager;
import android.supportv1.v7.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class MyLinearLayoutManager extends LinearLayoutManager {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a f4477a;

    public interface a {
        void a();
    }

    public MyLinearLayoutManager(Context context) {
        super(0, false);
        this.f4477a = null;
    }

    public a a() {
        return this.f4477a;
    }

    @Override // android.supportv1.v7.widget.LinearLayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        a aVar = this.f4477a;
        if (aVar != null) {
            aVar.a();
        }
    }

    public void a(a aVar) {
        this.f4477a = aVar;
    }
}
