package ru.snowmaze.infoboard.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import ru.snowmaze.domain.note.Note
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.databinding.FragmentHomeBinding
import ru.snowmaze.infoboard.ui.BaseFragment
import ru.snowmaze.infoboard.ui.ThemedActivity
import ru.snowmaze.infoboard.utils.showText
import ru.snowmaze.infoboard.utils.smoothScrollToPosition


class HomeFragment : BaseFragment(R.layout.fragment_home), NotesViewHolder.NotesAdapterCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: NotesAdapter by lazy { NotesAdapter(requireContext(), this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.context = requireContext()
        _binding = FragmentHomeBinding.bind(view)
//        val adapter = NotesAdapter(requireContext(), this)
        with(binding) {
            edgeToEdge {
                appbar.fit { Edge.Left + Edge.Top + Edge.Right }
                quickNoteContainer.fit { Edge.Bottom }
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
            if (!adapter.hasStableIds()) adapter.setHasStableIds(true)
            notes.adapter = adapter
            viewModel.notes.observe(viewLifecycleOwner) {
                adapter.notes = it
            }
        }
    }

    override fun onDestroyView() {
        binding.notes.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.theme_switcher)
        item.icon = ContextCompat.getDrawable(
            requireContext(), when (theme) {
                R.style.Light -> R.drawable.baseline_brightness_2_24
                else -> R.drawable.outline_brightness_2_24
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.theme_switcher) {
            val (x, y) = IntArray(2).apply {
                val switcher = binding.toolbar.findViewById<View>(R.id.theme_switcher)
                switcher.getLocationInWindow(this)
                set(0, get(0) + switcher.measuredWidth / 2)
                set(1, get(1) + switcher.measuredHeight / 2)
            }
            (requireActivity() as ThemedActivity).onChangeThemeClick(
                x, y, binding.root.drawToBitmap(
                    Bitmap.Config.ARGB_8888
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNoteClick(note: Note) {

    }

    override fun onNoteLongClick(note: Note) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("task_text", binding.quickNote.text.toString())
    }

}