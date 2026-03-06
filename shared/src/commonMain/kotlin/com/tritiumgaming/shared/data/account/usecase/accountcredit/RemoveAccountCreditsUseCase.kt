package com.tritiumgaming.shared.data.account.usecase.accountcredit

import com.tritiumgaming.shared.data.account.model.AccountCreditTransaction
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository

class RemoveAccountCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long) {
        repository.removeCredits(
            AccountCreditTransaction(
                credits
            )
        )
    }
}