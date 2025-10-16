package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionDescription
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionDrawChance
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionFlavor
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionSanityDrain
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.CodexPossessionsGroupItem

data class PossessionTypeMemberDto(
    val altName: PossessionItemAltName? = null,
    val image: PossessionImage,
    val flavorText: PossessionFlavor,
    val infoText: PossessionDescription,
    val attributesText: PossessionAttribute,
    val sanityDrain: PossessionSanityDrain,
    val drawChance: PossessionDrawChance,
)

fun PossessionTypeMemberDto.toDomain() =
    CodexPossessionsGroupItem(
        altName = altName,
        image = image,
        flavorText = flavorText,
        infoText = infoText,
        attributesText = attributesText,
        sanityDrain = sanityDrain,
        drawChance = drawChance,
    )