package ru.snowmaze.themeslib

import androidx.lifecycle.MutableLiveData

class ThemeHolder(initialTheme: Theme) {

    val themeLiveData = MutableLiveData<Theme>().apply {
        value = initialTheme
    }

}