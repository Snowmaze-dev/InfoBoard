package ru.snowmaze.infoboard.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

fun View.setTint(color: Int) {
    if (this is ImageView) {
        setColorFilter(color)
    } else {
        val colorStateList = ColorStateList(arrayOf(intArrayOf()), intArrayOf(color))
        if (this is TextView) {
            TextViewCompat.setCompoundDrawableTintList(this, colorStateList)
        } else
            ViewCompat.setBackgroundTintList(
                this,
                colorStateList
            )
    }
}

fun ImageButton.setTint(color: Int) {
    val colorStateList = ColorStateList(arrayOf(intArrayOf()), intArrayOf(color))
    ViewCompat.setBackgroundTintList(
        this,
        colorStateList
    )
}

fun RecyclerView.LayoutManager.smoothScrollToPosition(
    context: Context,
    position: Int,
    millisecondsPerInch: Float
) {
    val scroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_END
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return millisecondsPerInch / displayMetrics.densityDpi
        }
    }
    scroller.targetPosition = position
    startSmoothScroll(scroller)
}