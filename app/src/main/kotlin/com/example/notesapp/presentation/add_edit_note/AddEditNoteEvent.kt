package com.example.notesapp.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title: String) : AddEditNoteEvent()
    data class ChangedTitleFocus(val focus: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val content: String) : AddEditNoteEvent()
    data class ChangedContentFocus(val focus: FocusState) : AddEditNoteEvent()
    data class ChangedColor(val color: Int) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}
