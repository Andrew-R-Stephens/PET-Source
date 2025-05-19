package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.NetworkPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

fun Map<String, ExtendedPalette>.toNetwork() = map { (uuid) ->
    NetworkPaletteEntity(
        uuid = uuid
    )
}

fun List<NetworkPaletteEntity>.toExternal() = map { it ->
    MarketPalette(
        uuid = it.uuid,
        name = it.name,
        group = it.group,
        buyCredits = it.buyCredits,
        priority = it.priority,
        unlocked = it.unlocked
    )
}

fun Map<String, ExtendedPalette>.toExternal() = map { (uuid, palette) ->
    MarketPalette(
        uuid = uuid,
        palette = palette
    )
}

fun List<MarketPalette>.toPair() = associate { it ->
    it.toPair()
}
