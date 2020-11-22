package ru.snowmaze.infoboard

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.snowmaze.datasources.di.dataSourcesModules
import ru.snowmaze.infoboard.App.Companion.INSTANCE
import ru.snowmaze.infoboard.themes.DarkTheme
import ru.snowmaze.infoboard.themes.WhiteTheme
import ru.snowmaze.infoboard.ui.home.HomeViewModel
import ru.snowmaze.themeslib.Theme
import ru.snowmaze.themeslib.ThemeHolder

class App : Application() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@App)
            modules(dataSourcesModules)
            modules(
                module {
                    single<Theme> {
                        get<WhiteTheme>()
                    }
                    single {
                        DarkTheme(this@App)
                    }
                    single {
                        WhiteTheme(this@App)
                    }
                    single {
                        ThemeHolder(get())
                    }
                },
                module {
                    viewModel { HomeViewModel(get()) }
                }
            )
        }
    }

    companion object {

        internal lateinit var INSTANCE: App
            private set

    }

}

val Fragment.router get() = INSTANCE.router

val Activity.router get() = INSTANCE.router