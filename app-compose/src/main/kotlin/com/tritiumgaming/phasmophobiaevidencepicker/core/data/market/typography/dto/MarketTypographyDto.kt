package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

data class MarketTypographyDto(
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val typography: ExtendedTypography? = null
)
