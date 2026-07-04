package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository

class GatherAdsConsentUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(
        activity: Any,
        onFinished: (error: Any?) -> Unit
    ) {
        repository.gatherAdsConsent(activity, onFinished)
    }
}
