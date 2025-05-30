package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

interface RemotePaletteDataSource<T> {

    suspend fun fetchPalettes(): T

}

interface LocalPaletteDataSource<T> {

    fun getPalettes(): T

}