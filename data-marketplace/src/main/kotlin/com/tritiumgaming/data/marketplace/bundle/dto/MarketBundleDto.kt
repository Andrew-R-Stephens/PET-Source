package com.tritiumgaming.data.marketplace.bundle.dto

class MarketBundleDto(
    internal val uuid: String,
    internal val name: String,
    internal val buyCredits: Long = 0,
    internal val items: List<String> = listOf()
)
