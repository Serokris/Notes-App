package com.example.notesapp.presentation.notes_list

import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.notesapp.models.NoteWithColors

data class NotesState(
    val notesList: List<NoteWithColors> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)