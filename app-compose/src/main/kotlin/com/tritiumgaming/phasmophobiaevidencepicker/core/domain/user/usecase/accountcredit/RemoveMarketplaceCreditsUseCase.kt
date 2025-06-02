package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository

class RemoveMarketplaceCreditsUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(credits: Long) {
        repository.removeCredits(credits)
    }
}