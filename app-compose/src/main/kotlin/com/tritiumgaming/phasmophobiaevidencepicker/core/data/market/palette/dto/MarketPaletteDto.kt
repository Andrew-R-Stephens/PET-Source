package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

data class MarketPaletteDto(
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = true,
    internal val palette: ExtendedPalette? = null
)
