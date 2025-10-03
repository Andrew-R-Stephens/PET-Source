package com.tritiumgaming.data.user.dto

import com.tritiumgaming.shared.core.domain.user.model.AccountCredits


data class AccountCreditsDto(
    val earnedCredits: Long = 0L,
    val spentCredits: Long = 0L
)

fun AccountCreditsDto.toDomain(): AccountCredits =
    AccountCredits(
        earnedCredits = earnedCredits,
        spentCredits = spentCredits
    )

fun AccountCredits.toNetwork(): AccountCreditsDto =
    AccountCreditsDto(
        earnedCredits = earnedCredits,
        spentCredits = spentCredits
    )

