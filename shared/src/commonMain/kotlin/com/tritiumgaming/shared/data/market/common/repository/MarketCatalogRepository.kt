package com.tritiumgaming.shared.data.market.common.repository

/**
 * @param E - Entity (Domain)
 */
interface MarketCatalogRepository<E> {

    suspend fun synchronizeCache(): Result<List<E>>
    fun get(): Result<List<E>>

}