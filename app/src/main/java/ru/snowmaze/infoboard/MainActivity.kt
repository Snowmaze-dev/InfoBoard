package ru.snowmaze.infoboard

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import ru.snowmaze.infoboard.utils.hideKeyboard
import ru.snowmaze.themeslib.ThemeHolder

class MainActivity : AppCompatActivity() {

    private val themesHolder: ThemeHolder by inject()

    private val navigator = object : AppNavigator(this, R.id.fragments_container) {
        override fun setupFragmentTransaction(
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment?
        ) {
            fragmentTransaction.apply {
                setReorderingAllowed(true)
                currentFragment?.let { hide(it) }
            }
        }

        override fun applyCommands(commands: Array<out Command>) {
            hideKeyboard()
            super.applyCommands(commands)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) router.replaceScreen(homeScreen())
        findViewById<FragmentContainerView>(R.id.fragments_container).setOnApplyWindowInsetsListener { view, insets ->
            if (findViewById<View>(R.id.toolbar).dispatchApplyWindowInsets(insets).isConsumed) insets.consumeSystemWindowInsets() else insets
        }
    }


    override fun onResumeFragments() {
        super.onResumeFragments()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragments_container)!!
        if (fragment is BackPressable) {
            if (fragment.onBackPressed()) router.exit()
        } else super.onBackPressed()
    }
}