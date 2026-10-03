package com.tritiumgaming.core.domain.market.user.repository

import androidx.credentials.GetCredentialResponse
import com.tritiumgaming.core.common.credentials.SignInOptions
import androidx.credentials.GetCustomCredentialOption

interface CredentialsRepository {

    suspend fun getSignInCredentials(signInOptions: SignInOptions): Result<GetCustomCredentialOption>

    suspend fun signIn(credentialResponse: GetCredentialResponse): Result<Boolean>

    suspend fun signOut(): Result<Boolean>

    suspend fun deactivateAccount(): Result<Boolean>

}
