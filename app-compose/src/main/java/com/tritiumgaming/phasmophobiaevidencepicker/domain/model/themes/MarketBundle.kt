package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes

data class MarketBundle (
    val marketBundleEntity: MarketBundleEntity? = null,
    val uuid: String = marketBundleEntity?.uuid ?: ""
)