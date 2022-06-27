package com.example.muchomorkomat.database

import androidx.room.*

@Dao
interface MushroomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mushroomEntity: MushroomEntity): Long

    @Query("SELECT * FROM MushroomEntity")
    suspend fun getAll(): List<MushroomEntity>

    @Transaction
    @Query("SELECT * FROM MushroomEntity WHERE :mushroomId = id")
    suspend fun getByPlaylistId(mushroomId: Int): List<MushroomEntity>

    @Query("DELETE FROM MushroomEntity WHERE id = :mushroomId")
    suspend fun removeAllByPlaylistId(mushroomId: Long)

    @Query("DELETE FROM MushroomEntity")
    suspend fun deleteAll()
}