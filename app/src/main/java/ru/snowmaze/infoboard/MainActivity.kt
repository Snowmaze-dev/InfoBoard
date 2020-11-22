package ru.snowmaze.infoboard

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.snowmaze.infoboard.utils.hideKeyboard


class MainActivity : AppCompatActivity() {

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


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        setStatusBarImmersiveMode(Color.TRANSPARENT)
    }

    protected fun setStatusBarImmersiveMode(@ColorInt color: Int) {

        // StatusBar
        // 19, 4.4, KITKAT
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= 21) { // 21, 5.0, LOLLIPOP
            window.getAttributes().systemUiVisibility =
                window.getAttributes().systemUiVisibility or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }

        // Setup immersive mode on third-party rom
        if (Build.VERSION.SDK_INT >= 19) { // 19, 4.4, KITKAT
//            FlymeUtils.setStatusBarDarkIcon(win, false);
//            MIUIUtils.setStatusBar(window, MIUIUtils.StatusBarMode.TRANSPARENT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) router.replaceScreen(homeScreen())
        val v = findViewById<View>(R.id.fragments_container)
        v.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or
                SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        v.setOnApplyWindowInsetsListener { v, insets ->
            v.onApplyWindowInsets(insets)
            v.updatePadding(bottom = insets.systemWindowInsetBottom)
            v.findViewById<View>(R.id.toolbar).dispatchApplyWindowInsets(insets)
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