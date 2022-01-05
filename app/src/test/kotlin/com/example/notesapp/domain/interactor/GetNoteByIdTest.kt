package com.example.notesapp.domain.interactor

import com.example.domain.interactor.NoteInteractor
import com.example.domain.model.Note
import com.example.notesapp.data.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteByIdTest {

    private val fakeNoteRepository = FakeNoteRepository()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)
    private val note = Note(0, "Title", "Content", 0, 0)

    @Before
    fun setUp() = runBlocking {
        noteInteractor.addNote(note)
    }

    @Test
    fun `Get note by id, correct case`() = runBlocking {
        assert(noteInteractor.getNoteById(note.id) != null)
    }

    @Test
    fun `Get note by id, null pointer to note`() = runBlocking {
        noteInteractor.deleteNote(note)

        assert(noteInteractor.getNoteById(note.id) == null)
    }
}