package com.TritiumGaming.phasmophobiaevidencepicker.activities.pet

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.TritiumGaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.List

abstract class FirebaseFragment : PETFragment {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result: FirebaseAuthUIAuthenticationResult ->
        try {
            onSignInResultAccount(result)
        } catch (runtimeException: RuntimeException) {
            val message = "Login Error: " + runtimeException.message
            try {
                Toast.makeText(
                    requireActivity(),
                    message,
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    protected constructor() : super()

    protected constructor(layout: Int) : super(layout)

    protected abstract fun onSignInAccountSuccess()

    protected abstract fun onSignOutAccountSuccess()

    protected abstract fun onDeleteAccountSuccess()

    open fun manualSignInAccount() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d("ManuLogin", "User not null!")
            return
        }
        Log.d("ManuLogin", "Continuing to sign-in.")

        try {
            if (!isNetworkAvailable(
                    requireContext(),
                    globalPreferencesViewModel!!.networkPreference
                )
            ) {
                Toast.makeText(
                    requireActivity(),
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                    .show()

                return
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }


        val providers = List.of(
            GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .setTosAndPrivacyPolicyUrls(
                getString(R.string.preference_termsofservice_link),
                getString(R.string.preference_privacypolicy_link)
            )
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResultAccount(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse

        // Successfully signed in
        if (result.resultCode == Activity.RESULT_OK) {
            val user: FirebaseUser?
            try {
                user = currentFirebaseUser
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            if (user != null) {
                val message = "Welcome " + user.displayName
                val toast = Toast.makeText(
                    requireActivity(),
                    message,
                    Toast.LENGTH_LONG
                )
                toast.show()

                onSignInAccountSuccess()
            }
        } else {
            var message = "ERROR: (Error data could not be acquired)."
            if (response != null) {
                val error = response.error
                if (error != null) {
                    message = "ERROR " + error.errorCode + ": " + error.message
                }
            }

            val toast = Toast.makeText(
                requireActivity(),
                message,
                Toast.LENGTH_LONG
            )
            toast.show()
        }
    }

    open fun signOutAccount() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            return
        }

        try {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener { task: Task<Void?>? ->
                    val message = "User signed out"
                    try {
                        Toast.makeText(
                            requireActivity(),
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                    onSignOutAccountSuccess()
                }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    open fun deleteAccount() {
        AuthUI.getInstance()
            .delete(requireContext())
            .addOnCompleteListener { task: Task<Void?>? ->
                val message = "Successfully removed account."
                try {
                    Toast.makeText(
                        requireActivity(),
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
                refreshFragment()
            }
    }

    abstract override fun saveStates()
}
