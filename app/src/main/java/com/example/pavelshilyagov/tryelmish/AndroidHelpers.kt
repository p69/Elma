package com.example.pavelshilyagov.tryelmish

import android.content.Context
import android.support.v4.app.SupportActivity
import android.view.inputmethod.InputMethodManager

fun (SupportActivity).hideVirtualKeyboard() : Boolean {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    return imm?.hideSoftInputFromWindow(this.window?.decorView?.windowToken, 0) ?: false
}