package ru.snowmaze.infoboard.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Activity.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
}

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

//@ColorInt
//fun Context.getColorFromAttribute(@AttrRes attr: Int): Int {
//    this.withStyledAttributes(attrs = intArrayOf(attr)) {
//        return this.getColor(0, 0)
//    }
//    return 0
//}
