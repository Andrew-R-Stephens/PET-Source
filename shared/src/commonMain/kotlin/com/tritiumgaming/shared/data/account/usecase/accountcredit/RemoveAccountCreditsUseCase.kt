package com.tritiumgaming.shared.data.account.usecase.accountcredit

class RemoveAccountCreditsUseCase(
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long) {
        repository.removeCredits(
            com.tritiumgaming.shared.data.account.model.AccountCreditTransaction(
                credits
            )
        )
    }
}