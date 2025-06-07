package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCreditTransaction
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketAgreement
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCredits
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountTypography

interface FirestoreAccountRepository {

    suspend fun addCredits(creditTransaction: AccountCreditTransaction): Result<AccountCredits>
    suspend fun removeCredits(creditTransaction: AccountCreditTransaction): Result<AccountCredits>

    suspend fun setMarketplaceAgreementState(marketAgreement: AccountMarketAgreement): Result<AccountMarketAgreement>

    suspend fun addUnlockedDocuments(unlockUUIDs: ArrayList<String>?, type: String): Result<String>
    suspend fun fetchUnlockedPalettes(): Result<List<AccountPalette>>
    suspend fun fetchUnlockedTypographies(): Result<List<AccountTypography>>

    suspend fun addPurchasedDocument(orderID: String): Result<String>

}