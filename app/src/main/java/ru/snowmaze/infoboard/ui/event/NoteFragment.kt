package ru.snowmaze.infoboard.ui.event

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.snowmaze.infoboard.BackPressable
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.databinding.FragmentEventBinding
import ru.snowmaze.infoboard.ui.BaseFragment
import ru.snowmaze.themeslib.Theme

class NoteFragment: BaseFragment(R.layout.fragment_event), BackPressable {

    private val binding: FragmentEventBinding by viewBinding()

    override fun onThemeChanged(theme: Theme) {
        with(theme) {
            binding.root.setBackgroundColor(mainColor)
        }
    }

    override fun initView(view: View, theme: Theme, savedInstanceState: Bundle?) {
        onThemeChanged(theme)
    }

}