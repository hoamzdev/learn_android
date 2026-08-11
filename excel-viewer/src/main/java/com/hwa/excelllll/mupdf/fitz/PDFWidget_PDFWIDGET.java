package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class PDFWidget extends PDFAnnotation {
    public static final int PDF_BTN_FIELD_IS_NO_TOGGLE_TO_OFF = 16384;
    public static final int PDF_BTN_FIELD_IS_PUSHBUTTON = 65536;
    public static final int PDF_BTN_FIELD_IS_RADIO = 32768;
    public static final int PDF_CH_FIELD_IS_COMBO = 131072;
    public static final int PDF_CH_FIELD_IS_EDIT = 262144;
    public static final int PDF_CH_FIELD_IS_MULTI_SELECT = 2097152;
    public static final int PDF_CH_FIELD_IS_SORT = 524288;
    public static final int PDF_FIELD_IS_NO_EXPORT = 4;
    public static final int PDF_FIELD_IS_READ_ONLY = 1;
    public static final int PDF_FIELD_IS_REQUIRED = 2;
    public static final int PDF_TX_FIELD_IS_COMB = 16777216;
    public static final int PDF_TX_FIELD_IS_MULTILINE = 4096;
    public static final int PDF_TX_FIELD_IS_PASSWORD = 8192;
    public static final int TX_FORMAT_DATE = 3;
    public static final int TX_FORMAT_NONE = 0;
    public static final int TX_FORMAT_NUMBER = 1;
    public static final int TX_FORMAT_SPECIAL = 2;
    public static final int TX_FORMAT_TIME = 4;
    public static final int TYPE_BUTTON = 1;
    public static final int TYPE_CHECKBOX = 2;
    public static final int TYPE_COMBOBOX = 3;
    public static final int TYPE_LISTBOX = 4;
    public static final int TYPE_RADIOBUTTON = 5;
    public static final int TYPE_SIGNATURE = 6;
    public static final int TYPE_TEXT = 7;
    public static final int TYPE_UNKNOWN = 0;
    private int fieldFlags;
    private int fieldType;
    private int maxLen;
    private String[] options;
    private String originalValue;
    private int textFormat;

    static {
        Context.init();
    }

    public PDFWidget(long j) {
        super(j);
    }

    public void cancelEditing() {
        setValue(this.originalValue);
        setEditing(false);
    }

    public boolean commitEditing(String str) {
        setValue(this.originalValue);
        setEditing(false);
        if (setTextValue(str)) {
            return true;
        }
        setEditing(true);
        return false;
    }

    public int getFieldFlags() {
        return this.fieldFlags;
    }

    public int getFieldType() {
        return this.fieldType;
    }

    public int getMaxLen() {
        return this.maxLen;
    }

    public String[] getOptions() {
        return this.options;
    }

    public int getTextFormat() {
        return this.textFormat;
    }

    public native String getValue();

    public boolean isButton() {
        int fieldType = getFieldType();
        return fieldType == 1 || fieldType == 2 || fieldType == 5;
    }

    public boolean isCheckbox() {
        return getFieldType() == 2;
    }

    public boolean isChoice() {
        int fieldType = getFieldType();
        return fieldType == 3 || fieldType == 4;
    }

    public boolean isComb() {
        return (getFieldFlags() & PDF_TX_FIELD_IS_COMB) != 0;
    }

    public boolean isComboBox() {
        return getFieldType() == 3;
    }

    public native boolean isEditing();

    public boolean isListBox() {
        return getFieldType() == 4;
    }

    public boolean isMultiline() {
        return (getFieldFlags() & PDF_TX_FIELD_IS_MULTILINE) != 0;
    }

    public boolean isPassword() {
        return (getFieldFlags() & PDF_TX_FIELD_IS_PASSWORD) != 0;
    }

    public boolean isPushButton() {
        return getFieldType() == 1;
    }

    public boolean isRadioButton() {
        return getFieldType() == 5;
    }

    public boolean isReadOnly() {
        return (getFieldFlags() & 1) != 0;
    }

    public native boolean isSigned();

    public boolean isText() {
        return getFieldType() == 7;
    }

    public native boolean setChoiceValue(String str);

    public native void setEditing(boolean z);

    public native boolean setTextValue(String str);

    public native boolean setValue(String str);

    public native boolean sign(PKCS7Signer pKCS7Signer);

    public void startEditing() {
        setEditing(true);
        this.originalValue = getValue();
    }

    public native Quad[] textQuads();

    public native boolean toggle();

    public native boolean verify(PKCS7Verifier pKCS7Verifier);
}
