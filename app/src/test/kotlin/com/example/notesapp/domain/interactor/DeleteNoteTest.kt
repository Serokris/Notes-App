package com.example.notesapp.domain.interactor

import com.example.domain.interactor.NoteInteractor
import com.example.domain.model.Note
import com.example.notesapp.data.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private val fakeNoteRepository = FakeNoteRepository()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)
    private val deletingNote = Note(
        0, "Title", "Content", 0, 0
    )

    @Before
    fun setUp() = runBlocking {
        noteInteractor.addNote(deletingNote)
    }

    @Test
    fun deletingNoteTest() = runBlocking {
        noteInteractor.deleteNote(deletingNote)

        assert(noteInteractor.getNoteById(deletingNote.id) == null)
    }
}