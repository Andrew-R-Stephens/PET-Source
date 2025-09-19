package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository

import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.CredentialsDataSourceImpl
import com.tritiumgaming.shared.core.domain.market.user.repository.CredentialsRepository
import com.tritiumgaming.shared.core.domain.user.model.SignInOptions

class CredentialsRepositoryImpl(
    private val credentialsDataSource: CredentialsDataSourceImpl
): CredentialsRepository {

    override suspend fun getSignInCredentials(
        signInOptions: SignInOptions
    ): Result<GetCustomCredentialOption> =
        credentialsDataSource.getSignInCredentials(signInOptions)

    override suspend fun signOut(): Result<Boolean> =
        credentialsDataSource.signOut()

    override suspend fun deactivateAccount(): Result<Boolean> =
        credentialsDataSource.deactivateAccount()

    override suspend fun signIn(
        credentialResponse: GetCredentialResponse
    ): Result<Boolean> = credentialsDataSource.signIn(credentialResponse)

}