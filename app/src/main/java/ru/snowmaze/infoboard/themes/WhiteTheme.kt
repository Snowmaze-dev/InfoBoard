package ru.snowmaze.infoboard.themes

import android.content.Context
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.utils.color
import ru.snowmaze.themeslib.Theme

class WhiteTheme(context: Context) : Theme {

    override val id = 0

    override val mainColor = context.color(R.color.white_theme_main)

    override val mainColorLighter = mainColor

    override val toolbarColor = context.color(R.color.white_theme_accent)

    override val toolbarTextColor = context.color(R.color.dark_theme_toolbar_text)

    override val textColor = context.color(R.color.white_theme_text)

    override val tintColor = context.color(R.color.white_theme_image_button)

    override val additionalTextColor = context.color(R.color.white_theme_additional_text)

}