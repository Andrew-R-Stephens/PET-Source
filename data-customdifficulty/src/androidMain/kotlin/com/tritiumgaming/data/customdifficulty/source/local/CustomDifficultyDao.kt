package com.tritiumgaming.data.customdifficulty.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
actual interface CustomDifficultyDao {
    @Query("SELECT * FROM CustomDifficulty")
    actual fun getAll(): Flow<List<CustomDifficultyEntity>>

    @Query("SELECT * FROM CustomDifficulty WHERE id = :id")
    actual suspend fun getById(id: Int): CustomDifficultyEntity?

    @Query("SELECT COUNT(*) FROM CustomDifficulty")
    actual suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    actual suspend fun insert(difficulty: CustomDifficultyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    actual suspend fun insertAll(difficulties: List<CustomDifficultyEntity>)

    @Transaction
    actual suspend fun insertWithLimit(difficulty: CustomDifficultyEntity): Boolean {
        return if (difficulty.id == 0) {
            if (getCount() < MAX_ITEMS) {
                insert(difficulty)
                true
            } else {
                false
            }
        } else {
            insert(difficulty)
            true
        }
    }

    @Update
    actual suspend fun update(difficulty: CustomDifficultyEntity)

    @Delete
    actual suspend fun delete(difficulty: CustomDifficultyEntity)

    companion object {
        const val MAX_ITEMS = 3
    }
}
