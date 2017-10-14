package com.swiezaki.swiezakimobile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

class LoginInputView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_login_input, this, true)
    }
}
