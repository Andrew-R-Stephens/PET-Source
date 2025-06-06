package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

fun MarketPalette.toPair(): Pair<String, MarketPalette> {
    return Pair(uuid, this)
}
fun MarketPaletteDto.toExternal() = MarketPalette(
    uuid = uuid,
    name = name,
    group = group,
    buyCredits = buyCredits,
    priority = priority,
    unlocked = unlocked,
    palette = palette
)

fun Map<String, ExtendedPalette>.toLocal() = map { (uuid, palette) ->
    MarketPaletteDto(
        uuid = uuid,
        palette = palette
    )
}

fun Map<String, ExtendedPalette>.toExternal() = map { (uuid, palette) ->
    MarketPalette(
        uuid = uuid,
        palette = palette
    )
}

fun List<MarketPaletteDto>.toExternal() = map { dto ->
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

fun List<MarketPalette>.toPair() = associate { it ->
    it.toPair()
}
