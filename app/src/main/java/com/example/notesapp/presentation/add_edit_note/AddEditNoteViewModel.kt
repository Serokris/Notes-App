package com.example.notesapp.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.InvalidNoteException
import com.example.domain.interactor.NoteInteractor
import com.example.domain.model.Note
import com.example.notesapp.models.NoteWithColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val interactor: NoteInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title"))
    val noteTitle: State<NoteTextFieldState> get() = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter content"))
    val noteContent: State<NoteTextFieldState> get() = _noteContent

    private val _currentNoteSelectedColor =
        mutableStateOf(NoteWithColors.noteColors.random().toArgb())
    val currentNoteSelectedColor: State<Int> get() = _currentNoteSelectedColor

    private val _screenEventFlow = MutableSharedFlow<AddEditNoteScreenEvents>()
    val screenEventFlow get() = _screenEventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.also { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    interactor.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = _noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _currentNoteSelectedColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.title,
                )
            }
            is AddEditNoteEvent.ChangedTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focus.isFocused && _noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.content,
                )
            }
            is AddEditNoteEvent.ChangedContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focus.isFocused && _noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangedColor -> {
                _currentNoteSelectedColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        interactor.addNote(
                            Note(
                                id = currentNoteId ?: 0,
                                title = _noteTitle.value.text,
                                content = _noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = _currentNoteSelectedColor.value,
                            )
                        )
                        _screenEventFlow.emit(AddEditNoteScreenEvents.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _screenEventFlow.emit(
                            AddEditNoteScreenEvents.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }
}