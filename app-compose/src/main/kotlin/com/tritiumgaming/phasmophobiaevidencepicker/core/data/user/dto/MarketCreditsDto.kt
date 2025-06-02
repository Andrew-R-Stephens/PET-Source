package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketCredits

data class MarketCreditsDto(
    val earnedCredits: Long = 0L,
    val spentCredits: Long = 0L
)

fun MarketCreditsDto.toDomain(): MarketCredits =
    MarketCredits(
        earnedCredits = earnedCredits,
        spentCredits = spentCredits
    )

