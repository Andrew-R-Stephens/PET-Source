package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TextColorFamily(
    val primary: Color = Color.Unspecified, // textColorPrimary
    val secondary: Color = primary, // textColorSecondary
    val tertiary: Color = secondary, // textColorTertiary
    val body: Color = tertiary, // textColorBody
    val emphasis: Color = body, // textColorBodyEmphasis
)
