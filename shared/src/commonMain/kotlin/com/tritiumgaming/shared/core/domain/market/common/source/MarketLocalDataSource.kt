package com.tritiumgaming.shared.core.domain.market.common.source

interface MarketLocalDataSource<T> {

    fun get(): Result<T>

}

