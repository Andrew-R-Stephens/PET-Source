package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy

class ApplyPolicyUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(policy: Policy) = repository.applyPolicy(policy)
}
