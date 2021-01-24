package ru.snowmaze.infoboard.utils

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.dp(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Float.dp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}