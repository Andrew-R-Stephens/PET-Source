package com.tritiumgaming.data.codex.repository

import com.tritiumgaming.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.data.codex.model.possessions.PossessionsType

interface CodexRepository {

    fun fetchAchievements(): Result<List<AchievementsType>>
    fun fetchEquipment(): Result<List<EquipmentType>>
    fun fetchPossessions(): Result<List<PossessionsType>>

}