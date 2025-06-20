package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionsIcon

data class CodexPossessionsGroup(
    val name: PossessionTitle,
    val icon: PossessionsIcon,
    val items: List<CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
