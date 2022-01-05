package com.example.notesapp.models

import com.example.notesapp.presentation.theme.*

data class NoteWithColors(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, LightBlue, Violet, LightYellow)
    }
}