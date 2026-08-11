package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.artifex.solib.SODoc;

/* JADX INFO: loaded from: classes.dex */
public class PageAdapter extends BaseAdapter {
    public static final int DOC_KIND = 1;
    public static final int PDF_KIND = 2;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    DocViewHost f4718a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Context f4719b;
    private SODoc c;
    private int d;
    private int e;
    private int f = -1;

    public PageAdapter(Context context, DocViewHost docViewHost, int i) {
        this.f4718a = docViewHost;
        this.f4719b = context;
        this.e = i;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.d;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        DocPageView docMuPdfPageView;
        Activity activity = (Activity) this.f4719b;
        if (view == null) {
            int i2 = this.e;
            if (i2 == 1) {
                docMuPdfPageView = new DocPageView(activity, this.c);
            } else {
                docMuPdfPageView = i2 == 2 ? new DocMuPdfPageView(activity, this.c) : null;
            }
        } else {
            docMuPdfPageView = (DocPageView) view;
        }
        if (this.f == -1) {
            this.f = this.f4718a.getDocView().getWidth();
        }
        docMuPdfPageView.setupPage(i, this.f, 1);
        return docMuPdfPageView;
    }

    public void setCount(int i) {
        this.d = i;
    }

    public void setDoc(SODoc sODoc) {
        this.c = sODoc;
    }
}
