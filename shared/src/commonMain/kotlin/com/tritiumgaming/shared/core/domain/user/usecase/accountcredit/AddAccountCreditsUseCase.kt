package com.tritiumgaming.shared.core.domain.user.usecase.accountcredit

import com.tritiumgaming.shared.core.domain.user.model.AccountCreditTransaction
import com.tritiumgaming.shared.core.domain.user.model.AccountCredits
import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository

class AddAccountCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long): Result<AccountCredits> {
        val result = repository.addCredits(AccountCreditTransaction(credits))
        return result
    }
}