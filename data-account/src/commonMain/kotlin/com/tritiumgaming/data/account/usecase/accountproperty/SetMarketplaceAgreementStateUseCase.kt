package com.tritiumgaming.data.account.usecase.accountproperty

import com.tritiumgaming.data.account.model.AccountMarketAgreement
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository

class SetMarketplaceAgreementStateUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(shown: Boolean) {
        repository.setMarketplaceAgreementState(
            AccountMarketAgreement(
                shown
            )
        )
    }
}