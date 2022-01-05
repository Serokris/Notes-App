package com.example.notesapp.data.repository

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {

    private val notesList = mutableListOf<Note>()

    override suspend fun insert(note: Note) {
        notesList.add(note)
    }

    override suspend fun delete(note: Note) {
        notesList.remove(note)
    }

    override suspend fun deleteAll() {
        notesList.removeAll(notesList)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notesList.find { note -> note.id == id }
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return flow { emit(notesList) }
    }
}