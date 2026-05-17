package com.tritiumgaming.database.customdifficulty

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [CustomDifficultyEntity::class], version = 2, exportSchema = false)
@TypeConverters(DifficultyTypeConverters::class)

abstract class CustomDifficultyDatabase : RoomDatabase() {
    abstract fun customDifficultyDao(): CustomDifficultyDao

    companion object {
        fun getCallback(scope: CoroutineScope): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Seeding is usually done via a repository or DAO after DB creation
                    // But here we can't easily get the DAO until the DB is built.
                }
            }
        }
    }
}
