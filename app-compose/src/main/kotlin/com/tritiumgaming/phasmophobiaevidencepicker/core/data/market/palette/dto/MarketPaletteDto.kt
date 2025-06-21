package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType

data class MarketPaletteDto(
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val palette: PaletteType? = null
) {
    override fun toString(): String {
        return "MarketPaletteDto(uuid='$uuid', name='$name', group='$group', buyCredits=$buyCredits, priority=$priority, unlocked=$unlocked, palette=$palette)"
    }

}
