package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions.CodexPossessionsGroupItem

data class CodexPossessionGroupItemDto(
    @DrawableRes val image: Int,
    @StringRes val flavorText: Int,
    @StringRes val infoText: Int,
    @StringRes val attributesText: Int,
    @StringRes val sanityDrain: Int,
    @StringRes val drawChance: Int,
    @StringRes val altName: Int? = null
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