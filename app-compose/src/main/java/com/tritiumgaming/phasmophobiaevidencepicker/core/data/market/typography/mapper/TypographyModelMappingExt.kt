package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.NetworkTypographyEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

fun List<NetworkTypographyEntity>.toExternal() = map { it ->
    MarketTypography(
        uuid = it.uuid,
        name = it.name,
        group = it.group,
        buyCredits = it.buyCredits,
        priority = it.priority,
        unlocked = it.unlocked
    )
}

fun Map<String, ExtendedTypography>.toExternal() = map { (uuid, typography) ->
    MarketTypography(
        uuid = uuid,
        typography = typography
    )
}

fun List<MarketTypography>.toPair() = associate { it ->
    it.toPair()
}

fun MarketTypographyDto.toExternal(): NetworkTypographyEntity {
    return NetworkTypographyEntity(
        uuid = uuid,
        name = name,
        group = group,
        buyCredits = buyCredits,
        priority = priority
    )
}
