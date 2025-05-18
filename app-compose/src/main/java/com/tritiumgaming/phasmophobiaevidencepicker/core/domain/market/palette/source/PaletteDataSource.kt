package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

interface PaletteDataSource<T> {

    suspend fun fetchAll(): T

}