package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_database"
    }
}