package com.tritiumgaming.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tritiumgaming.data.customdifficulty.source.local.CustomDifficultyDao
import com.tritiumgaming.data.customdifficulty.source.local.CustomDifficultyEntity
import com.tritiumgaming.data.customdifficulty.source.local.DifficultyTypeConverters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [CustomDifficultyEntity::class], version = 1, exportSchema = false)
//@Database(entities = [CustomDifficultyEntity::class], version = 2, exportSchema = false)
@TypeConverters(DifficultyTypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun customDifficultyDao(): CustomDifficultyDao

    companion object {
        /**
         * Generic migration strategy from version 1 to 2.
         *
         * To complete an upgrade:
         * 1. Update the [CustomDifficultyEntity] with new fields if necessary.
         * 2. Add the corresponding SQL commands in the [migrate] method below.
         * 3. If the change is complex (e.g., renaming columns), consider using a temporary table.
         */
        /*val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Example: Adding a new column
                // db.execSQL("ALTER TABLE CustomDifficulty ADD COLUMN lastUpdated INTEGER NOT NULL DEFAULT 0")

                // Example: Creating a new table
                // db.execSQL("CREATE TABLE IF NOT EXISTS `NewTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT)")
            }
        }*/

        fun getCallback(scope: CoroutineScope): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        }
    }
}
