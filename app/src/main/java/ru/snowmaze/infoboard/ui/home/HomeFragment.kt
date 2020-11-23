package ru.snowmaze.infoboard.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel
import ru.snowmaze.domain.note.Note
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.databinding.FragmentHomeBinding
import ru.snowmaze.infoboard.themes.DarkTheme
import ru.snowmaze.infoboard.themes.WhiteTheme
import ru.snowmaze.infoboard.ui.BaseFragment
import ru.snowmaze.infoboard.utils.dp
import ru.snowmaze.infoboard.utils.setTint
import ru.snowmaze.infoboard.utils.showText
import ru.snowmaze.infoboard.utils.smoothScrollToPosition
import ru.snowmaze.themeslib.Theme
import kotlin.math.hypot


class HomeFragment : BaseFragment(R.layout.fragment_home), NotesViewHolder.NotesAdapterCallback {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: NotesAdapter by lazy { NotesAdapter(requireContext(), this) }
    private var themeChanging = false

    override fun initView(view: View, theme: Theme, savedInstanceState: Bundle?) {
        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
                view.onApplyWindowInsets(insets.toWindowInsets())
                toolbar.updatePadding(top = (insets.systemWindowInsetTop))
                insets.consumeSystemWindowInsets()
            }
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
            setHasOptionsMenu(true)
            savedInstanceState?.let {
                quickNote.setText(savedInstanceState.getString("task_text"))
            }
            doneButton.setOnClickListener {
                val text = quickNote.text.toString()
                if (text.isBlank()) {
                    showText(R.string.enter_note)
                    return@setOnClickListener
                }
                lifecycleScope.launch {
                    viewModel.saveNote(Note(text))
                        .observe(viewLifecycleOwner) { result ->
                            result.onSuccess {
                                adapter.addNote(it)
                                notes.layoutManager!!.smoothScrollToPosition(
                                    requireContext(),
                                    0,
                                    160F
                                )
                            }
                        }
//                  router.navigateTo(noteScreen(), false)
                }
            }
            setTheme(theme)
            adapter.setHasStableIds(true)
            notes.adapter = adapter
            viewModel.notes.observe(viewLifecycleOwner) {
                adapter.notes = it
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.theme_switcher)
        item.icon = ContextCompat.getDrawable(
            requireContext(), when (theme) {
                is WhiteTheme -> R.drawable.baseline_brightness_2_24
                else -> R.drawable.outline_brightness_2_24
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!themeChanging) {
            if (item.itemId == R.id.theme_switcher) {
                themeChanging = true
                val koin = getKoin()
                themeHolder.themeLiveData.value =
                    if (theme is WhiteTheme) koin.get<DarkTheme>() else koin.get<WhiteTheme>()
                (requireActivity() as AppCompatActivity).invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setTheme(theme: Theme) {
        with(binding) {
            with(theme) {
                container.setBackgroundColor(mainColor)
                toolbar.setBackgroundColor(toolbarColor)
                toolbar.setTitleTextColor(toolbarTextColor)
                quickNote.setTextColor(textColor)
                quickNote.setHintTextColor(additionalTextColor)
                doneButton.setTint(tintColor)
                adapter.onThemeChanged(theme)
            }
        }
    }

    override fun onThemeChanged(theme: Theme) {
        with(binding) {
            imageView.setImageBitmap(
                container.drawToBitmap(Bitmap.Config.ARGB_8888)
            )
            imageView.isVisible = true

            val finalRadius =
                hypot(container.measuredWidth.toFloat(), container.measuredHeight.toFloat())

            setTheme(theme)
            val switcherLocation = IntArray(2)
            binding.toolbar.findViewById<View>(R.id.theme_switcher).getLocationInWindow(switcherLocation)

            val anim = ViewAnimationUtils.createCircularReveal(
                container,
                switcherLocation[0] + switcherOffset,
                switcherLocation[1] + switcherOffset,
                0f,
                finalRadius
            )
            anim.duration = 500L
            anim.doOnEnd {
                themeChanging = false
                imageView.setImageDrawable(null)
                imageView.visibility = View.GONE
            }
            anim.start()

        }
    }

    override fun onNoteClick(note: Note) {

    }

    override fun onNoteLongClick(note: Note) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("task_text", binding.quickNote.text.toString())
    }

    private val switcherOffset = 20.dp()

}