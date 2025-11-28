package com.tritiumgaming.shared.data.codex.model.possessions

data class CodexPossessionsGroupItem(
    val altName: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionItemAltName? = null,
    val image: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionImage,
    val flavorText: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionFlavor,
    val infoText: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionDescription,
    val attributesText: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionAttribute,
    val sanityDrain: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionSanityDrain,
    val drawChance: com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionDrawChance,
)