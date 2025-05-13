package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

interface TypographyLocalDataSource {
    val typographies: Map<String, ExtendedTypography>
}