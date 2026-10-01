package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.data.model.properties.DensityType

internal data class UiConfigurationState(
    val densityType: DensityType = DensityType.COMFORTABLE,
    val isRtl: Boolean = false
)