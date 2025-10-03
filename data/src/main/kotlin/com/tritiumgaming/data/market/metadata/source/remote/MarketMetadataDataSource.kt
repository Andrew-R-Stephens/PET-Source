package com.tritiumgaming.data.market.metadata.source.remote

import com.tritiumgaming.data.market.metadata.dto.MarketMetadataDto

interface MarketMetadataDataSource {

    suspend fun fetch(): Result<MarketMetadataDto>

}