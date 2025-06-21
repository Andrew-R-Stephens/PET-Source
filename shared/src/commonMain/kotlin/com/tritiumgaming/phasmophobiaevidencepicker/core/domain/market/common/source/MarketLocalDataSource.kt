package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.common.source

interface MarketLocalDataSource<T> {

    fun get(): Result<T>

}

