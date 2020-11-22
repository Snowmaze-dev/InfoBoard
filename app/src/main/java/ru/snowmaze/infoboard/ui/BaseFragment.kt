package ru.snowmaze.infoboard.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.snowmaze.themeslib.Theme
import ru.snowmaze.themeslib.ThemeHolder

abstract class BaseFragment : Fragment {

    protected val themeHolder: ThemeHolder by inject()
    protected val theme
        get() = themeHolder.themeLiveData.value!!

    constructor() : super()

    constructor(contentLayoutId: Int) : super(contentLayoutId)

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view, theme, savedInstanceState)
        var flag = true
        themeHolder.themeLiveData.observe(viewLifecycleOwner) {
            if(flag) {
                flag = false
                return@observe
            }
            onThemeChanged(it)
        }
    }

    abstract fun onThemeChanged(theme: Theme)

    abstract fun initView(view: View, theme: Theme, savedInstanceState: Bundle?)

}