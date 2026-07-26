package com.example.testBaseApp.ui.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testBaseApp.R
import com.example.testBaseApp.utils.afterTextChanged

class CustomOtpInputView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var otps: List<EditText> = emptyList()

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_otp_input, this, true)
        otps = listOf(
            findViewById(R.id.edt_one),
            findViewById(R.id.edt_two),
            findViewById(R.id.edt_three),
            findViewById(R.id.edt_four),
        )
        setupOnInputOtp()
    }

    private fun setupOnInputOtp() {
        for (i in otps.indices) {
            val currentOtp = otps[i]

            currentOtp.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    view.setBackgroundResource(R.drawable.bg_edt_12_selected)
                } else {
                    view.setBackgroundResource(R.drawable.bg_edt_12)
                }
            }

            if (i == 0) {
                currentOtp.requestFocus()
                stateChange(currentOtp,true)
                currentOtp.setBackgroundResource(R.drawable.bg_edt_12_selected)
            } else {
                stateChange(currentOtp,false)
                currentOtp.setBackgroundResource(R.drawable.bg_edt_12)
            }

            currentOtp.afterTextChanged { s ->
                if (s.length == 1) {
                    if (i < otps.size - 1) {
                        stateChange(otps[i + 1],true)
                        otps[i + 1].requestFocus()
                    }
                }
            }


            currentOtp.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (currentOtp.text.isEmpty() && i > 0) {
                        stateChange(otps[i - 1], true)
                        otps[i - 1].setText("")
                        otps[i - 1].requestFocus()
                        stateChange(otps[i], false)
                        return@setOnKeyListener true//danh dau dev tu xu ly khi nhan del
                    }
                }
                false // danh dau cho he thong android tu xu ly del
            }
        }
    }

    private fun stateChange(textView: TextView,boolean: Boolean){
        textView.isEnabled = boolean
    }

    fun getOtpString(): String {
        return otps.joinToString("") { it.text.toString() }
    }
}