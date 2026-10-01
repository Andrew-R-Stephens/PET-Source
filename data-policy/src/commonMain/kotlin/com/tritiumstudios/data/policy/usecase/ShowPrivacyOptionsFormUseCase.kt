package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository

class ShowPrivacyOptionsFormUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(activity: Any, onFinished: () -> Unit = {}) =
        repository.showPrivacyOptionsForm(activity, onFinished)
}
