package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository

interface FirestoreUserRepository {

    suspend fun addCredits(creditAmount: Long): Result<String>
    suspend fun removeCredits(creditAmount: Long): Result<String>

    suspend fun setMarketplaceAgreementState(shown: Boolean): Result<String>

    suspend fun addUnlockDocument(unlockUUID: String?, type: String): Result<String>
    suspend fun addUnlockedDocuments (unlockUUIDs: ArrayList<String>?, type: String): Result<String>

    suspend fun addPurchaseDocument(orderID: String): Result<String>

}