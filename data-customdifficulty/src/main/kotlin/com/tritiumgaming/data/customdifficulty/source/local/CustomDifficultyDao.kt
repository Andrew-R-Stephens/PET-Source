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
interface CustomDifficultyDao {
    @Query("SELECT * FROM custom_difficulties")
    fun getAll(): Flow<List<CustomDifficultyEntity>>

    @Query("SELECT * FROM custom_difficulties WHERE id = :id")
    suspend fun getById(id: Int): CustomDifficultyEntity?

    @Query("SELECT COUNT(*) FROM custom_difficulties")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(difficulty: CustomDifficultyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(difficulties: List<CustomDifficultyEntity>)

    @Transaction
    suspend fun insertWithLimit(difficulty: CustomDifficultyEntity): Boolean {
        return if (difficulty.id == 0) {
            if (getCount() < 5) {
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
    suspend fun update(difficulty: CustomDifficultyEntity)

    @Delete
    suspend fun delete(difficulty: CustomDifficultyEntity)
}
