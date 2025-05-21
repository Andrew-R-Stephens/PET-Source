package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.db_unused.dto.room.Palette
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.db_unused.dto.room.PaletteDao

@Database(entities = [Palette::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paletteDao(): PaletteDao
}