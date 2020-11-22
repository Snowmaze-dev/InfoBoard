package ru.snowmaze.datasources.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.snowmaze.data.note.NoteDataEntity

@Entity
data class NoteEntity(@PrimaryKey(autoGenerate = true) val id: Long, val text: String) {

    fun toDataEntity() = NoteDataEntity(id, text)

}

fun NoteDataEntity.fromDataEntity() = NoteEntity(id, text)