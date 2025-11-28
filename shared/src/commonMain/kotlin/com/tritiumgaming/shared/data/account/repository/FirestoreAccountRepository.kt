package com.tritiumgaming.shared.data.account.repository

import kotlinx.coroutines.flow.Flow

interface FirestoreAccountRepository {

    suspend fun addCredits(
        creditTransaction: com.tritiumgaming.shared.data.account.model.AccountCreditTransaction
    ): Result<com.tritiumgaming.shared.data.account.model.AccountCredits>

    suspend fun removeCredits(
        creditTransaction: com.tritiumgaming.shared.data.account.model.AccountCreditTransaction
    ): Result<com.tritiumgaming.shared.data.account.model.AccountCredits>

    fun observeCredits(): Flow<Result<com.tritiumgaming.shared.data.account.model.AccountCredits>>

    suspend fun setMarketplaceAgreementState(
        marketAgreement: com.tritiumgaming.shared.data.account.model.AccountMarketAgreement
    ): Result<com.tritiumgaming.shared.data.account.model.AccountMarketAgreement>

    suspend fun addUnlockedDocuments(
        unlockUUIDs: List<String>?,
        type: String
    ): Result<String>

    suspend fun fetchUnlockedPalettes(
        forceUpdate: Boolean = false
    ): Result<List<com.tritiumgaming.shared.data.account.model.AccountPalette>>

    suspend fun fetchUnlockedTypographies(
        forceUpdate: Boolean = false
    ): Result<List<com.tritiumgaming.shared.data.account.model.AccountTypography>>

    fun observeUnlockedPalettes(): Flow<Result<List<com.tritiumgaming.shared.data.account.model.AccountPalette>>>
    fun observeUnlockedTypographies(): Flow<Result<List<com.tritiumgaming.shared.data.account.model.AccountTypography>>>

    suspend fun addPurchasedDocument(
        orderID: String
    ): Result<String>

}