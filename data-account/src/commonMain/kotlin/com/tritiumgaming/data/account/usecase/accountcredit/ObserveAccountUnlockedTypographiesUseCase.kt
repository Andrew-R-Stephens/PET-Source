package com.tritiumgaming.data.account.usecase.accountcredit

import com.tritiumgaming.data.account.model.AccountTypography
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow


class ObserveAccountUnlockedTypographiesUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<List<AccountTypography>>> {
        val result = repository.observeUnlockedTypographies()

        return result
    }
}