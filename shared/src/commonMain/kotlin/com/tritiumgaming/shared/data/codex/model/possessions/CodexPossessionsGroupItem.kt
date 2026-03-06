package com.tritiumgaming.shared.data.codex.model.possessions

import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.*

data class CodexPossessionsGroupItem(
    val altName: PossessionItemAltName? = null,
    val image: PossessionImage,
    val flavorText: PossessionFlavor,
    val infoText: PossessionDescription,
    val attributesText: PossessionAttribute,
    val sanityDrain: PossessionSanityDrain,
    val drawChance: PossessionDrawChance,
)