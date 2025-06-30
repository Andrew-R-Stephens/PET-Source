package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.repository.CredentialsRepository

class DeactivateAccountUseCase(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        val result = credentialsRepository.deactivateAccount()

        result.exceptionOrNull()?.printStackTrace()

        return result
    }
}
