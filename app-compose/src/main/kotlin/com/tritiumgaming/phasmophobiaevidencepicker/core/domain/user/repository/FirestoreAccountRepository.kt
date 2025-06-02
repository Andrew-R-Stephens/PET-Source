package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketAgreement
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketCredits

interface FirestoreAccountRepository {

    suspend fun addCredits(marketCredits: MarketCredits): Result<MarketCredits>
    suspend fun removeCredits(marketCredits: MarketCredits): Result<MarketCredits>

    suspend fun setMarketplaceAgreementState(marketAgreement: MarketAgreement): Result<MarketAgreement>

    //suspend fun addUnlockDocument(unlockUUID: String?, type: String): Result<String>
    suspend fun addUnlockedDocuments (unlockUUIDs: ArrayList<String>?, type: String): Result<String>

    suspend fun addPurchaseDocument(orderID: String): Result<String>

}