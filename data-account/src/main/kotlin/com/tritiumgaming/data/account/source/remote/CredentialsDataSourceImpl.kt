package com.tritiumgaming.data.account.source.remote

import android.content.Context
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.core.domain.market.user.source.CredentialsDataSource
import com.tritiumgaming.shared.core.domain.user.model.SignInOptions
import kotlinx.coroutines.tasks.await

class CredentialsDataSourceImpl(
    context: Context
): CredentialsDataSource {

    val webClientId = context.getString(R.string.default_web_client_id)

    override fun getSignInCredentials(
        option: SignInOptions
    ): Result<GetCustomCredentialOption> {

        return when(option) {
            SignInOptions.GOOGLE -> signInWithGoogleOption()
            SignInOptions.ID_UI -> signInWithGoogleIDOptionSilent()
            SignInOptions.ID_SILENT -> signInWithGoogleIDOptionSilent()
            SignInOptions.ID_FALLBACK -> signInWithGoogleIDOptionSilentWithFallback()
            SignInOptions.SILENT -> signInSilent().exceptionOrNull()?.let {
                Result.failure(Exception("Silent Sign-In attempted and failed."))
            } ?: Result.failure(Exception("Silent Sign-In succeeded."))
        }

    }

    suspend fun signIn(
        credentialResponse: GetCredentialResponse
    ): Result<Boolean> {

        return when (val credential = credentialResponse.credential) {

            /*is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                // Share responseJson i.e. a GetCredentialResponse on your server to
                // validate and  authenticate
            }
            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
                // Use id and password to send to your server to validate
                // and authenticate
            }*/

            is CustomCredential -> {

                return if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)  {

                    try {
                        val idTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        val firebaseCredential =
                            GoogleAuthProvider.getCredential(idTokenCredential.idToken, null)

                        val result = Firebase.auth.signInWithCredential(firebaseCredential).await()

                        return if(result.user != null) {
                            Result.success(true)
                        } else {
                            Result.failure(Exception("User was not signed in."))
                        }

                    } catch (e: Exception) {
                        Result.failure(Exception("Failed to sign in.", e))
                    }

                } else {
                    Result.failure(Exception("Unexpected type of credential"))
                }

            }
            else -> Result.failure(Exception("Unexpected type of credential"))

        }

    }

    override fun signOut(): Result<Boolean> {

        return if(Firebase.auth.currentUser == null) {
            Result.failure(Exception("User was not signed in."))
        } else {
            Firebase.auth.signOut()

            Result.success(true)
        }
    }

    override suspend fun deactivateAccount(): Result<Boolean> {

        val currentUser = Firebase.auth.currentUser

        currentUser ?: return Result.failure(
            Exception("Account could not be deleted because a User was not signed in."))

        try {
            currentUser.delete().await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(Exception("Account could not be deleted.", e))
        }

    }

    private fun signInSilent(): Result<Boolean> {
        val user = Firebase.auth.currentUser

        return if(user != null) {
            Log.d("FirebaseAuth", "User currently signed in as ${user.email}. " +
                    "Validating account...")

            try {
                user.reload()
                    .addOnSuccessListener {
                        Log.d("FirebaseAuth", "User ${user.email} successfully validated.")

                        Result.success(true)
                    }
            } catch (e: FirebaseAuthInvalidUserException) {
                Log.e("FirebaseAuth", "ERROR: User ${user.email} validation failed. " +
                        "Forcing local sign-out.")

                Firebase.auth.signOut()

                if(Firebase.auth.currentUser == null) {
                    Log.d("FirebaseAuth", "User ${user.email} was signed out.")
                } else {
                    Log.e("FirebaseAuth", "ERROR: User ${user.email} could not be signed out.")
                }

            }

            Result.success(true)

        } else {
            Result.failure(Exception("User record not found. Silent sign-in unsuccessful."))
        }

        Result.success(true)
    }

    /** For signing in with only google account options */
    private fun signInWithGoogleOption(): Result<GetSignInWithGoogleOption> {

        if(Firebase.auth.currentUser != null) {
            return Result.failure(Exception("User was already signed in."))
        }

        val googleIdOption = GetSignInWithGoogleOption
            .Builder(serverClientId = webClientId)
            .build()

        return Result.success(googleIdOption)
    }

    /** For signing in with the bottom sheet. Attempts silent sign in. */
    @Throws(Exception::class)
    private fun signInWithGoogleIDOptionSilent(): Result<GetGoogleIdOption>  {

        if(Firebase.auth.currentUser != null) {
            return Result.failure(Exception("User was already signed in."))
        }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setAutoSelectEnabled(true)
            .setNonce("")
            .setServerClientId(
                serverClientId = webClientId
            )
            .build()

        return Result.success(googleIdOption)
    }

    /** For signing in with the bottom sheet. Attempts silent sign in. */
    @Throws(Exception::class)
    private fun signInWithGoogleIDOptionUI(
    ): Result<GetGoogleIdOption> {

        if(Firebase.auth.currentUser != null) { return Result.failure(
            Exception("User was already signed in.")) }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setNonce("")
            .setServerClientId(
                serverClientId = webClientId
            )
            .build()

        return Result.success(googleIdOption)

    }

    /** For signing in with the bottom sheet UI options.
     * Attempts silent sign in before UI is shown. */
    private fun signInWithGoogleIDOptionSilentWithFallback(): Result<GetGoogleIdOption>  {

        return try {
            signInWithGoogleIDOptionSilent()
        } catch (e: NoCredentialException) {
            Log.e("FirebaseAuth", "No credentials found. Falling back to UI.", e)

            signInWithGoogleIDOptionUI()
        }

    }

}