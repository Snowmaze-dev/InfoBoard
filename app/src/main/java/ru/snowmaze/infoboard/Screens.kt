package ru.snowmaze.infoboard

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.snowmaze.infoboard.ui.event.NoteFragment
import ru.snowmaze.infoboard.ui.home.HomeFragment

fun homeScreen() = FragmentScreen { HomeFragment() }

fun noteScreen() = FragmentScreen { NoteFragment() }