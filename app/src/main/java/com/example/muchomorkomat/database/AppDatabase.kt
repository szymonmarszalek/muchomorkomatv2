package com.example.muchomorkomat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MushroomEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mushroomDao(): MushroomDao
    companion object {
        var db: AppDatabase? = null

        fun create(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "mushroom_db")

                    .build()
            }
            return db as AppDatabase
        }
    }
}
