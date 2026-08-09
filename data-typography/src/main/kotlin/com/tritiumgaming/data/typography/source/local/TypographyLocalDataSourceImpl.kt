package com.tritiumgaming.data.typography.source.local

import com.tritiumgaming.shared.data.market.common.source.MarketLocalDataSource
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType

class TypographyLocalDataSourceImpl:
    MarketLocalDataSource<List<TypographyType>> {

    val typographyResources = TypographyType.entries

    override fun get(): Result<List<TypographyType>> =
        Result.success(typographyResources)

}
