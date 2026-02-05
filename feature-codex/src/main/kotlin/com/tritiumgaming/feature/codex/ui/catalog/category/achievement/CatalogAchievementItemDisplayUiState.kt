package com.tritiumgaming.feature.codex.ui.catalog.category.achievement

import androidx.annotation.IntegerRes
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem

data class CatalogAchievementItemDisplayUiState(
    val list: List<AchievementsType> = emptyList(),
    val selectedGroup: AchievementsType? = null,
    val selectedItem: CodexAchievementsGroupItem? = null,
    @field:IntegerRes val icons: List<Int> = emptyList()
)