package com.tritiumgaming.shared.data.account.usecase.accountcredit

import com.tritiumgaming.shared.data.account.model.AccountCreditTransaction
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository

class AddAccountCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long): Result<AccountCredits> {
        val result = repository.addCredits(
            AccountCreditTransaction(
                credits
            )
        )
        return result
    }
}