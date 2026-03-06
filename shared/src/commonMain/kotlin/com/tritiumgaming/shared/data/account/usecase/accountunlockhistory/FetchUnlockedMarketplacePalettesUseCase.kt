package com.tritiumgaming.shared.data.account.usecase.accountunlockhistory

import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository

class FetchUnlockedMarketplacePalettesUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke() {
        //repository.getUnlockedMarketplacePalettes(credits)
    }
}