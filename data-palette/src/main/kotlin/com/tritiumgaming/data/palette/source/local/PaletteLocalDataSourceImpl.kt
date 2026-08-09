package com.tritiumgaming.data.palette.source.local

import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources
import com.tritiumgaming.shared.data.market.palette.source.LocalPaletteDataSource

class PaletteLocalDataSourceImpl :
    LocalPaletteDataSource<List<PaletteResources.PaletteType>> {

    private val localPaletteResources: List<PaletteResources.PaletteType> = PaletteResources.PaletteType.entries

    override fun getPalettes(): Result<List<PaletteResources.PaletteType>> =
        Result.success(localPaletteResources)

}