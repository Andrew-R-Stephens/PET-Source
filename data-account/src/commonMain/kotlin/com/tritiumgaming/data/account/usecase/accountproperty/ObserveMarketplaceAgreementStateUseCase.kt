package com.tritiumgaming.data.account.usecase.accountproperty

import com.tritiumgaming.data.account.model.AccountMarketAgreement
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveMarketplaceAgreementStateUseCase(
    private val repository: FirestoreAccountRepository
) {
    operator fun invoke(): Flow<Result<AccountMarketAgreement>> {
        return repository.observeMarketplaceAgreementState()
    }
}
