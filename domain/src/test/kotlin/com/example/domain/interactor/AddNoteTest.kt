package com.example.domain.interactor

import com.example.domain.common.InvalidNoteException
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddNoteTest {

    private val fakeNoteRepository = mock<NoteRepository>()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)

    @Test
    fun `Insert note, should be called method 'insert' at the repository`() = runBlocking {
        val note = Note(0, "Title", "Content", 0, 0)
        noteInteractor.addNote(note)
        verify(fakeNoteRepository).insert(note)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note, expecting exception because title is empty`() = runBlocking {
        val note = Note(0, "", "Content", 0, 0)
        noteInteractor.addNote(note)
    }

    @Test(expected = InvalidNoteException::class)
    fun `Insert note, expecting exception because content is empty`() = runBlocking {
        val note = Note(0, "Title", "", 0, 0)
        noteInteractor.addNote(note)
    }
}