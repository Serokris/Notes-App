package com.example.notesapp.domain.interactor

import com.example.domain.interactor.NoteInteractor
import com.example.domain.model.Note
import com.example.notesapp.data.repository.FakeNoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteAllNotesTest {

    private val fakeNoteRepository = FakeNoteRepository()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)

    @Before
    fun setUp() = runBlocking {
        ('a'..'z').forEachIndexed { index, char ->
            noteInteractor.addNote(
                Note(
                    id = index,
                    title = char.toString(),
                    content = char.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
    }

    @Test
    fun deleteAllNotes() = runBlocking {
        noteInteractor.deleteAllNotes()

        assert(noteInteractor.getAllNotes().first().isEmpty())
    }
}