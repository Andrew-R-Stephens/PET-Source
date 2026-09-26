package com.tritiumgaming.data.customdifficulty.source.local

import kotlinx.coroutines.flow.Flow

actual interface CustomDifficultyDao {
    actual fun getAll(): Flow<List<CustomDifficultyEntity>>
    actual suspend fun getById(id: Int): CustomDifficultyEntity?
    actual suspend fun getCount(): Int
    actual suspend fun insert(difficulty: CustomDifficultyEntity)
    actual suspend fun insertAll(difficulties: List<CustomDifficultyEntity>)
    actual suspend fun insertWithLimit(difficulty: CustomDifficultyEntity): Boolean = false
    actual suspend fun update(difficulty: CustomDifficultyEntity)
    actual suspend fun delete(difficulty: CustomDifficultyEntity)
}
