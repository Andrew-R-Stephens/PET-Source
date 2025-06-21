package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountunlockhistory

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository

class FetchUnlockedMarketplacePalettesUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke() {
        //repository.getUnlockedMarketplacePalettes(credits)
    }
}