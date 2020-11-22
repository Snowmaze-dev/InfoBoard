package ru.snowmaze.data.note.sources

import ru.snowmaze.data.note.NoteDataEntity

interface NoteSource {

    suspend fun getNotes(page: Int, pageSize: Int): List<NoteDataEntity>

    suspend fun insertNote(note: NoteDataEntity): Long

}