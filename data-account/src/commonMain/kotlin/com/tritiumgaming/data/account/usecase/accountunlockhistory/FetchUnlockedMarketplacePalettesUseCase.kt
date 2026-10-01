package com.tritiumgaming.data.account.usecase.accountunlockhistory

import com.tritiumgaming.data.account.repository.FirestoreAccountRepository

class FetchUnlockedMarketplacePalettesUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke() {
        //repository.getUnlockedMarketplacePalettes(credits)
    }
}