package com.tritiumgaming.shared.data.codex.model.possessions

import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.*

data class PossessionsType(
    val name: PossessionTitle,
    val icon: PossessionsIcon,
    val items: List<CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
