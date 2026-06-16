package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait

data class ValidatedGhostTrait(
    val ghostTrait: GhostTrait,
    val validationType: TraitValidationType = TraitValidationType.NEUTRAL
)