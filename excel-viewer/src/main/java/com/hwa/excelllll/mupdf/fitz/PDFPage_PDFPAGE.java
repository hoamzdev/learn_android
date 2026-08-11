package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class PDFPage extends Page {
    private PDFWidget[] widgets;

    static {
        Context.init();
    }

    private PDFPage(long j) {
        super(j);
    }

    private native PDFWidget[] getWidgetsNative();

    public PDFWidget activateWidgetAt(float f, float f2) {
        for (PDFWidget pDFWidget : getWidgets()) {
            if (pDFWidget.getBounds().contains(f, f2)) {
                pDFWidget.eventEnter();
                pDFWidget.eventDown();
                pDFWidget.eventFocus();
                pDFWidget.eventUp();
                pDFWidget.eventExit();
                pDFWidget.eventBlur();
                return pDFWidget;
            }
        }
        return null;
    }

    public native boolean applyRedactions();

    public native PDFAnnotation createAnnotation(int i);

    public native void deleteAnnotation(PDFAnnotation pDFAnnotation);

    public native PDFAnnotation[] getAnnotations();

    public PDFWidget[] getWidgets() {
        if (this.widgets == null) {
            this.widgets = getWidgetsNative();
        }
        return this.widgets;
    }

    public native boolean update();
}
