package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

fun MarketTypography.toPair(): Pair<String, MarketTypography> {
    return Pair(uuid, this)
}

fun MarketTypographyDto.toDomain() = MarketTypography(
    uuid = uuid,
    name = name,
    group = group,
    buyCredits = buyCredits,
    priority = priority,
    unlocked = unlocked,
    typography = typography
)

fun Map<String, ExtendedTypography>.toLocal() = map { (uuid, typography) ->
    MarketTypographyDto(
        uuid = uuid,
        typography = typography
    )
}

fun Map<String, ExtendedTypography>.toDomain() = map { (uuid, typography) ->
    MarketTypography(
        uuid = uuid,
        typography = typography
    )
}

fun List<MarketTypographyDto>.toDomain() = map { dto ->
    MarketTypography(
        uuid = dto.uuid,
        name = dto.name,
        group = dto.group,
        buyCredits = dto.buyCredits,
        priority = dto.priority,
        unlocked = dto.unlocked,
        typography = dto.typography
    )
}

fun List<MarketTypography>.toPair() = associate { it ->
    it.toPair()
}
