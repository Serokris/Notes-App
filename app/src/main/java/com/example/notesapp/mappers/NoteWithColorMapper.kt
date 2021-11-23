package com.example.notesapp.mappers

import com.example.domain.model.Note
import com.example.notesapp.models.NoteWithColors

fun Note.toNoteWithColor(): NoteWithColors {
    return NoteWithColors(id, title, content, timestamp, color)
}

fun NoteWithColors.toNote(): Note {
    return Note(id, title, content, timestamp, color)
}