package ru.snowmaze.infoboard.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout


class WindowInsetsFrameLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    init {

        // Look for replaced fragments and apply the insets again.
        setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(parent: View?, child: View?) {
                requestApplyInsets()
            }

            override fun onChildViewRemoved(parent: View?, child: View?) {}
        })
    }
}