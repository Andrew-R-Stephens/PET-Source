package com.tritiumgaming.data.marketplace.metadata.source.remote

import com.tritiumgaming.data.marketplace.metadata.dto.MarketMetadataDto

interface MarketMetadataDataSource {

    suspend fun fetch(): Result<MarketMetadataDto>

}