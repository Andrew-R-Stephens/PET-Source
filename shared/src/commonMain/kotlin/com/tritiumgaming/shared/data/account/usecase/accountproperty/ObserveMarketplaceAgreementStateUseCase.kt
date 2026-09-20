package com.tritiumgaming.shared.data.account.usecase.accountproperty

import com.tritiumgaming.shared.data.account.model.AccountMarketAgreement
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveMarketplaceAgreementStateUseCase(
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<AccountMarketAgreement>> {
        return repository.observeMarketplaceAgreementState()
    }
}
