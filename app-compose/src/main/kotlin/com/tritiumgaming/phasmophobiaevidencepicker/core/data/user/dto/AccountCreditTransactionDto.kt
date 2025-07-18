package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCreditTransaction

data class AccountCreditTransactionDto(
    val credits: Long
)

fun AccountCreditTransaction.toNetwork(): AccountCreditTransactionDto =
    AccountCreditTransactionDto(
        credits = credits
    )

fun AccountCreditTransactionDto.toDomain(): AccountCreditTransaction = 
    AccountCreditTransaction(
        credits = credits
    )