package com.tritiumgaming.shared.data.account.usecase.accountcredit

import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAccountCreditsUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<AccountCredits>> {
        val result = repository.observeCredits()

        result.map { it.exceptionOrNull()?.printStackTrace() }

        return result
    }
}
