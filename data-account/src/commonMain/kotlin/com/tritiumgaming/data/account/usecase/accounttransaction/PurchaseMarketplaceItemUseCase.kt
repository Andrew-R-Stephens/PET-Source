package com.tritiumgaming.data.account.usecase.accounttransaction

import com.tritiumgaming.data.account.model.MarketplaceExchangeMedium
import com.tritiumgaming.data.account.model.MarketplaceExchangeMedium.CREDITS
import com.tritiumgaming.data.account.model.MarketplaceExchangeMedium.LEGAL_TENDER
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository

class PurchaseMarketplaceItemUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(
        type: MarketplaceExchangeMedium,
        itemId: String,
        itemType: String
    ): Result<Boolean> {
        return when (type) {
            CREDITS -> repository.purchaseItemWithCredits(
                itemId,
                itemType
            )
            LEGAL_TENDER -> {
                repository.purchaseItemWithLegalTender(itemId, itemType)
            }
        }
    }
}