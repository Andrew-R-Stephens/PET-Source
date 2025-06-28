package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class M3ColorFamily(
    val color: Color = Color.Unspecified,
    val onColor: Color = Color.Unspecified,
    val colorContainer: Color = Color.Unspecified,
    val onColorContainer: Color = Color.Unspecified,

    val variant: Color = Color.Unspecified,
    val onColorVariant: Color = Color.Unspecified,
    val containerVariant: Color = Color.Unspecified,
    val onContainerVariant: Color = Color.Unspecified
)