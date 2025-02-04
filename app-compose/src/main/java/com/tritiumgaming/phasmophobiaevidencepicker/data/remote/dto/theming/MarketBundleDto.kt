package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.dto.theming

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketBundleEntity

class MarketBundleDto(
    private val uuid: String,
    private val name: String,
    private val buyCredits: Long = 0,
    private val items: List<String> = listOf()
) {

    fun toMarketBundleEntity(): MarketBundleEntity {
        return MarketBundleEntity(
            uuid,
            name,
            buyCredits,
            items
        )
    }

}