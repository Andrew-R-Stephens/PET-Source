package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.metadata.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.metadata.model.MarketMetadata

interface MarketMetadataRepository {

    suspend fun fetch(): MarketMetadata

}