package com.tritiumgaming.data.account.usecase.accountcredit

import com.tritiumgaming.data.account.model.AccountCreditTransaction
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository

class AddAccountCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long): Result<Boolean> {
        val result = repository.addCredits(
            AccountCreditTransaction(
                credits
            )
        )
        return result
    }
}