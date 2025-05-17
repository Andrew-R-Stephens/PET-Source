package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

interface TypographyDataSource {
    val typographies: Map<String, ExtendedTypography>
}