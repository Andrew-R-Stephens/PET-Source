package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier

data class WorldMapModifierDto(
    @StringRes val name: Int,
    val setupModifier: Float,
    val normalModifier: Float
)

fun List<WorldMapModifierDto>.toDomain() = map { it.toDomain() }

fun WorldMapModifierDto.toDomain() = WorldMapModifier(
    name = name,
    setupModifier = setupModifier,
    normalModifier = normalModifier
)