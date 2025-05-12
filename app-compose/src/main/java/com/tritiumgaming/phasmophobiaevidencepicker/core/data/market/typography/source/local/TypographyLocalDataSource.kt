package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypographiesMap

class TypographyLocalDataSource(
    override val typographys: Map<String, ExtendedTypography> = LocalTypographiesMap
): TypographyLocalDataSource
