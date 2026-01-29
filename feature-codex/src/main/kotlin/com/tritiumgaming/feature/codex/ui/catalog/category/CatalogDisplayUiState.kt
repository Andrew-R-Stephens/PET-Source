package com.tritiumgaming.feature.codex.ui.catalog.category

import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier
import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

abstract class CatalogDisplayUiState(
    val category: CodexResources.Category? = null
) {
    class None : CatalogDisplayUiState(category = CodexResources.Category.NONE)

    data class Equipment(
        val selectedGroup: EquipmentType? = null,
        val selectedItem: EquipmentTypeTier? = null
    ) : CatalogDisplayUiState(category = CodexResources.Category.EQUIPMENT)

    data class Possessions(
        val selectedGroup: PossessionsType? = null,
        val selectedItem: CodexPossessionsGroupItem? = null
    ) : CatalogDisplayUiState(category = CodexResources.Category.POSSESSIONS)

    data class Achievements(
        val selectedGroup: AchievementsType? = null,
        val selectedItem: CodexAchievementsGroupItem? = null
    ) : CatalogDisplayUiState(category = CodexResources.Category.ACHIEVEMENTS)
}
