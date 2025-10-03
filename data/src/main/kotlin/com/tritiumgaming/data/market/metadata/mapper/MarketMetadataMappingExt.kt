package com.tritiumgaming.data.market.metadata.mapper

import com.tritiumgaming.data.market.metadata.dto.MarketMetadataDto
import com.tritiumgaming.shared.core.domain.market.metadata.model.MarketMetadata

fun MarketMetadataDto.toDomain(): MarketMetadata =
    MarketMetadata(
        versionCode = versionCode
    )
