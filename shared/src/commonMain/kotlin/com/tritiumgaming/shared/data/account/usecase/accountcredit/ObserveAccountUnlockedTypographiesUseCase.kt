package com.tritiumgaming.shared.data.account.usecase.accountcredit

import kotlinx.coroutines.flow.Flow


class ObserveAccountUnlockedTypographiesUseCase (
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(): Flow<Result<List<com.tritiumgaming.shared.data.account.model.AccountTypography>>> {
        val result = repository.observeUnlockedTypographies()

        result.collect { r -> r.exceptionOrNull()?.printStackTrace() }

        return result
    }
}