package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository
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