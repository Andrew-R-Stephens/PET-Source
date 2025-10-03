package com.tritiumgaming.data.marketplace.bundle.mapper

import com.tritiumgaming.data.marketplace.bundle.dto.MarketBundleDto
import com.tritiumgaming.shared.core.domain.market.bundle.model.MarketBundle

fun MarketBundleDto.toDomain(): MarketBundle =
    MarketBundle(
        uuid = uuid,
        name = name,
        buyCredits = buyCredits,
        items = items
    )

fun List<MarketBundleDto>.toDomain(): List<MarketBundle> =
    map( MarketBundleDto::toDomain )
