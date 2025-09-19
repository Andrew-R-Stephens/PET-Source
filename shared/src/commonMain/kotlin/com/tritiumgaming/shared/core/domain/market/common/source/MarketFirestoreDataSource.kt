package com.tritiumgaming.shared.core.domain.market.common.source

/**
 * @param D - DTO
 * @param Q - Query Options
 */
interface MarketFirestoreDataSource<D, Q> {
    suspend fun fetch(queryOptions: Q): Result<List<D>>
}