package com.tritiumgaming.data.account.usecase.accountcredit

import com.tritiumgaming.data.account.model.AccountCredits
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveAccountCreditsUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<AccountCredits>> = repository.observeCredits()

}
