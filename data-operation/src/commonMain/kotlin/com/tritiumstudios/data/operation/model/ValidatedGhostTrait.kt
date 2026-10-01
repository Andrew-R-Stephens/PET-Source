package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.trait.model.GhostTrait

data class ValidatedGhostTrait(
    val ghostTrait: GhostTrait,
    val validationType: TraitValidationType = TraitValidationType.NEUTRAL
)