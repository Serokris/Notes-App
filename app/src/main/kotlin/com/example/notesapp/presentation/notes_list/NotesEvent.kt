package com.example.notesapp.presentation.notes_list

import com.example.domain.common.NoteOrder
import com.example.notesapp.models.NoteWithColors

sealed class NotesEvent {
    data class Order(val order: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: NoteWithColors) : NotesEvent()
    object DeleteAllNotes : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}