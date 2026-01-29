package com.tritiumgaming.shared.data.codex.model.possessions

import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources

data class CodexPossessionsGroupItem(
    val altName: PossessionsResources.PossessionItemAltName? = null,
    val image: PossessionsResources.PossessionImage,
    val flavorText: PossessionsResources.PossessionFlavor,
    val infoText: PossessionsResources.PossessionDescription,
    val attributesText: PossessionsResources.PossessionAttribute,
    val sanityDrain: PossessionsResources.PossessionSanityDrain,
    val drawChance: PossessionsResources.PossessionDrawChance,
)