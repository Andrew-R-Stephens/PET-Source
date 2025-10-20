package com.tritiumgaming.data.marketplace.typography.dto

import com.tritiumgaming.shared.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType

data class MarketTypographyDto(
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val typography: TypographyType? = null
)

fun MarketTypographyDto.toDomain() = MarketTypography(
    uuid = uuid,
    name = name,
    group = group,
    buyCredits = buyCredits,
    priority = priority,
    unlocked = unlocked,
    typography = typography
)

fun Map<String, TypographyType>.toLocal() = map { (uuid, typography) ->
    MarketTypographyDto(
        uuid = uuid,
        typography = typography,
        unlocked = true
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

