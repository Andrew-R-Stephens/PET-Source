package com.tritiumgaming.data.customdifficulty.repository

import com.tritiumgaming.database.customdifficulty.CustomDifficultyDao
import com.tritiumgaming.database.customdifficulty.CustomDifficultyEntity
import kotlinx.coroutines.flow.Flow

class CustomDifficultyRepository(private val customDifficultyDao: CustomDifficultyDao) {

    val allDifficulties: Flow<List<CustomDifficultyEntity>> = customDifficultyDao.getAll()

    suspend fun getDifficultyById(id: Int): CustomDifficultyEntity? {
        return customDifficultyDao.getById(id)
    }

    suspend fun insert(difficulty: CustomDifficultyEntity) {
        customDifficultyDao.insert(difficulty)
    }

    suspend fun update(difficulty: CustomDifficultyEntity) {
        customDifficultyDao.update(difficulty)
    }

    suspend fun delete(difficulty: CustomDifficultyEntity) {
        customDifficultyDao.delete(difficulty)
    }
}
