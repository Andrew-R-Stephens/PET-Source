package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source

interface MarketTypographyDataSource<T> {

    suspend fun fetchAll(): T

}