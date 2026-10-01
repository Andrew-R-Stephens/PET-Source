package com.tritiumgaming.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.tritiumgaming.data.model.properties.DensityType

val LocalUiConfiguration = staticCompositionLocalOf { ExtendedUiConfiguration() }

data class ExtendedUiConfiguration(
    val densityType: DensityType = DensityType.COMFORTABLE,
    val isRtl: Boolean = false
)