package ru.snowmaze.datasources.note

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.snowmaze.data.note.NoteDataEntity
import ru.snowmaze.data.note.sources.NoteSource

class PersistenceNoteSource(private val noteDao: NoteDao) : NoteSource {

    override suspend fun getNotes(page: Int, pageSize: Int) = withContext(Dispatchers.IO) {
        noteDao.getNotes(page * pageSize, pageSize).map(NoteEntity::toDataEntity)
    }

    override suspend fun insertNote(note: NoteDataEntity) = withContext(Dispatchers.IO) {
        noteDao.insert(note.fromDataEntity())
    }

}