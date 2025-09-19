package com.tritiumgaming.core.ui.theme.palette.common

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
