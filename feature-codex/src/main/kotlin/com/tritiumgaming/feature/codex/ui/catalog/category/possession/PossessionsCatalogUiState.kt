package com.tritiumgaming.feature.codex.ui.catalog.category.possession

import androidx.annotation.IntegerRes
import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

data class PossessionsCatalogUiState(
    val list: List<PossessionsType> = emptyList(),
    val selectedGroup: PossessionsType? = null,
    val selectedItem: CodexPossessionsGroupItem? = null,
    @field:IntegerRes val icons: List<Int> = emptyList()
)