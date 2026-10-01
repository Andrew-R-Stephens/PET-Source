package com.tritiumgaming.data.account.dto

import com.tritiumgaming.data.account.model.AccountCreditTransaction

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