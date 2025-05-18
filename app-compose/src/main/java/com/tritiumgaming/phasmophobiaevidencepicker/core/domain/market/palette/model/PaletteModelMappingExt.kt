package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.NetworkPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

fun Map<String, ExtendedPalette>.toNetwork() = map { (uuid) ->
    NetworkPaletteEntity(
        uuid = uuid
    )
}

fun List<NetworkPaletteEntity>.toExternal() = map { it ->
    PaletteEntity(
        uuid = it.uuid,
        name = it.name,
        group = it.group,
        buyCredits = it.buyCredits,
        priority = it.priority,
        unlocked = it.unlocked
    )
}

fun Map<String, ExtendedPalette>.toExternal() = map { (uuid, palette) ->
    PaletteEntity(
        uuid = uuid,
        palette = palette
    )
}

fun List<PaletteEntity>.toPair() = associate { it ->
    it.toPair()
}
