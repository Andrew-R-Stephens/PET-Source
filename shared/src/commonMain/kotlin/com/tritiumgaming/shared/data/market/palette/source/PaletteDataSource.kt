package com.tritiumgaming.shared.data.market.palette.source

interface LocalPaletteDataSource<T> {

    fun getPalettes(): Result<T>

}