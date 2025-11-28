package com.tritiumgaming.shared.data.account.usecase.accountcredit


import kotlinx.coroutines.flow.Flow

class ObserveAccountUnlockedPalettesUseCase (
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(): Flow<Result<List<com.tritiumgaming.shared.data.account.model.AccountPalette>>> {
        val result = repository.observeUnlockedPalettes()

        result.collect { r -> r.exceptionOrNull()?.printStackTrace() }

        return result
    }
}
