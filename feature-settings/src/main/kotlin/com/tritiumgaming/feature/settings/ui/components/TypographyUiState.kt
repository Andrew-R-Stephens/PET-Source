package com.tritiumgaming.feature.settings.ui.components

import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.shared.data.market.typography.model.LocalDefaultTypography
import com.tritiumgaming.shared.data.market.typography.model.asUuid

data class TypographyUiState(
    val uuid: String = LocalDefaultTypography.asUuid(),
    val typography: ExtendedTypography = LocalDefaultTypography.toTypographyResource()
)
