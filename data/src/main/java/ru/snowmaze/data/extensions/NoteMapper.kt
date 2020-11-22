package ru.snowmaze.data.extensions

import ru.snowmaze.data.note.NoteDataEntity
import ru.snowmaze.domain.note.Note

fun Note.toDataModel() = NoteDataEntity(id, text)

fun NoteDataEntity.toDomainModel() = Note(id, text)