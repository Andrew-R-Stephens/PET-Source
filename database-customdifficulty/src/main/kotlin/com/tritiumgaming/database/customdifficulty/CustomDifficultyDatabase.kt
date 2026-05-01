package com.tritiumgaming.database.customdifficulty

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CustomDifficultyEntity::class], version = 1, exportSchema = false)
@TypeConverters(DifficultyTypeConverters::class)
abstract class CustomDifficultyDatabase : RoomDatabase() {
    abstract fun customDifficultyDao(): CustomDifficultyDao
}
