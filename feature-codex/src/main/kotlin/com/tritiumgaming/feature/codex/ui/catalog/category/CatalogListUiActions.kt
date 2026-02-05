package com.tritiumgaming.feature.codex.ui.catalog.category

import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier
import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

sealed interface CatalogListUiActions {
    data class Equipment(
        val onSelect: (EquipmentType, EquipmentTypeTier) -> Unit
    ) : CatalogListUiActions

    data class Possessions(
        val onSelect: (PossessionsType, CodexPossessionsGroupItem) -> Unit
    ) : CatalogListUiActions

    data class Achievements(
        val onSelect: (AchievementsType, CodexAchievementsGroupItem) -> Unit
    ) : CatalogListUiActions
}