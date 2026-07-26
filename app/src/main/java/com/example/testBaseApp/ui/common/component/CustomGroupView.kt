package com.example.testBaseApp.ui.common.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.testBaseApp.R
import com.example.testBaseApp.component.setOnSingleListener
import com.example.testBaseApp.utils.toDp

@SuppressLint("RestrictedApi")
class CustomGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val tvTitle = TextView(context)
    private val btnRetry = AppCompatButton(context)
    private var onClickButtonRetryListener : (() -> Unit) = {}
    fun setOnClickButtonRetryListener(block :() -> Unit){
        this.onClickButtonRetryListener = block
    }

    init {
        tvTitle.id = generateViewId()
        btnRetry.id = generateViewId()
        val paramsTitle = LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        ).apply {
            topToTop = LayoutParams.PARENT_ID
            bottomToBottom = LayoutParams.PARENT_ID
            startToStart = LayoutParams.PARENT_ID
            endToEnd = LayoutParams.PARENT_ID
        }
        tvTitle.gravity = Gravity.CENTER_HORIZONTAL
        addView(tvTitle, paramsTitle)

        context.obtainStyledAttributes(attrs, R.styleable.CustomGroupView).apply {
            val title = getString(R.styleable.CustomGroupView_title) ?: ""
            setTitle(title)
            recycle()
        }

        val paramsButton = LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        ).apply {
            topToBottom = tvTitle.id
            startToStart = LayoutParams.PARENT_ID
            endToEnd = LayoutParams.PARENT_ID
        }
        btnRetry.apply {
            text = context.getString(R.string.try_again)
            setTextColor(ContextCompat.getColor(context, R.color.white))
            background = ContextCompat.getDrawable(context, R.drawable.bg_item_language_on)
            elevation = 10.toFloat()
            isAllCaps = false
            setOnSingleListener {
                onClickButtonRetryListener.invoke()
            }
        }
        addView(btnRetry, paramsButton)
    }
    fun setTitle(text: String) {
        tvTitle.text = text
    }
}