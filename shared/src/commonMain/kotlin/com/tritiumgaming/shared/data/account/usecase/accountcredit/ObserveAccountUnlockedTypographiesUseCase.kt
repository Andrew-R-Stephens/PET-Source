package com.tritiumgaming.shared.data.account.usecase.accountcredit

import com.tritiumgaming.shared.data.account.model.AccountTypography
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow


class ObserveAccountUnlockedTypographiesUseCase (
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(): Flow<Result<List<AccountTypography>>> {
        val result = repository.observeUnlockedTypographies()

        result.collect { r -> r.exceptionOrNull()?.printStackTrace() }

        return result
    }
}