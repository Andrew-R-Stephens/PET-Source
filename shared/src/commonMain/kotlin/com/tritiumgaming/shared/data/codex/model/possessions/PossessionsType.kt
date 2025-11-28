package com.tritiumgaming.shared.data.codex.model.possessions

data class PossessionsType(
    val name: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionTitle,
    val icon: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionsIcon,
    val items: List<com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
