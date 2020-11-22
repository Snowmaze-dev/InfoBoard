package ru.snowmaze.infoboard.utils

import android.content.res.Resources

fun Int.dp() = this * Resources.getSystem().displayMetrics.density.toInt()