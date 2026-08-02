package com.tritiumgaming.shared.data.account.usecase.accounttransaction

import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository

class PurchaseMarketplaceItemUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(
        type: MarketplaceExchangeMedium,
        itemId: String,
        itemType: String
    ): Result<Boolean> {
        return when (type) {
            MarketplaceExchangeMedium.CREDITS -> repository.purchaseItemWithCredits(
                itemId,
                itemType
            )
            MarketplaceExchangeMedium.LEGAL_TENDER -> {
                repository.purchaseItemWithLegalTender(itemId, itemType)
            }
        }
    }
}