package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CodexPossessionsGroupItem(
    @DrawableRes val image: Int,
    @StringRes val flavorText: Int,
    @StringRes val infoText: Int,
    @StringRes val attributesText: Int,
    @StringRes val sanityDrain: Int,
    @StringRes val drawChance: Int,
    @StringRes val altName: Int? = null
)