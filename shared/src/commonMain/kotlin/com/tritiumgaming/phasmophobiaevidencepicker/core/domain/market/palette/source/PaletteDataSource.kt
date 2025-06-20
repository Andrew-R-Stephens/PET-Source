package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

interface LocalPaletteDataSource<T> {

    fun getPalettes(): Result<T>

}