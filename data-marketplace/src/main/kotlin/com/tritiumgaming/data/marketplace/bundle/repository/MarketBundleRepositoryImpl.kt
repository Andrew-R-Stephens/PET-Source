package com.tritiumgaming.data.marketplace.bundle.repository

import com.tritiumgaming.data.marketplace.bundle.mapper.toDomain
import com.tritiumgaming.data.marketplace.bundle.source.remote.MarketBundleFirestoreDataSourceImpl
import com.tritiumgaming.shared.core.domain.market.bundle.model.BundleQueryOptions
import com.tritiumgaming.shared.core.domain.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.core.domain.market.bundle.repository.MarketBundleRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarketBundleRepositoryImpl(
    private val firestoreDataSource: MarketBundleFirestoreDataSourceImpl
): MarketBundleRemoteRepository {

    /**
     * Fetches remote bundles from the Firestore database.
     * @param bundleQueryOptions This is an optional parameter that allows you to specify filter
     * and ordering options. This can be left empty to fetch all bundles without any filtering.
     * Ordering defaults to [Query.Direction.DESCENDING].
     */
    override suspend fun fetchRemote(
        bundleQueryOptions: BundleQueryOptions
    ): Result<List<MarketBundle>> = withContext(Dispatchers.IO) {
        firestoreDataSource.fetch(bundleQueryOptions).map { bs -> bs.map { it.toDomain() } }
    }

}
