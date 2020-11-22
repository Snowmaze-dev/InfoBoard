package ru.snowmaze.domain.note

import kotlinx.coroutines.flow.Flow
import ru.snowmaze.domain.Result

interface NoteRepository {

    fun getNotes(page: Int, pageSize: Int = 15): Flow<Result<List<Note>>>

    suspend fun saveNote(note: Note): Result<Note>

    suspend fun editNote(note: Note): Result<Unit>

    suspend fun deleteNote(note: Note): Result<Unit>

}