package ru.snowmaze.data.di

import org.koin.dsl.module
import ru.snowmaze.data.note.NoteRepositoryImpl
import ru.snowmaze.domain.note.NoteRepository

val noteModule = module {
    single<NoteRepository> {
        NoteRepositoryImpl(get())
    }
}