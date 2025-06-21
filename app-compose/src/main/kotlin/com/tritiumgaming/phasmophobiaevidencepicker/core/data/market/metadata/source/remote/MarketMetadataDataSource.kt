package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.dto.MarketMetadataDto

interface MarketMetadataDataSource {

    suspend fun fetch(): Result<MarketMetadataDto>

}