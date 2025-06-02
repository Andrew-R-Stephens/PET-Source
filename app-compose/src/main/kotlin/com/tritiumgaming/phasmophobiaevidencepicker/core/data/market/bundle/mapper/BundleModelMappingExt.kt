package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.MarketBundle


fun MarketBundleDto.toDomain(): MarketBundle =
    MarketBundle(
        uuid = uuid,
        name = name,
        buyCredits = buyCredits,
        items = items
    )

fun List<MarketBundleDto>.toDomain(): List<MarketBundle> =
    map( MarketBundleDto::toDomain )
