package com.tritiumgaming.data.marketplace.metadata.repository

import com.tritiumgaming.data.marketplace.metadata.model.MarketMetadata

interface MarketMetadataRepository {

    suspend fun fetch(): Result<MarketMetadata>

}