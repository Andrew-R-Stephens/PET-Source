package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.dto.theming

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketTypographyEntity

class MarketThemeDto(
    private val uuid: String,
    private val name: String,
    private val group: String,
    private val buyCredits: Long = 0L,
    private val priority: Long? = 0L
) {

    fun toMarketPaletteEntity(): MarketPaletteEntity {
        return MarketPaletteEntity(
            uuid = uuid,
            name = name,
            group = group,
            buyCredits = buyCredits,
            priority = priority
        )
    }

    fun toMarketTypographyEntity(): MarketTypographyEntity {
        return MarketTypographyEntity(
            uuid = uuid,
            name = name,
            group = group,
            buyCredits = buyCredits,
            priority = priority
        )
    }

}
