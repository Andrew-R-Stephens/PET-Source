package com.tritiumgaming.data.marketplace.metadata.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.tritiumgaming.data.marketplace.metadata.mapper.toDomain
import com.tritiumgaming.data.marketplace.metadata.source.remote.MarketMetadataFirestoreDataSource
import com.tritiumgaming.shared.data.market.metadata.model.MarketMetadata
import com.tritiumgaming.shared.data.market.metadata.repository.MarketMetadataRepository

class MarketMetadataRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val marketMetadataFirestoreDataSource: MarketMetadataFirestoreDataSource
): MarketMetadataRepository {

    override suspend fun fetch(): Result<MarketMetadata> {
        val result = marketMetadataFirestoreDataSource.fetch()

        return result.map { dto -> dto.toDomain() }
    }

}