package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.dto.theming

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity

class MarketBundleDto(
    private val uuid: String,
    private val name: String,
    private val buyCredits: Long = 0,
    private val items: List<String> = listOf()
) {

    fun toMarketBundleEntity(): NetworkBundleEntity {
        return NetworkBundleEntity(
            uuid,
            name,
            buyCredits,
            items
        )
    }

}