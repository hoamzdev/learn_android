package com.example.testBaseApp.utils

class PDFWidget(j: Long) : PDFAnnotation(j) {
    companion object {
        const val PDF_BTN_FIELD_IS_NO_TOGGLE_TO_OFF = 16384

        const val PDF_BTN_FIELD_IS_PUSHBUTTON = 65536

        const val PDF_BTN_FIELD_IS_RADIO = 32768

        const val PDF_CH_FIELD_IS_COMBO = 131072

        const val PDF_CH_FIELD_IS_EDIT = 262144

        const val PDF_CH_FIELD_IS_MULTI_SELECT = 2097152

        const val PDF_CH_FIELD_IS_SORT = 524288

        const val PDF_FIELD_IS_NO_EXPORT = 4

        const val PDF_FIELD_IS_READ_ONLY = 1

        const val PDF_FIELD_IS_REQUIRED = 2

        const val PDF_TX_FIELD_IS_COMB = 16777216

        const val PDF_TX_FIELD_IS_MULTILINE = 4096

        const val PDF_TX_FIELD_IS_PASSWORD = 8192

        const val TX_FORMAT_DATE = 3

        const val TX_FORMAT_NONE = 0

        const val TX_FORMAT_NUMBER = 1

        const val TX_FORMAT_SPECIAL = 2

        const val TX_FORMAT_TIME = 4

        const val TYPE_UNKNOWN = 0

        const val TYPE_BUTTON = 1

        const val TYPE_CHECKBOX = 2

        const val TYPE_COMBOBOX = 3

        const val TYPE_LISTBOX = 4

        const val TYPE_RADIOBUTTON = 5

        const val TYPE_SIGNATURE = 6

        const val TYPE_TEXT = 7

        init {
            Context.init()
        }
    }

    private var fieldFlags: Int = 0

    private var fieldType: Int = 0

    private var maxLen: Int = 0

    private var options: Array<String>? = null

    private var originalValue: String? = null

    private var textFormat: Int = 0

    fun cancelEditing() {
        setValue(originalValue)
        setEditing(false)
    }

    fun commitEditing(value: String): Boolean {
        setValue(originalValue)
        setEditing(false)
        return if (setTextValue(value)) {
            true
        } else {
            setEditing(true)
            false
        }
    }

    fun getFieldFlags(): Int {
        return fieldFlags
    }

    fun getFieldType(): Int {
        return fieldType
    }

    fun getMaxLen(): Int {
        return maxLen
    }

    fun getOptions(): Array<String>? {
        return options
    }

    fun getTextFormat(): Int {
        return textFormat
    }

    external fun getValue(): String?
    fun isButton(): Boolean {
        val type = getFieldType()
        return type == TYPE_BUTTON || type == TYPE_CHECKBOX || type == TYPE_RADIOBUTTON
    }

    fun isCheckbox(): Boolean {
        return getFieldType() == TYPE_CHECKBOX
    }

    fun isChoice(): Boolean {
        val type = getFieldType()
        return type == TYPE_COMBOBOX || type == TYPE_LISTBOX
    }

    fun isComb(): Boolean {
        return (getFieldFlags() and PDF_TX_FIELD_IS_COMB) != 0
    }

    fun isComboBox(): Boolean {
        return getFieldType() == TYPE_COMBOBOX
    }

    external fun isEditing(): Boolean
    fun isListBox(): Boolean {
        return getFieldType() == TYPE_LISTBOX
    }

    fun isMultiline(): Boolean {
        return (getFieldFlags() and PDF_TX_FIELD_IS_MULTILINE) != 0
    }

    fun isPassword(): Boolean {
        return (getFieldFlags() and PDF_TX_FIELD_IS_PASSWORD) != 0
    }

    fun isPushButton(): Boolean {
        return getFieldType() == TYPE_BUTTON
    }

    fun isRadioButton(): Boolean {
        return getFieldType() == TYPE_RADIOBUTTON
    }

    fun isReadOnly(): Boolean {
        return (getFieldFlags() and PDF_FIELD_IS_READ_ONLY) != 0
    }

    external fun isSigned(): Boolean
    fun isText(): Boolean {
        return getFieldType() == TYPE_TEXT
    }

    external fun setChoiceValue(value: String): Boolean
    external fun setEditing(editing: Boolean)
    external fun setTextValue(value: String): Boolean
    external fun setValue(value: String?): Boolean
//    external fun sign(signer: PKCS7Signer): Boolean
    fun startEditing() {
        setEditing(true)
        originalValue = getValue()
    }

//    external fun textQuads(): Array<Quad>
//    external fun toggle(): Boolean
//    external fun verify(verifier: PKCS7Verifier): Boolean
}