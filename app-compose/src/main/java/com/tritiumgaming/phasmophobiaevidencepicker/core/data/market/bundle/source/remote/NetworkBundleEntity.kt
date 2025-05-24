package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote

@Deprecated("Use MarketBundleDto instead")
data class NetworkBundleEntity(
    val uuid: String,
    val name: String,
    val buyCredits: Long = 0L,
    val items: List<String> = listOf()
)