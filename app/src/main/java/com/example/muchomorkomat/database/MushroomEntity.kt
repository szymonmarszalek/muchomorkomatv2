package com.example.muchomorkomat.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MushroomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
) : DbEntity

