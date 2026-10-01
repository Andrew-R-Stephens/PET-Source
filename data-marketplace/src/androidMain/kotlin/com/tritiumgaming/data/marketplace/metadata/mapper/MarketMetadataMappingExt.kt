package com.tritiumgaming.data.marketplace.metadata.mapper

import com.tritiumgaming.data.marketplace.metadata.dto.MarketMetadataDto
import com.tritiumgaming.data.marketplace.metadata.model.MarketMetadata

fun MarketMetadataDto.toDomain(): MarketMetadata =
    MarketMetadata(
        versionCode = versionCode
    )
