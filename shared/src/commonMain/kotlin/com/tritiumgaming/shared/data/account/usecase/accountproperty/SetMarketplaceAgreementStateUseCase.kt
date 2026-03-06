package com.tritiumgaming.shared.data.account.usecase.accountproperty

import com.tritiumgaming.shared.data.account.model.AccountMarketAgreement
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository

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