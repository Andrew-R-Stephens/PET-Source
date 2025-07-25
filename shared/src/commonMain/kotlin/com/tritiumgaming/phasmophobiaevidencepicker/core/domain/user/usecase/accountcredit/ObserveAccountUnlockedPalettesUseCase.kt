package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit


import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAccountUnlockedPalettesUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<List<AccountPalette>>> {
        val result = repository.observeUnlockedPalettes()

        result.map { it.exceptionOrNull()?.printStackTrace() }

        return result
    }
}
