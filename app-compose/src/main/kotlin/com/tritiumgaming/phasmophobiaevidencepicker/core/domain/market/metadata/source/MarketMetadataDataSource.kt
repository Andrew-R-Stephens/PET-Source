package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.metadata.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.dto.MarketMetadataDto

interface MarketMetadataDataSource {

    suspend fun fetch(): Result<MarketMetadataDto>

}