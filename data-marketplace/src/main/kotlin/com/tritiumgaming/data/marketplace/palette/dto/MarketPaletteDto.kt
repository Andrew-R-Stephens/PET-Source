package com.tritiumgaming.data.marketplace.palette.dto

import com.tritiumgaming.shared.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources.PaletteType

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

fun MarketPaletteDto.toDomain(): MarketPalette = MarketPalette(
    uuid = uuid,
    name = name,
    group = group,
    buyCredits = buyCredits,
    priority = priority,
    unlocked = unlocked,
    palette = palette
)

fun Map<String, PaletteType>.toLocal(): List<MarketPaletteDto> = map { (uuid, palette) ->
    MarketPaletteDto(
        uuid = uuid,
        palette = palette,
        unlocked = true
    )
}

fun List<MarketPaletteDto>.toDomain(): List<MarketPalette> = map { dto ->
    MarketPalette(
        uuid = dto.uuid,
        name = dto.name,
        group = dto.group,
        buyCredits = dto.buyCredits,
        priority = dto.priority,
        unlocked = dto.unlocked,
        palette = dto.palette
    )
}
