package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.shared.data.market.typography.mappers.LocalDefaultTypography
import com.tritiumgaming.shared.data.market.typography.mappers.asUuid

internal data class TypographyUiState(
    val uuid: String = LocalDefaultTypography.asUuid(),
    val typography: ExtendedTypography = LocalDefaultTypography.toTypographyResource()
)

