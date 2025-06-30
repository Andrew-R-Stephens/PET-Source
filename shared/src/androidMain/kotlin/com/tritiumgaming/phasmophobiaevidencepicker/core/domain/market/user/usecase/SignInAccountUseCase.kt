package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.usecase

import androidx.credentials.GetCredentialResponse
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.repository.CredentialsRepository

class SignInAccountUseCase(
    val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(
        credentialResponse: GetCredentialResponse
    ): Result<Boolean> {
        val result = credentialsRepository.signIn(credentialResponse)

        result.exceptionOrNull()?.printStackTrace()

        return result
    }
}