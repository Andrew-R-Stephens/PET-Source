package com.tritiumgaming.shared.core.domain.market.metadata.repository

import com.tritiumgaming.shared.core.domain.market.metadata.model.MarketMetadata

interface MarketMetadataRepository {

    suspend fun fetch(): Result<MarketMetadata>

}