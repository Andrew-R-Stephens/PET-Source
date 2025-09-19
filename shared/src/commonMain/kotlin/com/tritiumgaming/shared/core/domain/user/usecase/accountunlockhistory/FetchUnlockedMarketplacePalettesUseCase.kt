package com.tritiumgaming.shared.core.domain.user.usecase.accountunlockhistory

import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository

class FetchUnlockedMarketplacePalettesUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke() {
        //repository.getUnlockedMarketplacePalettes(credits)
    }
}