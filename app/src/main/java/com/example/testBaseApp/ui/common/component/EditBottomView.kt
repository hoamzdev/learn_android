package com.example.testBaseApp.ui.common.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.example.testBaseApp.R


@SuppressLint("CustomViewStyleable")
class EditBottomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val backgroundView: View

    init {
        LayoutInflater.from(context).inflate(
            R.layout.layout_edit_buton,
            this,
            true
        )

        backgroundView = findViewById(R.id.viewBg)

        attrs?.let {
            context.withStyledAttributes(
                it,
                R.styleable.EditBtn
            ) {

                val imageResource = getResourceId(
                    R.styleable.EditBtn_ImageEdt,
                    R.drawable.ic_preview_highlight
                )

                if (imageResource > 0) {
                    findViewById<AppCompatImageView>(R.id.imgEdit)
                        .setImageResource(imageResource)
                }

            }
        }

        isClickable = true
        isFocusable = true
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        backgroundView.visibility = if (selected) {
            VISIBLE
        } else {
            INVISIBLE
        }
    }

    fun setButtonEnabled(enabled: Boolean) {
        alpha = if (enabled) 1.0f else 0.4f
        isClickable = enabled
        isFocusable = enabled
    }
}
