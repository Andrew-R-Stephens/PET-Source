package com.tritiumgaming.core.ui.theme.density

import androidx.compose.runtime.staticCompositionLocalOf
import com.tritiumgaming.shared.data.preferences.model.properties.DensityType

val LocalUiDensity = staticCompositionLocalOf { DensityType.COMFORTABLE }