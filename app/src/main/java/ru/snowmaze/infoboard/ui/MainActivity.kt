package ru.snowmaze.infoboard.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.snowmaze.infoboard.App
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.databinding.ActivityMainBinding
import ru.snowmaze.infoboard.homeScreen
import ru.snowmaze.infoboard.router
import ru.snowmaze.infoboard.utils.CubicBezierInterpolator
import ru.snowmaze.infoboard.utils.dp
import ru.snowmaze.infoboard.utils.hideKeyboard
import kotlin.math.hypot
import androidx.lifecycle.LifecycleOwner as LifecycleOwner1


class MainActivity : AppCompatActivity(), ThemedActivity {

    private val binding: ActivityMainBinding by viewBinding()
    private var _theme = R.style.Light
    override val currentTheme: Int get() = _theme

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
        _theme = appPrefs.getInt("theme", currentTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) router.replaceScreen(homeScreen())
    }


    override fun onChangeThemeClick(x: Int, y: Int, bitmap: Bitmap) {
        with(binding) {
            if(imageView.isVisible) return
            val contentRadius =
                hypot(
                    fragmentsContainer.measuredWidth.toFloat(),
                    fragmentsContainer.measuredHeight.toFloat()
                )
            val content = fragmentsContainer
            val image = imageView
            root.removeView(image)
            val view: View
            val startRadius: Float
            val finalRadius: Float
            if (currentTheme == R.style.Light) {
                _theme = R.style.Dark
                startRadius = contentRadius
                finalRadius = 0F
                root.addView(image, 1)
                view = image
            } else {
                _theme = R.style.Light
                startRadius = 0F
                finalRadius = contentRadius
                view = content
                root.addView(image, 0)
            }
            appPrefs.edit {
                putInt("theme", currentTheme)
            }
            imageView.setImageBitmap(bitmap)
            imageView.isVisible = true

            val fragment = supportFragmentManager.fragments[0]!!

            supportFragmentManager.commit {
                detach(fragment)
                attach(fragment)
            }

            fragment.viewLifecycleOwnerLiveData.observe(
                this@MainActivity, object : Observer<LifecycleOwner1> {
                    override fun onChanged(t: LifecycleOwner1?) {
                        if (t?.lifecycle?.currentState == Lifecycle.State.INITIALIZED) {
                            fragment.viewLifecycleOwnerLiveData.removeObserver(this)
                            val anim = ViewAnimationUtils.createCircularReveal(
                                view,
                                x,
                                y,
                                startRadius,
                                finalRadius
                            )
                            anim.interpolator = CubicBezierInterpolator.easeInOutQuad
                            anim.duration = 700L
                            anim.doOnEnd {
                                imageView.setImageDrawable(null)
                                imageView.isVisible = false
                            }
                            anim.start()
                        }
                    }

                })

        }
    }

    private val appPrefs get() = getSharedPreferences("info_board", Context.MODE_PRIVATE)


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
        if (fragment is BackPressable) if (fragment.onBackPressed()) router.exit()
        else router.exit()
        if (supportFragmentManager.fragments.size <= 1) super.onBackPressed()
    }

    private val switcherOffset = 20f.dp()


}

interface ThemedActivity {

    val currentTheme: Int

    fun onChangeThemeClick(x: Int, y: Int, bitmap: Bitmap)

}