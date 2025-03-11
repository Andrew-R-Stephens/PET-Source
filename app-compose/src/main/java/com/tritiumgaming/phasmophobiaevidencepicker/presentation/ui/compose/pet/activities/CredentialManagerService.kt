package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.SignInCredentialManager.SignInOptions.GOOGLE
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.SignInCredentialManager.SignInOptions.ID_FALLBACK
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.SignInCredentialManager.SignInOptions.ID_SILENT
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.SignInCredentialManager.SignInOptions.ID_UI
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.SignInCredentialManager.SignInOptions.SILENT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface CredentialManagerService:
    SignInCredentialManager, SignOutCredentialManager

interface SignOutCredentialManager {

    fun signOut(
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

}

interface SignInCredentialManager {

    fun signIn(
        activity: Activity? = null,
        option: SignInOptions = SILENT,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {},
    ) {

        when(option) {
            GOOGLE -> signInWithGoogleOption(activity, onSuccess, onFailure, onComplete)
            ID_UI -> signInWithGoogleIDOptionSilent(activity, onSuccess, onFailure, onComplete)
            ID_SILENT -> signInWithGoogleIDOptionSilent(activity, onSuccess, onFailure, onComplete)
            ID_FALLBACK -> signInWithGoogleIDOptionSilentWithFallback(activity, onSuccess, onFailure, onComplete)
            SILENT -> signInSilent(onSuccess, onFailure, onComplete)
        }

    }

    private fun signInSilent(
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        val user = Firebase.auth.currentUser

        if(user != null) {
            Log.d("FirebaseAuth", "User currently signed in as ${user.email}. " +
                    "Validating account...")

            try {
                user.reload().addOnSuccessListener {
                    Log.d("FirebaseAuth", "User ${user.email} successfully validated.")

                    onSuccess()
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

                e.printStackTrace()

                onFailure()
            }

        } else {
            Log.e("FirebaseAuth", "User record not found. Silent sign-in unsuccessful.")

            onFailure()
        }

        onComplete()
    }

    /** For signing in with only google account options */
    private fun signInWithGoogleOption(
        activity: Activity? = null,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        if(activity == null) { return }
        if(Firebase.auth.currentUser != null) { return }

        val googleIdOption = GetSignInWithGoogleOption
            .Builder(
                serverClientId = activity.getString(R.string.default_web_client_id))
            .build()

        finalizeAuthWithCredential(
            activity, googleIdOption, onSuccess, onFailure, onComplete
        )
    }

    /** For signing in with the bottom sheet. Attempts silent sign in. */
    @Throws(Exception::class)
    private fun signInWithGoogleIDOptionSilent(
        activity: Activity? = null,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        if(activity == null) { return }
        if(Firebase.auth.currentUser != null) { return }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setAutoSelectEnabled(true)
            .setNonce("")
            .setServerClientId(
                serverClientId = activity.getString(R.string.default_web_client_id)
            )
            .build()

        finalizeAuthWithCredential(
            activity, googleIdOption, onSuccess, onFailure, onComplete
        )
    }

    /** For signing in with the bottom sheet. Attempts silent sign in. */
    @Throws(Exception::class)
    private fun signInWithGoogleIDOptionUI(
        activity: Activity? = null,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        if(activity == null) { return }
        if(Firebase.auth.currentUser != null) { return }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setNonce("")
            .setServerClientId(
                serverClientId = activity.getString(R.string.default_web_client_id)
            )
            .build()

        finalizeAuthWithCredential(
            activity, googleIdOption, onSuccess, onFailure, onComplete
        )
    }

    /** For signing in with the bottom sheet UI options.
     * Attempts silent sign in before UI is shown. */
    private fun signInWithGoogleIDOptionSilentWithFallback(
        activity: Activity? = null,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        if(activity == null) { return }
        if(Firebase.auth.currentUser != null) { return }

        try {
            signInWithGoogleIDOptionSilent(activity, onSuccess, onFailure, onComplete)
        } catch (e: NoCredentialException) {
            signInWithGoogleIDOptionUI(activity, onSuccess, onFailure, onComplete)
        }

    }

    private fun finalizeAuthWithCredential(
        activity: Activity,
        credentialOption: CredentialOption,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()

        val credentialManager = CredentialManager.create(context = activity)

        fun handleSignIn(credentialResponse: GetCredentialResponse) {

            when (val credential = credentialResponse.credential) {

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
                    // If you are also using any external sign-in libraries, parse them
                    // here with the utility functions provided.
                    if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)  {

                        try {
                            val idTokenCredential =
                                GoogleIdTokenCredential.createFrom(credential.data)
                            val firebaseCredential = GoogleAuthProvider.getCredential(
                                idTokenCredential.idToken, null)

                            Firebase.auth.signInWithCredential(firebaseCredential)
                                .addOnSuccessListener {
                                    Log.d(
                                        "Firebase-Auth",
                                        "Auth Credential process SUCCESS. RESULT: " +
                                                "${Firebase.auth.currentUser?.displayName}")
                                    onSuccess()
                                }
                                .addOnFailureListener {
                                    Log.e(
                                        "Firebase-Auth",
                                        "Auth Credential process FAILED.")
                                    onFailure()
                                }
                                .addOnCompleteListener {
                                    Log.d(
                                        "Firebase-Auth",
                                        "Auth Credential process COMPLETE.")
                                    onComplete()
                                }
                        } catch (e: Exception) { e.printStackTrace() }
                    } else {
                        Log.e("FirebaseAuth", "Unexpected type of credential")
                    }
                } else -> {
                    Log.e("FirebaseAuth", "Unexpected type of credential")
                }

            }

        }

        CoroutineScope(Dispatchers.IO).launch {
            val credentialResponse = credentialManager.getCredential(
                request = request,
                context = activity
            )

            handleSignIn(credentialResponse)
        }

    }

    enum class SignInOptions {
        GOOGLE,
        ID_UI,
        ID_SILENT,
        ID_FALLBACK,
        SILENT
    }

}
