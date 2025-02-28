package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.CredentialManagerService.SignInOptions.GOOGLE
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.CredentialManagerService.SignInOptions.ID_FALLBACK
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.CredentialManagerService.SignInOptions.ID_SILENT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface CredentialManagerService {

    fun signIn(
        activity: Activity,
        option: SignInOptions = GOOGLE,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {},
    ) {

        if(Firebase.auth.currentUser != null) {
            Log.d("User", "${Firebase.auth.currentUser?.email}")
            return
        }

        val googleIdOption = when(option) {
            GOOGLE -> signInWithGoogleOption(activity)
            ID_FALLBACK -> signInWithGoogleIDOptionWithFallback(activity)
            ID_SILENT -> signInWithGoogleIDOptionSilent(activity)
        }

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val result = CredentialManager.create(context = activity).getCredential(
                    request = request,
                    context = activity
                )

                val credential = result.credential

                if(credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val idTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val firebaseCredential = GoogleAuthProvider.getCredential(
                        idTokenCredential.idToken, null)

                    Firebase.auth.signInWithCredential(firebaseCredential)
                        .addOnSuccessListener {
                            Log.d(
                                "Firebase-Auth",
                                "Auth Credential process SUCCESS. RESULT: $result")
                            onSuccess()
                        }
                        .addOnFailureListener {
                            Log.d(
                                "Firebase-Auth",
                                "Auth Credential process FAILED. RESULT: $result")
                            onFailure()
                        }
                        .addOnCompleteListener {
                            Log.d(
                                "Firebase-Auth",
                                "Auth Credential process COMPLETE. RESULT: $result")
                            onComplete()
                        }

                }

            } catch (e: Exception) { e.printStackTrace() }

        }

    }

    fun signOut(
        activity: Activity,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        if(Firebase.auth.currentUser == null) {
            onFailure()
        } else {
            Firebase.auth.signOut()

            onSuccess()
        }

        onComplete()
    }

    /** For signing in with only google account options */
    private fun signInWithGoogleOption(activity: Activity): GetSignInWithGoogleOption {

        val googleIdOption = GetSignInWithGoogleOption
            .Builder(
                serverClientId = activity.getString(R.string.default_web_client_id))
            .build()

        return googleIdOption

    }

    /** For signing in with the bottom sheet. Attempts silent sign in. */
    @Throws(Exception::class)
    private fun signInWithGoogleIDOptionSilent(activity: Activity): GetGoogleIdOption {

        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setAutoSelectEnabled(true)
            .setNonce("")
            .setServerClientId(
                serverClientId = activity.getString(R.string.default_web_client_id)
            )
            .build()

    }

    /** For signing in with the bottom sheet UI options.
     * Attempts silent sign in before UI is shown. */
    private fun signInWithGoogleIDOptionWithFallback(activity: Activity): GetGoogleIdOption {

        /*
        attempt to find signed in users if true
        attempt 1 time, then state false and try again
        if first attempt with filter is successful, auto-select will auto sign-in user
        */

        val googleIdOption =
            try {
                signInWithGoogleIDOptionSilent(activity)
            } catch (e: NoCredentialException) {
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setNonce("")
                    .setServerClientId(
                        serverClientId = activity.getString(R.string.default_web_client_id)
                    )
                    .build()
            }

        return googleIdOption

    }

    enum class SignInOptions {
        GOOGLE,
        ID_FALLBACK,
        ID_SILENT
    }

}
