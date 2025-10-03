package com.tritiumgaming.data.market.palette.mapper

import com.tritiumgaming.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.shared.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources

fun MarketPaletteDto.toDomain(): MarketPalette = MarketPalette(
    uuid = uuid,
    name = name,
    group = group,
    buyCredits = buyCredits,
    priority = priority,
    unlocked = unlocked,
    palette = palette
)

fun Map<String, PaletteResources.PaletteType>.toLocal(): List<MarketPaletteDto> = map { (uuid, palette) ->
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
