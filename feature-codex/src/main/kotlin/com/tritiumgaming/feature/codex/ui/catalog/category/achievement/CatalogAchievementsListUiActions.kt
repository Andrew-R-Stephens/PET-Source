package com.tritiumgaming.feature.codex.ui.catalog.category.achievement

import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem

data class CatalogAchievementsListUiActions(
    val onSelect: (AchievementsType, CodexAchievementsGroupItem) -> Unit
)
