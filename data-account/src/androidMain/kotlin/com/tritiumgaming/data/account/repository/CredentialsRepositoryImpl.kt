package com.tritiumgaming.data.account.repository

import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.core.common.credentials.SignInOptions
import com.tritiumgaming.core.domain.market.user.repository.CredentialsRepository
import com.tritiumgaming.data.account.source.remote.CredentialsDataSourceImpl

class CredentialsRepositoryImpl(
    private val credentialsDataSource: CredentialsDataSourceImpl
): CredentialsRepository {

    override suspend fun getSignInCredentials(
        signInOptions: SignInOptions
    ): Result<GetCustomCredentialOption> =
        credentialsDataSource.getSignInCredentials(signInOptions)

    override suspend fun signIn(credentialResponse: GetCredentialResponse): Result<Boolean> =
        credentialsDataSource.signIn(credentialResponse)

    override suspend fun signOut(): Result<Boolean> =
        credentialsDataSource.signOut()

    override suspend fun deactivateAccount(): Result<Boolean> =
        credentialsDataSource.deactivateAccount()

}