package com.example.notesapp.di

import com.example.data.repository.NoteRepositoryImpl
import com.example.data.source.local.NoteDao
import com.example.domain.interactor.NoteInteractor
import com.example.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository = NoteRepositoryImpl(noteDao)

    @Provides
    @Singleton
    fun provideNoteInteractor(
        noteRepository: NoteRepository
    ): NoteInteractor = NoteInteractor(noteRepository)
}