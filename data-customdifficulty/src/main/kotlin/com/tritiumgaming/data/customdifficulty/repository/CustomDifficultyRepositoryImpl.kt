package com.tritiumgaming.data.customdifficulty.repository

import com.tritiumgaming.data.customdifficulty.mapper.toDomain
import com.tritiumgaming.data.customdifficulty.mapper.toEntity
import com.tritiumgaming.database.customdifficulty.CustomDifficultyDao
import com.tritiumgaming.database.customdifficulty.CustomDifficultyEntity
import com.tritiumgaming.shared.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.shared.data.customdifficulty.repository.CustomDifficultyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CustomDifficultyRepositoryImpl(
    private val customDifficultyDao: CustomDifficultyDao,
    scope: CoroutineScope
): CustomDifficultyRepository {

    override val allCustomDifficulties: Flow<Result<List<CustomDifficultyModel>>> =
        customDifficultyDao.getAll().map { list ->
            Result.success(list.map { it.toDomain() })
        }.catch { e ->
            emit(Result.failure(e))
        }

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

    override suspend fun update(difficulty: CustomDifficultyModel): Result<Unit> {
        return try {
            customDifficultyDao.update(difficulty.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
