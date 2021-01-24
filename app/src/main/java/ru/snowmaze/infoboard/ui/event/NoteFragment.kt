package ru.snowmaze.infoboard.ui.event

import by.kirich1409.viewbindingdelegate.viewBinding
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.databinding.FragmentEventBinding
import ru.snowmaze.infoboard.ui.BackPressable
import ru.snowmaze.infoboard.ui.BaseFragment

class NoteFragment: BaseFragment(R.layout.fragment_event), BackPressable {

    private val binding: FragmentEventBinding by viewBinding()

}