package com.tritiumgaming.feature.codex.ui.catalog.category

import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import com.tritiumgaming.shared.data.codex.mappers.CodexResources.Category.NONE

data class CatalogCategoryUiState(
    val currentCategory: CodexResources.Category = NONE,
    val equipment: CatalogCategory.Equipment = CatalogCategory.Equipment(),
    val possessions: CatalogCategory.Possessions = CatalogCategory.Possessions(),
    val achievements: CatalogCategory.Achievements = CatalogCategory.Achievements()
    )
