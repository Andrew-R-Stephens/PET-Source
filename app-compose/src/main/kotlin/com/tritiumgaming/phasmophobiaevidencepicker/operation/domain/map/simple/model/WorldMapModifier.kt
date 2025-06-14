package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import androidx.annotation.StringRes

data class WorldMapModifier(
    @StringRes val name: Int,
    val setupModifier: Float = 0f,
    val normalModifier: Float = 0f
)