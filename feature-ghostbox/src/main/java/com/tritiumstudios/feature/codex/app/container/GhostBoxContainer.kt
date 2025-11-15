package com.tritiumstudios.feature.codex.app.container

import com.tritiumgaming.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.data.codex.source.local.AchievementsLocalDataSource
import com.tritiumgaming.data.codex.source.local.EquipmentLocalDataSource
import com.tritiumgaming.data.codex.source.local.PossessionsLocalDataSource
import com.tritiumgaming.shared.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchPossessionTypesUseCase

class GhostBoxContainer() {
    // Codex
    private val codexRepository: CodexRepository by lazy {
        val achievementsLocalDataSource = AchievementsLocalDataSource()
        val equipmentLocalDataSource = EquipmentLocalDataSource()
        val possessionsLocalDataSource = PossessionsLocalDataSource()
        CodexRepositoryImpl(
            achievementsLocalDataSource = achievementsLocalDataSource,
            equipmentLocalDataSource = equipmentLocalDataSource,
            possessionsLocalDataSource = possessionsLocalDataSource
        )
    }

    internal val fetchCodexAchievementsUseCase = FetchAchievementTypesUseCase(
        codexRepository = codexRepository
    )
    internal val fetchCodexEquipmentUseCase = FetchEquipmentTypesUseCase(
        codexRepository = codexRepository
    )
    internal val fetchCodexPossessionsUseCase = FetchPossessionTypesUseCase(
        codexRepository = codexRepository
    )

}
