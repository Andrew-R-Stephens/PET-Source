package com.tritiumgaming.shared.core.domain.market.palette.source

interface LocalPaletteDataSource<T> {

    fun getPalettes(): Result<T>

}