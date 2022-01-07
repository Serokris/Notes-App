package com.example.domain.interactor

import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteAllNotesTest {

    private val fakeNoteRepository = mock<NoteRepository>()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)

    @Test
    fun deleteAllNotes() = runBlocking {
        noteInteractor.deleteAllNotes()
        verify(fakeNoteRepository).deleteAll()
    }
}