package com.tritiumgaming.data.customdifficulty.source.local

import kotlinx.coroutines.flow.Flow

expect interface CustomDifficultyDao {
    fun getAll(): Flow<List<CustomDifficultyEntity>>
    suspend fun getById(id: Int): CustomDifficultyEntity?
    suspend fun getCount(): Int
    suspend fun insert(difficulty: CustomDifficultyEntity)
    suspend fun insertAll(difficulties: List<CustomDifficultyEntity>)
    open suspend fun insertWithLimit(difficulty: CustomDifficultyEntity): Boolean
    suspend fun update(difficulty: CustomDifficultyEntity)
    suspend fun delete(difficulty: CustomDifficultyEntity)
}
