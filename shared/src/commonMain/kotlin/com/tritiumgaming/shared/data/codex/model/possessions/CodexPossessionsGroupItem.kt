package com.tritiumgaming.shared.data.codex.model.possessions

import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionAttribute
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionDescription
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionDrawChance
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionFlavor
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionImage
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionSanityDrain

data class CodexPossessionsGroupItem(
    val altName: PossessionItemAltName? = null,
    val image: PossessionImage,
    val flavorText: PossessionFlavor,
    val infoText: PossessionDescription,
    val attributesText: PossessionAttribute,
    val sanityDrain: PossessionSanityDrain,
    val drawChance: PossessionDrawChance,
)