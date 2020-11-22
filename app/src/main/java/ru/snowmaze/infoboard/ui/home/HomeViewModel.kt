package ru.snowmaze.infoboard.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import ru.snowmaze.domain.note.Note
import ru.snowmaze.domain.note.NoteRepository
import kotlin.random.Random

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private var page = 0

    fun getNextNotes() = noteRepository.getNotes(page).also {
        page++
    }.asLiveData()

    fun saveNote(note: Note) = liveData {
        emit(noteRepository.saveNote(note))
    }

}