package ru.snowmaze.infoboard.utils

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData

fun Int.dp() = this * Resources.getSystem().displayMetrics.density.toInt()

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}