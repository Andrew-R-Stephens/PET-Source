package com.tritiumgaming.shared.data.account.usecase.accountcredit

class AddAccountCreditsUseCase(
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long): Result<com.tritiumgaming.shared.data.account.model.AccountCredits> {
        val result = repository.addCredits(
            com.tritiumgaming.shared.data.account.model.AccountCreditTransaction(
                credits
            )
        )
        return result
    }
}