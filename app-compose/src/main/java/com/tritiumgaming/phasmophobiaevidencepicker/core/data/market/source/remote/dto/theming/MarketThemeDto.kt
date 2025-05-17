package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.dto.theming

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyEntity

class MarketThemeDto(
    private val uuid: String,
    private val name: String,
    private val group: String,
    private val buyCredits: Long = 0L,
    private val priority: Long? = 0L
) {

    fun toMarketPaletteEntity(): PaletteEntity {
        return PaletteEntity(
            uuid = uuid,
            name = name,
            group = group,
            buyCredits = buyCredits,
            priority = priority
        )
    }

    fun toMarketTypographyEntity(): TypographyEntity {
        return TypographyEntity(
            uuid = uuid,
            name = name,
            group = group,
            buyCredits = buyCredits,
            priority = priority
        )
    }

}
