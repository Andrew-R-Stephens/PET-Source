package com.tritiumgaming.shared.data.account.usecase.accountcredit


import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveAccountUnlockedPalettesUseCase (
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<List<AccountPalette>>> {
        val result = repository.observeUnlockedPalettes()

        return result
    }
}
