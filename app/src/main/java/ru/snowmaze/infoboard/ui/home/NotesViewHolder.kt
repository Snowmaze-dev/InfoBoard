package ru.snowmaze.infoboard.ui.home

import androidx.recyclerview.widget.RecyclerView
import ru.snowmaze.domain.note.Note
import ru.snowmaze.infoboard.databinding.LayoutNoteBinding
import ru.snowmaze.infoboard.utils.setTint
import ru.snowmaze.themeslib.Theme

class NotesViewHolder(
    private val binding: LayoutNoteBinding,
    private val callback: NotesAdapterCallback
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, selected: Boolean, theme: Theme) {
        with(binding) {
//                root.background =
//                    MaterialShapeDrawable.createWithElevationOverlay(root.context, 20F).apply {
//                        fillColor = ColorStateList(
//                            arrayOf(intArrayOf()),
//                            intArrayOf(theme.mainColorLighter)
//                        )
//                        setBounds(100, 100, 100, 100)
//                        setCornerSize(50F)
//                        shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
//                    }
            root.setOnClickListener {
                callback.onNoteClick(note)
            }
            root.setOnLongClickListener {
                callback.onNoteLongClick(note)
                true
            }
            root.setCardBackgroundColor(if(selected) theme.selectedColor else theme.mainColorLighter)
            noteDescription.setTextColor(theme.textColor)
            noteDescription.text = note.text
            noteDescription.setTint(theme.tintColor)
        }
    }

    interface NotesAdapterCallback {

        fun onNoteClick(note: Note)

        fun onNoteLongClick(note: Note)

    }

}