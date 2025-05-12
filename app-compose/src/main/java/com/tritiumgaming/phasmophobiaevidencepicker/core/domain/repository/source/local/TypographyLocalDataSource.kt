package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

interface TypographyLocalDataSource {
    val typographies: Map<String, ExtendedTypography>
}