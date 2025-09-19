package com.tritiumgaming.shared.operation.domain.codex.model.possessions

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemDrawChance
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemFlavorText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemInfoText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemSanityDrain

data class CodexPossessionsGroupItem(
    val altName: PossessionItemAltName? = null,
    val image: PossessionItemImage,
    val flavorText: PossessionItemFlavorText,
    val infoText: PossessionItemInfoText,
    val attributesText: PossessionItemAttribute,
    val sanityDrain: PossessionItemSanityDrain,
    val drawChance: PossessionItemDrawChance,
)