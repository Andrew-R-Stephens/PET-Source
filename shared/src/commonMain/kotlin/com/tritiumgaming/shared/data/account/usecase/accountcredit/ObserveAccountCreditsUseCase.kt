package com.tritiumgaming.shared.data.account.usecase.accountcredit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAccountCreditsUseCase (
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<com.tritiumgaming.shared.data.account.model.AccountCredits>> {
        val result = repository.observeCredits()

        result.map { it.exceptionOrNull()?.printStackTrace() }

        return result
    }
}
