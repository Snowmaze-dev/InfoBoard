package ru.snowmaze.infoboard.ui.home

import androidx.lifecycle.*
import ru.snowmaze.domain.note.Note
import ru.snowmaze.domain.note.NoteRepository
import ru.snowmaze.infoboard.utils.notifyObserver

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val selectedNotesList = mutableListOf<Note>()
    private val mSelectedNotes = MutableLiveData<List<Note>>(selectedNotesList)
    val selectedNotes: LiveData<List<Note>> get() = mSelectedNotes
    private val notesList = mutableListOf<Note>()
    private val mNotes = MutableLiveData<List<Note>>(notesList)
    val notes: LiveData<List<Note>> get() = mNotes

    private var page = 0

    fun getNextNotes() = noteRepository.getNotes(page).also {
        page++
    }.asLiveData()

    init {
        getNextNotes().observeForever { result ->
            result.onSuccess {
                notesList.addAll(it)
                mNotes.notifyObserver()
            }
        }
    }

    fun saveNote(note: Note) = liveData {
        emit(noteRepository.saveNote(note))
    }

    fun removeNotes(notes: List<Note>) {
        // TODO
    }

    fun addSelected(note: Note) {
        selectedNotesList.add(note)
    }

    fun removeSelected(note: Note) {
        selectedNotesList.remove(note)
    }

}