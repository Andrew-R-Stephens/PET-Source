package com.tritiumgaming.data.codex.repository

import com.tritiumgaming.data.codex.dto.toDomain
import com.tritiumgaming.data.codex.source.local.AchievementsLocalDataSource
import com.tritiumgaming.data.codex.source.local.EquipmentLocalDataSource
import com.tritiumgaming.data.codex.source.local.PossessionsLocalDataSource
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType
import com.tritiumgaming.shared.data.codex.repository.CodexRepository

class CodexRepositoryImpl(
    private val achievementsLocalDataSource: AchievementsLocalDataSource,
    private val equipmentLocalDataSource: EquipmentLocalDataSource,
    private val possessionsLocalDataSource: PossessionsLocalDataSource
): CodexRepository {

    private var _achievementsCache = listOf<AchievementsType>()
    private var _equipmentCache = listOf<EquipmentType>()
    private var _possessionsCache = listOf<PossessionsType>()

    override fun fetchAchievements(): Result<List<AchievementsType>> {
        if(_achievementsCache.isEmpty()) {
            val result = achievementsLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
            val data = result.getOrThrow()
            _achievementsCache = data
        }

        return Result.success(_achievementsCache)
    }

    override fun fetchEquipment(): Result<List<EquipmentType>> {
        if(_equipmentCache.isEmpty()) {
            val result = equipmentLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
            val data = result.getOrThrow()
            _equipmentCache = data
        }
        return Result.success(_equipmentCache)
    }

    override fun fetchPossessions(): Result<List<PossessionsType>> {
        if(_possessionsCache.isEmpty()) {
            val result = possessionsLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
            val data = result.getOrThrow()
            _possessionsCache = data
        }
        return Result.success(_possessionsCache)
    }

}