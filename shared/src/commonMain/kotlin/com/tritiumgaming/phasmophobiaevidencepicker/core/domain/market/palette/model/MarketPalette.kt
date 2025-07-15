package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketPalette

data class MarketPalette(
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = false,
    val palette: PaletteType? = null
)

fun MarketPalette.toPair(): Pair<String, MarketPalette> {
    return Pair(uuid, this)
}

fun List<MarketPalette>.toPair() = associate { it ->
    it.toPair()
}

fun List<MarketPalette>.toAccountMarketPalette() = map {
    it.toAccountMarketPalette()
}

fun MarketPalette.toAccountMarketPalette(): AccountMarketPalette {
    return AccountMarketPalette(
        uuid = uuid,
        name = name,
        group = group,
        buyCredits = buyCredits,
        priority = priority,
        unlocked = unlocked,
        palette = palette
    )
}