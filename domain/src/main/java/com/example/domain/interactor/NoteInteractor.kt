package com.example.domain.interactor

import com.example.domain.common.InvalidNoteException
import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteInteractor @Inject constructor(
    private val repository: NoteRepository
) {

    suspend fun addNote(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty")
        }
        repository.insert(note)
    }

    suspend fun deleteNote(note: Note) = repository.delete(note)

    suspend fun deleteAllNotes() = repository.deleteAll()

    suspend fun getNoteById(id: Int): Note? = repository.getNoteById(id)

    fun getAllNotes(
        noteOrder: NoteOrder = NoteOrder.Date((OrderType.Descending))
    ): Flow<List<Note>> {
        return repository.getAllNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { note -> note.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { note -> note.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { note -> note.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { note -> note.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { note -> note.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { note -> note.color }
                    }
                }
            }
        }
    }
}