package com.tritiumgaming.shared.data.account.usecase.accountproperty

class SetMarketplaceAgreementStateUseCase(
    private val repository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(shown: Boolean) {
        repository.setMarketplaceAgreementState(
            com.tritiumgaming.shared.data.account.model.AccountMarketAgreement(
                shown
            )
        )
    }
}