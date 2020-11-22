package ru.snowmaze.datasources.note

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class PersistenceHolder(context: Context) {

    private val database = Room.databaseBuilder(context, AppDatabase::class.java, "Notes").build()

    @Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun getNoteDao(): NoteDao

    }

    val noteDao = database.getNoteDao()



}