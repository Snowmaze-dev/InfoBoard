package ru.snowmaze.data.note

import kotlinx.coroutines.flow.flow
import ru.snowmaze.data.extensions.toDataModel
import ru.snowmaze.data.extensions.toDomainModel
import ru.snowmaze.data.note.sources.NoteSource
import ru.snowmaze.domain.Result
import ru.snowmaze.domain.note.Note
import ru.snowmaze.domain.note.NoteRepository

class NoteRepositoryImpl(private val noteSource: NoteSource) : NoteRepository {

    override fun getNotes(page: Int, pageSize: Int) = flow {
        emit(Result.runSuspending { (noteSource.getNotes(page, pageSize)).map(NoteDataEntity::toDomainModel) })
    }

    override suspend fun saveNote(note: Note): Result<Note> = Result.runSuspending {
        Note(noteSource.insertNote(note.toDataModel()), note.text)
    }

    override suspend fun editNote(note: Note): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note): Result<Unit> {
        TODO("Not yet implemented")
    }

}