package ru.snowmaze.datasources.di

import org.koin.dsl.module
import ru.snowmaze.data.di.noteModule
import ru.snowmaze.data.note.sources.NoteSource
import ru.snowmaze.datasources.note.PersistenceHolder
import ru.snowmaze.datasources.note.PersistenceNoteSource

val dataSourcesModules = listOf(
    module {
        single {
            PersistenceHolder(get())
        }
        single<NoteSource> {
            PersistenceNoteSource(get<PersistenceHolder>().noteDao)
        }
    },
    noteModule
)