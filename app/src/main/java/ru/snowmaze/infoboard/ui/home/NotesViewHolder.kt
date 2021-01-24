package ru.snowmaze.infoboard.ui.home

import androidx.recyclerview.widget.RecyclerView
import ru.snowmaze.domain.note.Note
import ru.snowmaze.infoboard.R
import ru.snowmaze.infoboard.databinding.LayoutNoteBinding
import ru.snowmaze.infoboard.utils.getColorFromAttr

class NotesViewHolder(
    private val binding: LayoutNoteBinding,
    private val callback: NotesAdapterCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, selected: Boolean) {
        with(binding) {
            root.setOnClickListener {
                callback.onNoteClick(note)
            }
            root.setOnLongClickListener {
                callback.onNoteLongClick(note)
                true
            }
            root.setCardBackgroundColor(
                root.context.getColorFromAttr(
                    if (selected) R.attr.colorBackgroundSelected
                    else R.attr.colorBackgroundLighter
                )
            )
            noteDescription.text = note.text
        }
    }

    interface NotesAdapterCallback {

        fun onNoteClick(note: Note)

        fun onNoteLongClick(note: Note)

    }

}