package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source

interface MarketTypographyDataSource<T> {

    fun getTypographies(): Result<T>

}