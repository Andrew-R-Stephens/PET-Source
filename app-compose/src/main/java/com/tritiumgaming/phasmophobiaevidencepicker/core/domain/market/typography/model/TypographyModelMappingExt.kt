package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.NetworkTypographyEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

fun List<NetworkTypographyEntity>.toExternal() = map { it ->
    TypographyEntity(
        uuid = it.uuid,
        name = it.name,
        group = it.group,
        buyCredits = it.buyCredits,
        priority = it.priority,
        unlocked = it.unlocked
    )
}

fun Map<String, ExtendedTypography>.toExternal() = map { (uuid, typography) ->
    TypographyEntity(
        uuid = uuid,
        typography = typography
    )
}

fun List<TypographyEntity>.toPair() = associate { it ->
    it.toPair()
}