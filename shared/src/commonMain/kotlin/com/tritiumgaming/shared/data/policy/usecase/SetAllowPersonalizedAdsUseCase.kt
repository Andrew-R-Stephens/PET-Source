package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository

class SetAllowPersonalizedAdsUseCase(
    private val repository: PolicyRepository
) {
    suspend operator fun invoke(allow: Boolean) = repository.setAllowPersonalizedAds(allow)
}
