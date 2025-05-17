package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypographiesMap

class TypographyLocalDataSource(
    override val typographies: Map<String, ExtendedTypography> = LocalTypographiesMap
): TypographyDataSource
