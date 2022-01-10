package com.example.domain.interactor

import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.domain.model.Note
import com.example.domain.repository.NoteRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAllNotesTest {

    private val fakeNoteRepository = mock<NoteRepository>()
    private val noteInteractor = NoteInteractor(fakeNoteRepository)
    private val notesForSorting = mutableListOf<Note>()

    @Before
    fun setUp() {
        ('a'..'z').forEachIndexed { index, char ->
            notesForSorting.add(
                Note(
                    id = index,
                    title = char.toString(),
                    content = char.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesForSorting.shuffle()
    }

    @Test
    fun `Should be return notes, ordered by title ascending`() = runBlocking {
        Mockito.`when`(fakeNoteRepository.getAllNotes()).thenReturn(flow { emit(notesForSorting) })

        val orderedNotes =
            noteInteractor.getAllNotes((NoteOrder.Title(OrderType.Ascending))).single()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].title < orderedNotes[i + 1].title)
        }
    }

    @Test
    fun `Should be return notes, ordered by title descending`() = runBlocking {
        Mockito.`when`(fakeNoteRepository.getAllNotes()).thenReturn(flow { emit(notesForSorting) })

        val orderedNotes =
            noteInteractor.getAllNotes((NoteOrder.Title(OrderType.Descending))).single()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].title > orderedNotes[i + 1].title)
        }
    }

    @Test
    fun `Should be return notes, ordered by date ascending`() = runBlocking {
        Mockito.`when`(fakeNoteRepository.getAllNotes()).thenReturn(flow { emit(notesForSorting) })

        val orderedNotes =
            noteInteractor.getAllNotes((NoteOrder.Date(OrderType.Ascending))).single()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].timestamp < orderedNotes[i + 1].timestamp)
        }
    }

    @Test
    fun `Should be return notes, ordered by date descending`() = runBlocking {
        Mockito.`when`(fakeNoteRepository.getAllNotes()).thenReturn(flow { emit(notesForSorting) })

        val orderedNotes =
            noteInteractor.getAllNotes((NoteOrder.Date(OrderType.Descending))).single()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].timestamp > orderedNotes[i + 1].timestamp)
        }
    }

    @Test
    fun `Should be return notes, ordered by color ascending`() = runBlocking {
        Mockito.`when`(noteInteractor.getAllNotes()).thenReturn(flow { emit(notesForSorting) })

        val orderedNotes =
            noteInteractor.getAllNotes((NoteOrder.Color(OrderType.Ascending))).single()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].color < orderedNotes[i + 1].color)
        }
    }

    @Test
    fun `Should be return notes, ordered by color descending`() = runBlocking {
        Mockito.`when`(noteInteractor.getAllNotes()).thenReturn(flow { emit(notesForSorting) })

        val orderedNotes =
            noteInteractor.getAllNotes((NoteOrder.Color(OrderType.Descending))).single()

        for (i in 0..orderedNotes.size - 2) {
            assert(orderedNotes[i].color > orderedNotes[i + 1].color)
        }
    }
}