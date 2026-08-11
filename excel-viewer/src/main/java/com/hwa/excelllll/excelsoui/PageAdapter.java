package com.hwa.excelllll.excelsoui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.hwa.excelllll.excelsolib.SODoc;

public class PageAdapter extends BaseAdapter {
    public static final int DOC_KIND = 1;
    public static final int PDF_KIND = 2;
    DocViewHost host;
    private final Context context;
    private SODoc doc;
    private int pageCount;
    private int docKind;
    private int pageWidth = -1;

    public PageAdapter(Context context, DocViewHost host, int docKind) {
        this.host = host;
        this.context = context;
        this.docKind = docKind;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.pageCount;
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        DocPageView pageView;
        Activity activity = (Activity) this.context;
        if (convertView == null) {
            int kind = this.docKind;
            if (kind == 1) {
                pageView = new DocPageView(activity, this.doc);
            } else {
                pageView = kind == 2 ? new DocMuPdfPageView(activity, this.doc) : null;
            }
        } else {
            pageView = (DocPageView) convertView;
        }
        if (this.pageWidth == -1) {
            this.pageWidth = this.host.getDocView().getWidth();
        }
        pageView.setupPage(position, this.pageWidth, 1);
        return pageView;
    }

    public void setCount(int count) {
        this.pageCount = count;
    }

    public void setDoc(SODoc doc) {
        this.doc = doc;
    }
}
