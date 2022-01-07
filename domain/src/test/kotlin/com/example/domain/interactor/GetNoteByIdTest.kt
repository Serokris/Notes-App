package com.example.domain.interactor

import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetNoteByIdTest {

    private val fakeNoteRepository = mock<NoteRepository>()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)

    @Test
    fun `Get note by id, correct case`() = runBlocking {
        Mockito.`when`(fakeNoteRepository.getNoteById(0))
            .thenReturn(Note(0, "Title", "Content", 0, 0))
        assert(noteInteractor.getNoteById(0) != null)
    }

    @Test
    fun `Get note by id, null pointer to note`() = runBlocking {
        Mockito.`when`(fakeNoteRepository.getNoteById(0)).thenReturn(null)
        assert(noteInteractor.getNoteById(0) == null)
    }
}