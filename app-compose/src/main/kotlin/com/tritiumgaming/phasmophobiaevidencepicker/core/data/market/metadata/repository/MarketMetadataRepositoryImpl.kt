package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.mapper.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.source.remote.MarketMetadataFirestoreDataSource
import com.tritiumgaming.shared.core.domain.market.metadata.model.MarketMetadata
import com.tritiumgaming.shared.core.domain.market.metadata.repository.MarketMetadataRepository

class MarketMetadataRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val marketMetadataFirestoreDataSource: MarketMetadataFirestoreDataSource
): MarketMetadataRepository {

    override suspend fun fetch(): Result<MarketMetadata> {
        val result = marketMetadataFirestoreDataSource.fetch()

        return result.map { dto -> dto.toDomain() }
    }

}