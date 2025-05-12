package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.dto.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.dto.room.Palette
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.dto.room.PaletteDao

@Database(entities = [Palette::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paletteDao(): PaletteDao
}