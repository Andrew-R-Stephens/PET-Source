package com.tritiumgaming.data.marketplace.typography.model

import com.tritiumgaming.data.marketplace.account.model.AccountMarketTypography
import com.tritiumgaming.data.typography.mappers.TypographyResources

data class MarketTypography (
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = false,
    val typography: TypographyResources.TypographyType? = null
)

fun MarketTypography.toPair(): Pair<String, MarketTypography> {
    return Pair(uuid, this)
}

fun List<MarketTypography>.toPair() = associate {
    it.toPair()
}

fun List<MarketTypography>.toAccountMarketTypography() = map {
    it.toAccountMarketTypography()
}

fun MarketTypography.toAccountMarketTypography(): AccountMarketTypography {
    return AccountMarketTypography(
        uuid = uuid,
        name = name,
        group = group,
        buyCredits = buyCredits,
        priority = priority,
        unlocked = unlocked,
        typography = typography
    )
}