package com.tritiumgaming.data.marketplace.common.source

interface MarketLocalDataSource<T> {

    fun get(): Result<T>

}

