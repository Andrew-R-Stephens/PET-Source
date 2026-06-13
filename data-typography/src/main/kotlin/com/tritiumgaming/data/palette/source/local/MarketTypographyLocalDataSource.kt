package com.tritiumgaming.data.palette.source.local

import com.tritiumgaming.shared.data.market.common.source.MarketLocalDataSource
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.ANDROID
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.BRICK
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.CLASSIC
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.CLEAN
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.JETBRAINS_MONO
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.JOURNAL
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.LONG_CANG
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.NEUCHA
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.NEW_TEGOMIN

class MarketTypographyLocalDataSource:
    MarketLocalDataSource<List<TypographyType>> {

    val typographyResources = TypographyType.entries

    override fun get(): Result<List<TypographyType>> =
        Result.success(typographyResources)

}
