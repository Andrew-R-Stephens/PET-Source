package com.tritiumgaming.database.customdifficulty

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomDifficultyDao {
    @Query("SELECT * FROM custom_difficulties")
    fun getAll(): Flow<List<CustomDifficultyEntity>>

    @Query("SELECT * FROM custom_difficulties WHERE id = :id")
    suspend fun getById(id: Int): CustomDifficultyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(difficulty: CustomDifficultyEntity)

    @Update
    suspend fun update(difficulty: CustomDifficultyEntity)

    @Delete
    suspend fun delete(difficulty: CustomDifficultyEntity)
}
