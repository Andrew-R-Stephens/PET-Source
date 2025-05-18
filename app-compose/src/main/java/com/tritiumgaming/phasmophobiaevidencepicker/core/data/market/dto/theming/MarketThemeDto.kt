package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.dto.theming

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.NetworkPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.NetworkTypographyEntity

class MarketThemeDto(
    private val uuid: String,
    private val name: String,
    private val group: String,
    private val buyCredits: Long = 0L,
    private val priority: Long? = 0L
) {

    fun toMarketPaletteEntity(): NetworkPaletteEntity {
        return NetworkPaletteEntity(
            uuid = uuid,
            name = name,
            group = group,
            buyCredits = buyCredits,
            priority = priority
        )
    }

    fun toMarketTypographyEntity(): NetworkTypographyEntity {
        return NetworkTypographyEntity(
            uuid = uuid,
            name = name,
            group = group,
            buyCredits = buyCredits,
            priority = priority
        )
    }

}
