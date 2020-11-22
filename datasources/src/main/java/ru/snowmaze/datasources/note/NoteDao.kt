package ru.snowmaze.datasources.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: NoteEntity): Long

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("SELECT * FROM NoteEntity ORDER BY id DESC LIMIT :pageSize OFFSET :offSet")
    suspend fun getNotes(offSet: Int, pageSize: Int): List<NoteEntity>

}