package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.FeatureAvailability
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

data class MarketTypography (
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = true,
    val typography: ExtendedTypography? = null
)

fun List<MarketTypography>.toAccountMarketTypography() = map {
    it.toAccountMarketTypography()
}

fun MarketTypography.toAccountMarketTypography(): AccountMarketTypography {
    return AccountMarketTypography(
        uuid = uuid,
        name = name,
        group = group,
        buyCredits = buyCredits,
        priority = priority,
        unlocked = unlocked,
        typography = typography
    )
}