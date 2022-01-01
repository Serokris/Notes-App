package com.example.notesapp.domain.interactor

import com.example.notesapp.data.repository.FakeNoteRepository
import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.domain.interactor.NoteInteractor
import com.example.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllNotesTest {

    private val fakeNoteRepository = FakeNoteRepository()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)

    @Before
    fun setUp() {
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, char ->
            notesToInsert.add(
                Note(
                    id = index,
                    title = char.toString(),
                    content = char.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach { note ->
                noteInteractor.addNote(note)
            }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val orderedNotes = noteInteractor.getAllNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].title < orderedNotes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val orderedNotes = noteInteractor.getAllNotes(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].title > orderedNotes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val orderedNotes = noteInteractor.getAllNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].timestamp < orderedNotes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val orderedNotes = noteInteractor.getAllNotes(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].timestamp > orderedNotes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val orderedNotes = noteInteractor.getAllNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].color < orderedNotes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val orderedNotes = noteInteractor.getAllNotes(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].color > orderedNotes[i + 1].color)
        }
    }
}