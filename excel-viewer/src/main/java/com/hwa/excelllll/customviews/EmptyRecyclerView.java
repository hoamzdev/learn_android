package com.hwa.excelllll.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class EmptyRecyclerView extends RecyclerView {

    /* JADX INFO: renamed from: i1 */
    public static final /* synthetic */ int f48285i1 = 0;

    /* JADX INFO: renamed from: g1 */
    public View f48286g1;

    /* JADX INFO: renamed from: h1 */
    public final AdapterDataObserver f48287h1;

    public EmptyRecyclerView(Context context) {
        super(context);
        this.f48287h1 = new AdapterDataObserver() { // from class: com.xlsxreader.excelreader.customviews.EmptyRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            /* JADX INFO: renamed from: a */
            public final void mo4195a() {
                int i = EmptyRecyclerView.f48285i1;
                EmptyRecyclerView.this.m21859h0();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            /* JADX INFO: renamed from: d */
            public final void mo4198d(int i) {
                int i2 = EmptyRecyclerView.f48285i1;
                EmptyRecyclerView.this.m21859h0();
            }
        };
    }

    /* JADX INFO: renamed from: h0 */
    public final void m21859h0() {
        if (this.f48286g1 == null || getAdapter() == null) {
            return;
        }
        boolean z = getAdapter().mo4052c() == 0;
        this.f48286g1.setVisibility(z ? 0 : 8);
        setVisibility(z ? 8 : 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(Adapter adapter) {
        Adapter adapter2 = getAdapter();
        AdapterDataObserver adapterDataObserver = this.f48287h1;
        if (adapter2 != null) {
            adapter2.f7926a.unregisterObserver(adapterDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.f7926a.registerObserver(adapterDataObserver);
        }
        m21859h0();
    }

    public void setEmptyView(View view) {
        this.f48286g1 = view;
        m21859h0();
    }

    public EmptyRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f48287h1 = new AdapterDataObserver() { // from class: com.xlsxreader.excelreader.customviews.EmptyRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            /* JADX INFO: renamed from: a */
            public final void mo4195a() {
                int i = EmptyRecyclerView.f48285i1;
                EmptyRecyclerView.this.m21859h0();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            /* JADX INFO: renamed from: d */
            public final void mo4198d(int i) {
                int i2 = EmptyRecyclerView.f48285i1;
                EmptyRecyclerView.this.m21859h0();
            }
        };
    }

    public EmptyRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f48287h1 = new AdapterDataObserver() { // from class: com.xlsxreader.excelreader.customviews.EmptyRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            /* JADX INFO: renamed from: a */
            public final void mo4195a() {
                int i2 = EmptyRecyclerView.f48285i1;
                EmptyRecyclerView.this.m21859h0();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            /* JADX INFO: renamed from: d */
            public final void mo4198d(int i2) {
                int i3 = EmptyRecyclerView.f48285i1;
                EmptyRecyclerView.this.m21859h0();
            }
        };
    }
}
