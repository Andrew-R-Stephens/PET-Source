package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.dto.MarketMetadataDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.metadata.model.MarketMetadata

fun MarketMetadataDto.toDomain(): MarketMetadata =
    MarketMetadata(
        versionCode = versionCode
    )
