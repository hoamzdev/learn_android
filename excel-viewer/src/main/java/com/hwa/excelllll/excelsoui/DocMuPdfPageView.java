package com.hwa.excelllll.excelsoui;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hwa.excelllll.excelsolib.SODoc;

/** Page view used by the adapter for PDF-backed documents. */
@SuppressLint("ViewConstructor")
public class DocMuPdfPageView extends DocPageView {
    public DocMuPdfPageView(Context context, SODoc document) {
        super(context, document);
    }
}
