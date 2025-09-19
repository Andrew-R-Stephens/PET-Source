package com.tritiumgaming.shared.core.domain.user.usecase.accountproperty

import com.tritiumgaming.shared.core.domain.user.model.AccountMarketAgreement
import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository

class SetMarketplaceAgreementStateUseCase(
    private val repository: FirestoreAccountRepository
) {
    suspend operator fun invoke(shown: Boolean) {
        repository.setMarketplaceAgreementState(AccountMarketAgreement(shown))
    }
}