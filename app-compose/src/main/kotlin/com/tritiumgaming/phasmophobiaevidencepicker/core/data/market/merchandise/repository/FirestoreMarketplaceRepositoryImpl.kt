package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.repository

import com.google.firebase.firestore.Query
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketMerchandiseFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto

class FirestoreMarketplaceRepositoryImpl(
    private val marketFirestoreDataSource: MarketFirestoreDataSource,
    private val merchandiseFirestoreDataSource: MarketMerchandiseFirestoreDataSource,
    private val paletteFirestoreDataSource: MarketPaletteFirestoreDataSource,
    private val bundleFirestoreDataSource: MarketBundleFirestoreDataSource,
) {

    suspend fun fetchAllPalettes(): List<MarketPaletteDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        return paletteFirestoreDataSource.fetchAll(merchandiseDocument)

    }

    suspend fun fetchPalettesWhere(
        filterField: String? = null,
        value: String? = null,
        orderField: String? = null,
        order: Query.Direction? = Query.Direction.DESCENDING
    ): List<MarketPaletteDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        return paletteFirestoreDataSource.fetchWhere(
            merchandiseDocument, filterField, value, orderField, order)

    }

    suspend fun fetchAllBundles(): List<MarketBundleDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        bundleFirestoreDataSource.fetchBundles(merchandiseDocument)

        return listOf()

    }

    suspend fun fetchBundlesWhere(
        filterField: String? = null,
        value: String? = null,
        orderField: String? = null,
        order: Query.Direction? = Query.Direction.DESCENDING
    ): List<MarketBundleDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        return bundleFirestoreDataSource.fetchWhere(
            merchandiseDocument, filterField, value, orderField, order)

    }

}