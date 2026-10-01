package com.tritiumgaming.data.marketplace.common.source

/**
 * @param D - DTO
 * @param Q - Query Options
 */
interface MarketFirestoreDataSource<D, Q> {
    suspend fun fetch(queryOptions: Q, version: Int): Result<List<D>>
}