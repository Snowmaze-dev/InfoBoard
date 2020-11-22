package ru.snowmaze.infoboard.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Activity.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
}

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)