package com.example.data.source.local

import androidx.room.*
import com.example.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("DELETE FROM `notes-table`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `notes-table` WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT * FROM `notes-table`")
    fun getAllNotes(): Flow<List<NoteEntity>>
}