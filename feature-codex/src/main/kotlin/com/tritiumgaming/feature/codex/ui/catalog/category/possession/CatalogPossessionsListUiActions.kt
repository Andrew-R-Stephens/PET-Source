package com.tritiumgaming.feature.codex.ui.catalog.category.possession

import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

data class CatalogPossessionsListUiActions(
    val onSelect: (PossessionsType, CodexPossessionsGroupItem) -> Unit
)
