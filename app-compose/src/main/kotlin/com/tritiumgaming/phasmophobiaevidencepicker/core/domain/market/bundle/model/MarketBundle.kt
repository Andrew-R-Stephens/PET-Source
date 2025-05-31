package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model

data class MarketBundle (
    val uuid: String,
    val name: String,
    val buyCredits: Long = 0L,
    val items: List<String> = listOf()
)

fun MarketBundle.toPair(): Pair<String, MarketBundle> {
    return Pair(uuid, this)
}