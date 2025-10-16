package com.tritiumgaming.data.codex.repository

import com.tritiumgaming.data.codex.dto.toDomain
import com.tritiumgaming.data.codex.source.local.AchievementsLocalDataSource
import com.tritiumgaming.data.codex.source.local.EquipmentLocalDataSource
import com.tritiumgaming.data.codex.source.local.PossessionsLocalDataSource
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.PossessionsType
import com.tritiumgaming.shared.operation.domain.codex.repository.CodexRepository

class CodexRepositoryImpl(
    private val achievementsLocalDataSource: AchievementsLocalDataSource,
    private val equipmentLocalDataSource: EquipmentLocalDataSource,
    private val possessionsLocalDataSource: PossessionsLocalDataSource
): CodexRepository {

    override fun fetchAchievements(): Result<List<AchievementsType>> {
        return achievementsLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
    }

    override fun fetchEquipment(): Result<List<EquipmentType>> {
        return equipmentLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
    }

    override fun fetchPossessions(): Result<List<PossessionsType>> {
        return possessionsLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
    }

}