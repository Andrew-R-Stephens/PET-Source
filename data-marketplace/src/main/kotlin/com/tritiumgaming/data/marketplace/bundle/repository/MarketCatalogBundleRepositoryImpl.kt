package com.tritiumgaming.data.marketplace.bundle.repository

import android.util.Log
import com.tritiumgaming.data.marketplace.bundle.mapper.toDomain
import com.tritiumgaming.data.marketplace.bundle.source.remote.MarketBundleFirestoreDataSourceImpl
import com.tritiumgaming.shared.data.market.bundle.model.BundleQueryOptions
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.bundle.repository.MarketCatalogBundleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MarketCatalogBundleRepositoryImpl(
    private val firestoreDataSource: MarketBundleFirestoreDataSourceImpl,
    coroutineDispatcher: CoroutineDispatcher
): MarketCatalogBundleRepository {

    private var cache: List<MarketBundle> = emptyList()

    suspend fun fetchRemote(
        queryOptions: BundleQueryOptions = BundleQueryOptions()
    ): Result<List<MarketBundle>> {
        Log.d("MarketBundle", "Fetching remote bundles")

        val result = firestoreDataSource.fetch(queryOptions)
            .map { it.toDomain() }

        return result
    }

    override suspend fun synchronizeCache(): Result<List<MarketBundle>> {
        Log.d("MarketBundle", "Synchronizing bundles")

        val remoteResult = fetchRemote()
        remoteResult.exceptionOrNull()?.let { e ->
            Log.d("MarketBundle", "Error getting remote bundles: $e")
        }
        val remote = remoteResult.getOrDefault(emptyList())

        cache = remote

        Log.d("MarketBundle", "Fetched ${cache.size} bundles")
        cache.forEach {
            Log.d("MarketBundle", "Fetched $it")
        }

        return Result.success(cache)
    }

    override fun get(): Result<List<MarketBundle>> = Result.success(cache)

    init {
        CoroutineScope(coroutineDispatcher).launch {
            synchronizeCache()
        }
    }
}
