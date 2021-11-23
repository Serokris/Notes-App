package com.example.notesapp.presentation.add_edit_note

sealed class AddEditNoteScreenEvents {
    data class ShowSnackbar(val message: String) : AddEditNoteScreenEvents()
    object SaveNote : AddEditNoteScreenEvents()
}
