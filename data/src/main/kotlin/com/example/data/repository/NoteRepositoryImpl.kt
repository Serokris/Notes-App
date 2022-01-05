package com.example.data.repository

import com.example.data.mapper.toNote
import com.example.data.mapper.toNoteEntity
import com.example.data.source.local.NoteDao
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun insert(note: Note) {
        noteDao.insert(note.toNoteEntity())
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note.toNoteEntity())
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)?.toNote()
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
            .map { noteList ->
                noteList.map { noteEntity ->
                    noteEntity.toNote()
                }
            }
    }
}