package com.tritiumgaming.shared.core.domain.market.user.repository

import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.shared.data.account.model.SignInOptions

interface CredentialsRepository {

    suspend fun getSignInCredentials(signInOptions: SignInOptions): Result<GetCustomCredentialOption>

    suspend fun signIn(credentialResponse: GetCredentialResponse): Result<Boolean>

    suspend fun signOut(): Result<Boolean>

    suspend fun deactivateAccount(): Result<Boolean>

}