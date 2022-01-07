package com.example.domain.interactor

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteNoteTest {

    private val fakeNoteRepository = mock<NoteRepository>()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)
    private val deletingNote = Note(0, "Title", "Content", 0, 0)

    @Test
    fun deletingNoteTest() = runBlocking {
        noteInteractor.deleteNote(deletingNote)
        verify(fakeNoteRepository).delete(deletingNote)
    }
}