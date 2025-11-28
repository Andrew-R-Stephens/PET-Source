package com.tritiumgaming.shared.data.market.metadata.repository

import com.tritiumgaming.shared.data.market.metadata.model.MarketMetadata

interface MarketMetadataRepository {

    suspend fun fetch(): Result<MarketMetadata>

}