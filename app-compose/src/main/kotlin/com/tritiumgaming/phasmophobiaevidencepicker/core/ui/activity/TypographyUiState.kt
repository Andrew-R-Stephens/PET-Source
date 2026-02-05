package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography

internal data class TypographyUiState(
    val uuid: String = LocalDefaultTypography.uuid,
    val typography: ExtendedTypography = LocalDefaultTypography.typography
)

