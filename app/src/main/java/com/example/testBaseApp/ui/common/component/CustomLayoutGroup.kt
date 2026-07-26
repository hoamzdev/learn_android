package com.example.testBaseApp.ui.common.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testBaseApp.R
import com.example.testBaseApp.component.setOnClickShrinkListener

class CustomLayoutGroup @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, deffStyle: Int = 0
) : ConstraintLayout(context, attributeSet, deffStyle) {
    private val buttonSend = AppCompatButton(context)

    private var onClick: () -> Unit = {}
    fun setOnClickSendListener(block: () -> Unit) {
        this.onClick = block
    }

    init {
        buttonSend.id = generateViewId()
        val paramsButton = LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        ).apply {
            topToTop = LayoutParams.PARENT_ID
            startToStart = LayoutParams.PARENT_ID
            endToEnd = LayoutParams.PARENT_ID
        }
        paramsButton.setMargins(left, top, right, 0)
        buttonSend.text = "Send"
        buttonSend.isAllCaps = false
        buttonSend.setBackgroundResource(R.drawable.bg_12)
        buttonSend.setTextColor(Color.WHITE)
        addView(buttonSend, paramsButton)
        handleClickEven()
    }

    private fun handleClickEven() {
        buttonSend.setOnClickShrinkListener {
            onClick.invoke()
        }
    }
}