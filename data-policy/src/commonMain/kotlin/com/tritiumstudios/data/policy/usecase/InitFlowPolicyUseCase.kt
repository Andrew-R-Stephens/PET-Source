package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository

class InitFlowPolicyUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}
