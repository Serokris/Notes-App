package com.example.data.mapper

import com.example.data.model.NoteEntity
import com.example.domain.model.Note

fun NoteEntity.toNote(): Note = Note(id, title, content, timestamp, color)

fun Note.toNoteEntity(): NoteEntity = NoteEntity(id, title, content, timestamp, color)