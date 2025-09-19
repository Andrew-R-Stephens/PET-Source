package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase

import com.tritiumgaming.shared.core.domain.market.user.repository.CredentialsRepository

class SignOutAccountUseCase(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        val result = credentialsRepository.signOut()

        result.exceptionOrNull()?.printStackTrace()

        return result
    }
}
    