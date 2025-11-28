package com.tritiumgaming.shared.data.market.bundle.model
data class MarketBundle (
    val uuid: String,
    val name: String,
    val buyCredits: Long = 0L,
    val items: List<String> = listOf()
)

fun com.tritiumgaming.shared.data.market.bundle.model.MarketBundle.toPair(): Pair<String, com.tritiumgaming.shared.data.market.bundle.model.MarketBundle> {
    return Pair(uuid, this)
}