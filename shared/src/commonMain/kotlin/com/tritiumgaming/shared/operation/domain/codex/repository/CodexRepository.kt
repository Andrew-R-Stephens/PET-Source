package com.tritiumgaming.shared.operation.domain.codex.repository

import com.tritiumgaming.shared.operation.domain.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.PossessionsType

interface CodexRepository {

    fun fetchAchievements(): Result<List<AchievementsType>>
    fun fetchEquipment(): Result<List<EquipmentType>>
    fun fetchPossessions(): Result<List<PossessionsType>>

}