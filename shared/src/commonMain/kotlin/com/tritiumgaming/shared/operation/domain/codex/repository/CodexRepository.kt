package com.tritiumgaming.shared.operation.domain.codex.repository

import com.tritiumgaming.shared.operation.domain.codex.model.achievements.CodexAchievementsGroup
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.CodexEquipmentGroup
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.CodexPossessionsGroup

interface CodexRepository {

    fun fetchAchievements(): Result<List<CodexAchievementsGroup>>
    fun fetchEquipment(): Result<List<CodexEquipmentGroup>>
    fun fetchPossessions(): Result<List<CodexPossessionsGroup>>

}