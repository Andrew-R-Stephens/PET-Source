package com.tritiumgaming.shared.data.codex.repository

interface CodexRepository {

    fun fetchAchievements(): Result<List<com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType>>
    fun fetchEquipment(): Result<List<com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType>>
    fun fetchPossessions(): Result<List<com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType>>

}