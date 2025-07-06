package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ObserveAccountUnlockedTypographiesUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<List<AccountTypography>>> {
        val result = repository.observeUnlockedTypographies()

        result.map { it.exceptionOrNull()?.printStackTrace() }

        return result
    }
}