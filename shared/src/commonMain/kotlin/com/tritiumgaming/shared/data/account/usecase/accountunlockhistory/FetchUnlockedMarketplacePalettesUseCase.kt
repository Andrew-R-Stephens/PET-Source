package com.tritiumgaming.shared.data.account.usecase.accountunlockhistory

class FetchUnlockedMarketplacePalettesUseCase(
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke() {
        //repository.getUnlockedMarketplacePalettes(credits)
    }
}