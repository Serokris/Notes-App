package com.example.domain.repository

import com.example.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

    suspend fun deleteAll()

    suspend fun getNoteById(id: Int): Note?

    fun getAllNotes(): Flow<List<Note>>
}