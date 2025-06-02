package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.MarketCreditsDto

data class MarketCredits(
    val earnedCredits: Long,
    val spentCredits: Long
)

fun MarketCredits.toNetwork(): MarketCreditsDto =
    MarketCreditsDto(
        earnedCredits = earnedCredits,
        spentCredits = spentCredits
    )
