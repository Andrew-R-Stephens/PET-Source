package com.tritiumgaming.feature.codex.ui.catalog.category

import androidx.annotation.IntegerRes
import com.tritiumgaming.shared.data.codex.mappers.CodexResources.Category
import com.tritiumgaming.shared.data.codex.mappers.CodexResources.Category.ACHIEVEMENTS
import com.tritiumgaming.shared.data.codex.mappers.CodexResources.Category.EQUIPMENT
import com.tritiumgaming.shared.data.codex.mappers.CodexResources.Category.POSSESSIONS
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

abstract class CatalogCategory(
    val category: Category,
    @field:IntegerRes open val icons: List<Int>
) {
    class None: CatalogCategory(
        category = Category.NONE,
        icons = emptyList()
    )

    data class Equipment(
        val list: List<EquipmentType> = emptyList(),
        @field:IntegerRes override val icons: List<Int> = emptyList()
    ) : CatalogCategory(
        category = EQUIPMENT,
        icons = icons
    )

    data class Possessions(
        val list: List<PossessionsType> = emptyList(),
        @field:IntegerRes override val icons: List<Int> = emptyList()
    ) : CatalogCategory(
        category = POSSESSIONS,
        icons = icons
    )

    data class Achievements(
        val list: List<AchievementsType> = emptyList(),
        @field:IntegerRes override val icons: List<Int> = emptyList()
    ) : CatalogCategory(
        category = ACHIEVEMENTS,
        icons = icons
    )
}
