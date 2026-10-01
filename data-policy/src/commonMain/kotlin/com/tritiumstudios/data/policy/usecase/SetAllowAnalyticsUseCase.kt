package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository

class SetAllowAnalyticsUseCase(
    private val repository: PolicyRepository
) {
    suspend operator fun invoke(allow: Boolean) = repository.setAllowAnalytics(allow)
}
