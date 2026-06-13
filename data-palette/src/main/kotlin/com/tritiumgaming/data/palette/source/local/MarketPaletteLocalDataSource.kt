package com.tritiumgaming.data.palette.source.local

import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.palette.source.LocalPaletteDataSource

class MarketPaletteLocalDataSource :
    LocalPaletteDataSource<List<PaletteType>> {

    private val localPaletteResources: List<PaletteType> = PaletteType.entries

    override fun getPalettes(): Result<List<PaletteType>> =
        Result.success(localPaletteResources)

}
