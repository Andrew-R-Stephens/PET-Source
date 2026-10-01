package com.tritiumgaming.data.codex.model.possessions

import com.tritiumgaming.data.codex.mappers.PossessionsResources.PossessionTitle
import com.tritiumgaming.data.codex.mappers.PossessionsResources.PossessionsIcon

data class PossessionsType(
    val name: PossessionTitle,
    val icon: PossessionsIcon,
    val items: List<CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
