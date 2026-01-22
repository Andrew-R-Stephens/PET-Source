package com.tritiumgaming.feature.settings.ui.components

import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography

data class TypographyUiState(
    val uuid: String = LocalDefaultTypography.uuid,
    val typography: ExtendedTypography = LocalDefaultTypography.typography
)
