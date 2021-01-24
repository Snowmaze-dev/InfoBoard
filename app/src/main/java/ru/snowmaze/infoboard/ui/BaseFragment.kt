package ru.snowmaze.infoboard.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import ru.snowmaze.infoboard.R

abstract class BaseFragment (private val layoutRes: Int) : Fragment() {

    private lateinit var themeContext: ContextThemeWrapper
    protected var theme = R.style.Light

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        theme = (requireActivity() as ThemedActivity).currentTheme
        themeContext = ContextThemeWrapper(
            super.getContext(), theme
        )
        return LayoutInflater.from(themeContext).inflate(layoutRes, container, false)
    }

    override fun getContext(): Context {
        return themeContext
    }

}