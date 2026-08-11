package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class PDFDocument extends Document {

    public interface JsEventListener {
        void onAlert(String str);
    }

    static {
        Context.init();
    }

    public PDFDocument() {
        super(newNative());
    }

    private native PDFObject addPageBuffer(Rect rect, int i, PDFObject pDFObject, Buffer buffer);

    private native PDFObject addPageString(Rect rect, int i, PDFObject pDFObject, String str);

    private native PDFObject addStreamBuffer(Buffer buffer, Object obj, boolean z);

    private native PDFObject addStreamString(String str, Object obj, boolean z);

    private static native long newNative();

    public native PDFObject addCJKFont(Font font, int i, int i2, boolean z);

    public native PDFObject addFont(Font font);

    public native PDFObject addImage(Image image);

    public native PDFObject addObject(PDFObject pDFObject);

    public PDFObject addPage(Rect rect, int i, PDFObject pDFObject, Buffer buffer) {
        return addPageBuffer(rect, i, pDFObject, buffer);
    }

    public PDFObject addRawStream(Buffer buffer) {
        return addStreamBuffer(buffer, null, true);
    }

    public native PDFObject addSimpleFont(Font font, int i);

    public PDFObject addStream(Buffer buffer) {
        return addStreamBuffer(buffer, null, false);
    }

    public native void calculate();

    public native boolean canBeSavedIncrementally();

    public native int countObjects();

    public native PDFObject createObject();

    public native void deleteObject(int i);

    public void deleteObject(PDFObject pDFObject) {
        deleteObject(pDFObject.asIndirect());
    }

    public native void deletePage(int i);

    public native void disableJs();

    public native void enableJs();

    public native PDFObject findPage(int i);

    public native PDFObject getTrailer();

    public native PDFObject graftObject(PDFObject pDFObject);

    public boolean hasAcroForm() {
        return getTrailer().get("Root").get("AcroForm").get("Fields").size() > 0;
    }

    public native boolean hasUnsavedChanges();

    public boolean hasXFAForm() {
        return !getTrailer().get("Root").get("AcroForm").get("XFA").isNull();
    }

    public native void insertPage(int i, PDFObject pDFObject);

    public native boolean isJsSupported();

    @Override // com.artifex.mupdf.fitz.Document
    public boolean isPDF() {
        return true;
    }

    public native void nativeSaveWithStream(SeekableInputOutputStream seekableInputOutputStream, String str);

    public native PDFObject newArray();

    public native PDFObject newBoolean(boolean z);

    public native PDFObject newByteString(byte[] bArr);

    public native PDFObject newDictionary();

    public native PDFObject newIndirect(int i, int i2);

    public native PDFObject newInteger(int i);

    public native PDFObject newName(String str);

    public native PDFObject newNull();

    public native PDFGraftMap newPDFGraftMap();

    public native PDFObject newReal(float f);

    public native PDFObject newString(String str);

    public void save(SeekableInputOutputStream seekableInputOutputStream, String str) {
        nativeSaveWithStream(seekableInputOutputStream, str);
    }

    public native void save(String str, String str2);

    public native void setJsEventListener(JsEventListener jsEventListener);

    public PDFObject addPage(Rect rect, int i, PDFObject pDFObject, String str) {
        return addPageString(rect, i, pDFObject, str);
    }

    public PDFObject addRawStream(Buffer buffer, Object obj) {
        return addStreamBuffer(buffer, obj, true);
    }

    public PDFObject addStream(Buffer buffer, Object obj) {
        return addStreamBuffer(buffer, obj, false);
    }

    public PDFDocument(long j) {
        super(j);
    }

    public PDFObject addRawStream(String str) {
        return addStreamString(str, null, true);
    }

    public PDFObject addStream(String str) {
        return addStreamString(str, null, false);
    }

    public PDFObject addRawStream(String str, Object obj) {
        return addStreamString(str, obj, true);
    }

    public PDFObject addStream(String str, Object obj) {
        return addStreamString(str, obj, false);
    }
}
