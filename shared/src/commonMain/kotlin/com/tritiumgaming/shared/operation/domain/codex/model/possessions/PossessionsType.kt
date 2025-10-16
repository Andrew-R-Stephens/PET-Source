package com.tritiumgaming.shared.operation.domain.codex.model.possessions

import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionTitle
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionsIcon

data class PossessionsType(
    val name: PossessionTitle,
    val icon: PossessionsIcon,
    val items: List<CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
