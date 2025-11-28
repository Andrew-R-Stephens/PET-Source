package com.tritiumgaming.shared.data.market.common.source

interface MarketLocalDataSource<T> {

    fun get(): Result<T>

}

