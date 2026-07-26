package com.example.testBaseApp.ui.common.component

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.testBaseApp.component.setOnSingleListener

class CustomAddViewDynamic @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private val btnAdd = AppCompatButton(context)
    private var lastViewId : Int = NO_ID
    init {
        btnAdd.id = generateViewId()
        btnAdd.text = "Add View To Group"
        val btnParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            topToTop = LayoutParams.PARENT_ID
            startToStart = LayoutParams.PARENT_ID
            endToEnd = LayoutParams.PARENT_ID
            val marginTop = 20.toDp()
            setMargins(0,marginTop,0,0)
        }
        addView(btnAdd, btnParams)
        lastViewId = btnAdd.id

        btnAdd.setOnSingleListener {
            addNewRow()
        }
    }

    private fun addNewRow() {
        val edtInput = EditText(context).apply {
            id = generateViewId()
            hint = "Nhập thông tin..."
        }

        val btnDelete = TextView(context).apply {
            id = generateViewId()
            text = "Xóa"
            setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            setPadding(16,0,16,0)
        }

        val edtParams = LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT
        ).apply {
            topToBottom = lastViewId
            startToStart = LayoutParams.PARENT_ID
            endToStart = btnDelete.id

            val marginPx = 12.toDp()
            setMargins(0, marginPx, marginPx, 0)
        }

        val deleteParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            topToTop = edtInput.id
            bottomToBottom = edtInput.id
            endToEnd = LayoutParams.PARENT_ID
        }

        addView(edtInput, edtParams)
        addView(btnDelete, deleteParams)

        lastViewId = edtInput.id

        btnDelete.setOnClickListener {
            removeView(edtInput)
            removeView(btnDelete)
        }
    }

    private fun Int.toDp() = (this * resources.displayMetrics.density).toInt()

}