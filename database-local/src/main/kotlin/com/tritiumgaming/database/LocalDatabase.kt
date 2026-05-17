package com.tritiumgaming.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tritiumgaming.data.customdifficulty.source.local.CustomDifficultyDao
import com.tritiumgaming.data.customdifficulty.source.local.CustomDifficultyEntity
import com.tritiumgaming.data.customdifficulty.source.local.DifficultyTypeConverters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [CustomDifficultyEntity::class], version = 2, exportSchema = false)
@TypeConverters(DifficultyTypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun customDifficultyDao(): CustomDifficultyDao

    companion object {
        fun getCallback(scope: CoroutineScope): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        }
    }
}
