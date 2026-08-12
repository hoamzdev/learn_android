package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import com.hwa.excelllll.core.SODoc

/**
 * Small native-editor adapter.
 *
 * This is deliberately not a full spreadsheet text widget:
 * the selected cell remains owned by SODoc and this EditText only
 * provides Android keyboard/input integration.
 */
@SuppressLint("ViewConstructor")
class ExcelCellEditView(
    context: Context,
    private val document: SODoc
) : AppCompatEditText(context) {

    init {
        setSingleLine(false)
        imeOptions = EditorInfo.IME_ACTION_DONE
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                commitEdit()
                true
            } else {
                false
            }
        }
    }

    fun beginEdit() {
        setText(document.getSelectionAsText().orEmpty())
        setSelection(text?.length ?: 0)
        requestFocus()
    }

    fun commitEdit(): Boolean {
        if (!document.getSelectionCanHaveTextAltered()) return false
        document.setSelectionText(text.toString(), 0, true)
        return true
    }

    fun cancelEdit() {
        clearFocus()
    }

}
