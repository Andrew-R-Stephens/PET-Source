package com.tritiumgaming.data.account.usecase.accountcredit


import com.tritiumgaming.data.account.model.AccountPalette
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveAccountUnlockedPalettesUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<List<AccountPalette>>> {
        val result = repository.observeUnlockedPalettes()

        return result
    }
}
