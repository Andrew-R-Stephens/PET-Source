package com.tritiumgaming.database.unknown.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tritiumgaming.database.unknown.dto.room.Palette
import com.tritiumgaming.database.unknown.dto.room.PaletteDao

@Database(entities = [Palette::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paletteDao(): PaletteDao
}