package com.tritiumgaming.shared.data.market.palette.model

data class MarketPalette(
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = false,
    val palette: com.tritiumgaming.shared.data.market.palette.model.PaletteResources.PaletteType? = null
)

fun com.tritiumgaming.shared.data.market.palette.model.MarketPalette.toPair(): Pair<String, com.tritiumgaming.shared.data.market.palette.model.MarketPalette> {
    return Pair(uuid, this)
}

fun List<com.tritiumgaming.shared.data.market.palette.model.MarketPalette>.toPair() = associate { it ->
    it.toPair()
}

fun List<com.tritiumgaming.shared.data.market.palette.model.MarketPalette>.toAccountMarketPalette() = map {
    it.toAccountMarketPalette()
}

fun com.tritiumgaming.shared.data.market.palette.model.MarketPalette.toAccountMarketPalette(): com.tritiumgaming.shared.data.account.model.AccountMarketPalette {
    return com.tritiumgaming.shared.data.account.model.AccountMarketPalette(
        uuid = uuid,
        name = name,
        group = group,
        buyCredits = buyCredits,
        priority = priority,
        unlocked = unlocked,
        palette = palette
    )
}