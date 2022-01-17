package com.example.notesapp.models

data class SavedNoteOrder(
    val noteOrderType: SavedNoteOrderType,
    val orderType: SavedOrderType
)

enum class SavedNoteOrderType {
    Title,
    Date,
    Color,
}

enum class SavedOrderType {
    Ascending,
    Descending
}