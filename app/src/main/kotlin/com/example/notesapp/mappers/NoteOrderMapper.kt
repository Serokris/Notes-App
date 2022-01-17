package com.example.notesapp.mappers

import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.notesapp.models.SavedNoteOrder
import com.example.notesapp.models.SavedNoteOrderType
import com.example.notesapp.models.SavedOrderType

fun NoteOrder.toSavedNoteOrder(): SavedNoteOrder {
    return when (orderType) {
        OrderType.Ascending -> {
            when (this) {
                is NoteOrder.Title -> {
                    SavedNoteOrder(SavedNoteOrderType.Title, SavedOrderType.Ascending)
                }
                is NoteOrder.Date -> {
                    SavedNoteOrder(SavedNoteOrderType.Date, SavedOrderType.Ascending)
                }
                is NoteOrder.Color -> {
                    SavedNoteOrder(SavedNoteOrderType.Color, SavedOrderType.Ascending)
                }
            }
        }
        OrderType.Descending -> {
            when (this) {
                is NoteOrder.Title -> {
                    SavedNoteOrder(SavedNoteOrderType.Title, SavedOrderType.Descending)
                }
                is NoteOrder.Date -> {
                    SavedNoteOrder(SavedNoteOrderType.Date, SavedOrderType.Descending)
                }
                is NoteOrder.Color -> {
                    SavedNoteOrder(SavedNoteOrderType.Color, SavedOrderType.Descending)
                }
            }
        }
    }
}

fun SavedNoteOrder.toNoteOrder(): NoteOrder {
    return when (orderType) {
        SavedOrderType.Ascending -> {
            when (noteOrderType) {
                SavedNoteOrderType.Title -> {
                    NoteOrder.Title(OrderType.Ascending)
                }
                SavedNoteOrderType.Date -> {
                    NoteOrder.Date(OrderType.Ascending)
                }
                SavedNoteOrderType.Color -> {
                    NoteOrder.Color(OrderType.Ascending)
                }
            }
        }
        SavedOrderType.Descending -> {
            when (noteOrderType) {
                SavedNoteOrderType.Title -> {
                    NoteOrder.Title(OrderType.Descending)
                }
                SavedNoteOrderType.Date -> {
                    NoteOrder.Date(OrderType.Descending)
                }
                SavedNoteOrderType.Color -> {
                    NoteOrder.Color(OrderType.Descending)
                }
            }
        }
    }
}