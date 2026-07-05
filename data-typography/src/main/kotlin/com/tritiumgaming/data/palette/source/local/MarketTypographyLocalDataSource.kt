package com.tritiumgaming.data.palette.source.local

import com.tritiumgaming.shared.data.market.common.source.MarketLocalDataSource
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType

class MarketTypographyLocalDataSource:
    MarketLocalDataSource<List<TypographyType>> {

    val typographyResources = TypographyType.entries

    override fun get(): Result<List<TypographyType>> =
        Result.success(typographyResources)

}
