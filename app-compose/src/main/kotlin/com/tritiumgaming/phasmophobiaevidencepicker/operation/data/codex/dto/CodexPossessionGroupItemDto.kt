package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAltName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemDrawChance
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemFlavorText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemInfoText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemSanityDrain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions.CodexPossessionsGroupItem

data class CodexPossessionGroupItemDto(
    val image: PossessionItemImage,
    val flavorText: PossessionItemFlavorText,
    val infoText: PossessionItemInfoText,
    val attributesText: PossessionItemAttribute,
    val sanityDrain: PossessionItemSanityDrain,
    val drawChance: PossessionItemDrawChance,
    val altName: PossessionItemAltName? = null
)

fun CodexPossessionGroupItemDto.toDomain() =
    CodexPossessionsGroupItem(
        image = image,
        flavorText = flavorText,
        infoText = infoText,
        attributesText = attributesText,
        sanityDrain = sanityDrain,
        drawChance = drawChance,
        altName = altName
    )