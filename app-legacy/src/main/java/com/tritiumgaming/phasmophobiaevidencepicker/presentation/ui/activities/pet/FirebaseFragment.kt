package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet

abstract class FirebaseFragment : PETFragment {

    protected constructor() : super()

    protected constructor(layout: Int) : super(layout)

    /*private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result: FirebaseAuthUIAuthenticationResult ->
        try {
            onSignInResultAccount(result)
        } catch (runtimeException: RuntimeException) {
            val message = "${getString(R.string.alert_account_login_failure)}: ${runtimeException.message}"
            try {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
            }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }*/

    /*
    open fun manualSignInAccount() {
        signIn(
            requireActivity(),
            SignInCredentialManager.SignInOptions.GOOGLE
        )
    }

    open fun signOutAccount() {
        signOut()
    }

    open fun deleteAccount() {
        delete()
    }

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
                    getString(R.string.alert_internet_unavailable),
                    Toast.LENGTH_SHORT).show()

                return
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }

        val providers = listOf(
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
            currentFirebaseUser?.let { user ->
                val message = "${getString(R.string.alert_account_welcome)} ${user.displayName}"
                val toast = Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG)
                toast.show()

                try { buildUserDocument() }
                catch (e: Exception) { throw RuntimeException(e) }

                onSignInAccountSuccess()
            }
        } else {
            var message = "${getString(R.string.alert_error_generic)} ${getString(R.string.alert_account_data_failure)}"
            response?.error?.let { error ->
                message = "${getString(R.string.alert_error_generic)} ${ error.errorCode } ${error.message}"
            }
            Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
        }
    }

    open fun signOutAccount() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            return
        }

        try {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    try {
                        val message = getString(R.string.alert_account_logout_success)
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show() }
                    catch (e: IllegalStateException) { e.printStackTrace() }

                    onSignOutAccountSuccess()
                }
        }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    open fun deleteAccount() {
        AuthUI.getInstance()
            .delete(requireContext())
            .addOnSuccessListener {
                try {
                    val message = getString(R.string.alert_account_remove_success)
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
                }
                catch (e: IllegalStateException) { e.printStackTrace() }

                onDeleteAccountSuccess()
            }
            .addOnFailureListener {
                try {
                    val message = getString(R.string.alert_account_remove_failure)
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
                }
                catch (e: IllegalStateException) { e.printStackTrace() }

            }
    }


    protected abstract fun onSignInAccountSuccess()

    protected abstract fun onSignOutAccountSuccess()

    protected abstract fun onDeleteAccountSuccess()*/

    abstract override fun saveStates()
}
