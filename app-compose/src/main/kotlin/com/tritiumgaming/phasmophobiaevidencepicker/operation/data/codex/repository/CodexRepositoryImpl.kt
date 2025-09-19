package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.CodexAchievementsGroup
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.CodexEquipmentGroup
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.CodexPossessionsGroup
import com.tritiumgaming.shared.operation.domain.codex.repository.CodexRepository

class CodexRepositoryImpl(
    private val achievementsLocalDataSource: CodexAchievementsLocalDataSource,
    private val equipmentLocalDataSource: CodexEquipmentLocalDataSource,
    private val possessionsLocalDataSource: CodexPossessionsLocalDataSource
): CodexRepository {

    override fun fetchAchievements(): Result<List<CodexAchievementsGroup>> {
        return achievementsLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
    }

    override fun fetchEquipment(): Result<List<CodexEquipmentGroup>> {
        return equipmentLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
    }

    override fun fetchPossessions(): Result<List<CodexPossessionsGroup>> {
        return possessionsLocalDataSource.fetchItems().map { dto -> dto.toDomain() }
    }

}