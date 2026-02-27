package com.tritiumgaming.core.ui.theme.density

import androidx.compose.runtime.staticCompositionLocalOf
import com.tritiumgaming.shared.data.preferences.DensityType

val LocalUiDensity = staticCompositionLocalOf { DensityType.COMFORTABLE }