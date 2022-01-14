package com.example.notesapp.presentation.notes_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.domain.interactor.NoteInteractor
import com.example.notesapp.mappers.toNote
import com.example.notesapp.mappers.toNoteWithColor
import com.example.notesapp.models.NoteWithColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val interactor: NoteInteractor
) : ViewModel() {

    private val _screenState = mutableStateOf(NotesState())
    val screenState: State<NotesState> = _screenState

    private var _recentlyDeletedNote: NoteWithColors? = null
    val recentlyDeletedNote: NoteWithColors? get() = _recentlyDeletedNote

    private var getAllNotesJob: Job? = null

    init {
        getAllNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (screenState.value.noteOrder::class == event.order::class &&
                    screenState.value.noteOrder.orderType == event.order.orderType
                ) return
                getAllNotes(event.order)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    interactor.deleteNote(event.note.toNote())
                    _recentlyDeletedNote = event.note
                }
            }
            NotesEvent.DeleteAllNotes -> {
                viewModelScope.launch {
                    interactor.deleteAllNotes()
                }
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    interactor.addNote(recentlyDeletedNote?.toNote() ?: return@launch)
                    _recentlyDeletedNote = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
                _screenState.value = screenState.value.copy(
                    isOrderSectionVisible = !screenState.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getAllNotes(noteOrder: NoteOrder) {
        getAllNotesJob?.cancel()
        getAllNotesJob = interactor.getAllNotes(noteOrder).onEach { notesList ->
            val notesWithColors = notesList.map { note -> note.toNoteWithColor() }
            _screenState.value = screenState.value.copy(
                notesList = notesWithColors,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}