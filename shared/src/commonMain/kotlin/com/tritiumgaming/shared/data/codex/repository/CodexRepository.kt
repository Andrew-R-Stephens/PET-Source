package com.tritiumgaming.shared.data.codex.repository

import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

interface CodexRepository {

    fun fetchAchievements(): Result<List<AchievementsType>>
    fun fetchEquipment(): Result<List<EquipmentType>>
    fun fetchPossessions(): Result<List<PossessionsType>>

}