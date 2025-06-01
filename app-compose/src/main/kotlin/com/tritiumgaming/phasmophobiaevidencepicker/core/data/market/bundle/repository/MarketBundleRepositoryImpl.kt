package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource.BundleQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository.MarketBundleRemoteRepository

class MarketBundleRepositoryImpl(
    private val firestoreDataSource: MarketBundleFirestoreDataSource
): MarketBundleRemoteRepository {

    /**
     * Fetches remote bundles from the Firestore database.
     * @param bundleQueryOptions This is an optional parameter that allows you to specify filter
     * and ordering options. This can be left empty to fetch all bundles without any filtering.
     * Ordering defaults to [Query.Direction.DESCENDING].
     */
    override suspend fun fetchRemote(
        bundleQueryOptions: BundleQueryOptions
    ): List<MarketBundleDto> = firestoreDataSource.query(bundleQueryOptions)

}
