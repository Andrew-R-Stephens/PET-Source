package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketCreditTransaction

data class MarketCreditTransactionDto(
    val credits: Long
)

fun MarketCreditTransaction.toDomain(): MarketCreditTransactionDto =
    MarketCreditTransactionDto(
        credits = credits
    )
