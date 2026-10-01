package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.data.typography.mappers.LocalDefaultTypography
import com.tritiumgaming.data.typography.mappers.TypographyResources

internal data class TypographyUiState(
    val typography: TypographyResources.TypographyType = LocalDefaultTypography
)

