package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.NetworkPaletteEntity

class MarketPaletteDto(
    internal val uuid: String,
    internal val name: String,
    internal val group: String,
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L
)

fun MarketPaletteDto.toExternal(): NetworkPaletteEntity {
    return NetworkPaletteEntity(
        uuid = uuid,
        name = name,
        group = group,
        buyCredits = buyCredits,
        priority = priority
    )
}
