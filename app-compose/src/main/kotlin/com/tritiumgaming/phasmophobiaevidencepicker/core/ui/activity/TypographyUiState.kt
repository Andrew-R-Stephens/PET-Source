package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.shared.data.market.typography.mappers.LocalDefaultTypography
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources

internal data class TypographyUiState(
    val typography: TypographyResources.TypographyType = LocalDefaultTypography
)

