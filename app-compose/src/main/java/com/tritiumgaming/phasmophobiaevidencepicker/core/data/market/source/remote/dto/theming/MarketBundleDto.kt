package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.dto.theming

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.bundle.MarketBundleEntity

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