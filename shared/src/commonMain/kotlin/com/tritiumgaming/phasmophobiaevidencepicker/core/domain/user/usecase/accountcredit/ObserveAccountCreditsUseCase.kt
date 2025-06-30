package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCredits
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository
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