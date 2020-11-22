package ru.snowmaze.infoboard.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun Fragment.showText(text: String) =
    view?.let { Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show() }

fun Fragment.showText(@StringRes res: Int) = showText(getString(res))