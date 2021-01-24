package ru.snowmaze.infoboard.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.snowmaze.domain.note.Note
import ru.snowmaze.infoboard.databinding.LayoutNoteBinding


class NotesAdapter(var context: Context, private val callback: NotesViewHolder.NotesAdapterCallback) :
    RecyclerView.Adapter<NotesViewHolder>(), NotesViewHolder.NotesAdapterCallback {

    private var mNotes = mutableListOf<Note>()
    var notes: List<Note>
        set(value) {
            mNotes = value.toMutableList()
            notifyDataSetChanged()
        }
        get() = mNotes
    var selected = mutableListOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutNoteBinding.inflate(LayoutInflater.from(context), parent, false), this
    )


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, selected.contains(note))
    }

    override fun getItemId(position: Int) = mNotes[position].id

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

    private fun addSelected(note: Note, notesIndex: Int) {
        selected.add(note)
        notifyItemChanged(notesIndex)
    }

    private fun removeSelected(index: Int, notesIndex: Int) {
        selected.removeAt(index)
        notifyItemChanged(notesIndex)
    }

    override fun onNoteClick(note: Note) {
        if(selected.isNotEmpty()) {
            val notesIndex = notes.indexOf(note)
            val index = selected.indexOf(note)
            if(index == -1) addSelected(note, notesIndex)
            else removeSelected(index, notesIndex)
        }
        callback.onNoteClick(note)
    }

    override fun onNoteLongClick(note: Note) {
        val index = selected.indexOf(note)
        val notesIndex = notes.indexOf(note)
        if (index == -1) addSelected(note, notesIndex)
        else removeSelected(index, notesIndex)
        callback.onNoteLongClick(note)
    }

}