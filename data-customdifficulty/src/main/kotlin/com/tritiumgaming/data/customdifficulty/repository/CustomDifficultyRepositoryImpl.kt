package com.tritiumgaming.data.customdifficulty.repository

import com.tritiumgaming.data.customdifficulty.mapper.toDomain
import com.tritiumgaming.data.customdifficulty.mapper.toEntity
import com.tritiumgaming.database.customdifficulty.CustomDifficultyDao
import com.tritiumgaming.database.customdifficulty.CustomDifficultyEntity
import com.tritiumgaming.shared.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.shared.data.customdifficulty.repository.CustomDifficultyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CustomDifficultyRepositoryImpl(
    private val customDifficultyDao: CustomDifficultyDao,
    scope: CoroutineScope
): CustomDifficultyRepository {

    override val allCustomDifficulties: Flow<List<CustomDifficultyModel>> =
        customDifficultyDao.getAll().map { list -> list.map { it.toDomain() } }

    init {
        scope.launch {
            if (customDifficultyDao.getCount() == 0) {
                val defaults = List(3) { i ->
                    CustomDifficultyEntity.createDefault()
                }
                customDifficultyDao.insertAll(defaults)
            }
        }
    }

    override suspend fun update(difficulty: CustomDifficultyModel) {
        customDifficultyDao.update(difficulty.toEntity())
    }
}
