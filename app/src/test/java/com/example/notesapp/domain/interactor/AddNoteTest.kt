package com.example.notesapp.domain.interactor

import com.example.domain.common.InvalidNoteException
import com.example.domain.interactor.NoteInteractor
import com.example.domain.model.Note
import com.example.notesapp.data.repository.FakeNoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddNoteTest {

    private val fakeNoteRepository = FakeNoteRepository()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)

    @Test
    fun `Insert note, correct case`() = runBlocking {
        noteInteractor.addNote(
            Note(0, "Title", "Content", 0, 0)
        )
        assert(noteInteractor.getNoteById(0) != null)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note, expecting exception because title is empty`() = runBlocking {
        noteInteractor.addNote(
            Note(0, "", "Content", 0, 0)
        )
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note, expecting exception because content is empty`() = runBlocking {
        noteInteractor.addNote(
            Note(0, "Title", "", 0, 0)
        )
    }
}