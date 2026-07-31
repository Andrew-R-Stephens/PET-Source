package com.tritiumgaming.shared.data.account.usecase.accountcredit

import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository

class PurchaseMarketplaceItemUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(itemId: String, itemType: String): Result<Boolean> {
        return repository.purchaseItemWithCredits(itemId, itemType)
    }
}
