package com.tritiumgaming.shared.core.domain.user.usecase.accountcredit


import com.tritiumgaming.shared.core.domain.user.model.AccountPalette
import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveAccountUnlockedPalettesUseCase (
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(): Flow<Result<List<AccountPalette>>> {
        val result = repository.observeUnlockedPalettes()

        result.collect { r -> r.exceptionOrNull()?.printStackTrace() }

        return result
    }
}
