package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SwitchColorFamily(
    val trackInactive: Color = Color.Unspecified,
    val trackActive: Color = trackInactive,
    val thumbInactive: Color = Color.Unspecified, //switchColorOff
    val thumbActive: Color = thumbInactive, //switchColorOn
)