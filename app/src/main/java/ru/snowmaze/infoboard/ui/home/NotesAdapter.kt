package ru.snowmaze.infoboard.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.MaterialShapeDrawable
import ru.snowmaze.domain.note.Note
import ru.snowmaze.infoboard.databinding.LayoutNoteBinding
import ru.snowmaze.infoboard.utils.setTint
import ru.snowmaze.themeslib.Theme


class NotesAdapter(context: Context) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var mNotes = mutableListOf<Note>()
    var notes: List<Note>
        set(value) {
            mNotes = value.toMutableList()
            notifyDataSetChanged()
        }
        get() = mNotes
    private val layoutInflater = LayoutInflater.from(context)
    private lateinit var theme: Theme

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutNoteBinding.inflate(layoutInflater, parent, false)
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position], theme)
    }

    override fun getItemId(position: Int) = mNotes[position].id

    fun onThemeChanged(theme: Theme) {
        this.theme = theme
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size

    fun addNote(note: Note) {
        mNotes.add(0, note)
        notifyItemInserted(0)
    }

    fun removeNote(note: Note) {
        val index = mNotes.indexOf(note)
        notifyItemInserted(index)
        mNotes.removeAt(index)
    }

    class NotesViewHolder(private val binding: LayoutNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, theme: Theme) {
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
                root.setCardBackgroundColor(theme.mainColorLighter)
                noteDescription.setTextColor(theme.textColor)
                noteDescription.text = note.text
                noteDescription.setTint(theme.tintColor)
            }
        }

    }

}