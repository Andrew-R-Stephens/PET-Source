package com.tritiumgaming.data.account.usecase.accountcredit

import com.tritiumgaming.data.account.repository.FirestoreAccountRepository

class RemoveAccountCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long) {
        /*repository.removeCredits(
            AccountCreditTransaction(
                credits
            )
        )*/
    }
}