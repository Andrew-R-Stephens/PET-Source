package com.tritiumgaming.data.palette.dto

import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType
import com.tritiumgaming.shared.data.market.typography.mappers.asUuid
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography

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

fun List<TypographyType>.toLocal() = map { typographyType ->
    MarketTypographyDto(
        uuid = typographyType.asUuid(),
        typography = typographyType,
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

