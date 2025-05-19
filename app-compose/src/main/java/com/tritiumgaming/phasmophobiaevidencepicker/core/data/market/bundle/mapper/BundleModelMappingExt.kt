package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.NetworkBundleEntity

fun MarketBundleDto.toExternal(): NetworkBundleEntity {
    return NetworkBundleEntity(
        uuid,
        name,
        buyCredits,
        items
    )
}