package com.tritiumgaming.shared.data.codex.model.possessions

import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources

data class PossessionsType(
    val name: PossessionsResources.PossessionTitle,
    val icon: PossessionsResources.PossessionsIcon,
    val items: List<CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
