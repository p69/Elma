package com.example.hearthstoneexplorer

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

fun (AppCompatActivity).hideVirtualKeyboard() : Boolean {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    return imm?.hideSoftInputFromWindow(this.window?.decorView?.windowToken, 0) ?: false
}