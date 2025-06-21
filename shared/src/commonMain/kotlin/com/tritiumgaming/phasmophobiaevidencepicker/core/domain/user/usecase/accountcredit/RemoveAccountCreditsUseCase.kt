package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCreditTransaction
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository

class RemoveAccountCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long) {
        repository.removeCredits(AccountCreditTransaction(credits))
    }
}