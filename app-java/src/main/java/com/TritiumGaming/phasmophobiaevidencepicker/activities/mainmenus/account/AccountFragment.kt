package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.account

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.buildUserDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.utils.FormatterUtils.obfuscateEmailSpannable
import com.TritiumGaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import java.util.List

class AccountFragment : MainMenuFirebaseFragment() {
    private val showEmail = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.init()

        return inflater.inflate(R.layout.fragment_account_overview, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btn_account_login =
            view.findViewById<AppCompatButton>(R.id.settings_account_login_button)
        /*final AppCompatButton btn_account_logout =
                view.findViewById(R.id.settings_account_logout_button);
        final AppCompatButton btn_account_delete =
                view.findViewById(R.id.settings_account_delete_button);*/
        val btn_account_infoContainer =
            view.findViewById<ConstraintLayout>(R.id.constraintLayout_accountInformation)
        val btn_account_info =
            view.findViewById<AppCompatTextView>(R.id.settings_accountsettings_info)

        btn_account_login.setOnClickListener { v: View? ->
            manualSignInAccount()
            view.invalidate()
        }

        /*
        btn_account_logout.setOnClickListener(v -> {
            signOutAccount();

            view.invalidate();
        });

        btn_account_delete.setOnClickListener(v -> {
            deleteAccount();

            view.invalidate();
        });*/
        val accountEmail: String = currentFirebaseUser?.email ?: ""

        val displayedEmail =
            SpannableString(accountEmail)

        var obfuscatedEmailFinal: SpannableString? = null
        try {
            @ColorInt val obfuscationColor = getColorFromAttribute(requireContext(), R.attr.textColorBodyEmphasis)
            var obfuscatedEmailTemp = displayedEmail
            obfuscatedEmailTemp = obfuscateEmailSpannable(accountEmail, obfuscationColor)
            obfuscatedEmailFinal = obfuscatedEmailTemp
        } catch (e: IllegalStateException) { e.printStackTrace() }

        /*
        SpannableString finalEmail_obfuscated1 = finalEmail_obfuscated;
        btn_account_infoContainer.setOnClickListener(v -> {
            showEmail = !showEmail;

            if(showEmail) {
                btn_account_info.setText(email_displayed);
            } else {
                btn_account_info.setText(
                        finalEmail_obfuscated1 != null ? finalEmail_obfuscated1 : "?");
            }
        });
        */

        /*
        if(firebaseUser == null) {
            btn_account_login.setVisibility(View.VISIBLE);
            btn_account_logout.setVisibility(View.GONE);
            btn_account_infoContainer.setVisibility(View.GONE);
            btn_account_delete.setVisibility(View.GONE);
        } else {
            btn_account_login.setVisibility(View.GONE);
            btn_account_logout.setVisibility(View.VISIBLE);
            btn_account_infoContainer.setVisibility(View.VISIBLE);
            btn_account_delete.setVisibility(View.VISIBLE);
            btn_account_info.setText(email_displayed);

            if(!showEmail) {
                btn_account_info.setText(finalEmail_obfuscated1);
            }
        }

        btn_account_delete.setVisibility(View.GONE);*/
    }

    override fun initViewModels() {
        super.initViewModels()
    }

    private val userPurchaseHistory: Unit
        get() {
            var unlockHistoryCollection: CollectionReference? = null
            try {
                unlockHistoryCollection =
                    FirestoreUnlockHistory.unlockHistoryCollection
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (unlockHistoryCollection == null) {
                return
            }

            try {
                unlockHistoryCollection.get()
                    .addOnSuccessListener { task: QuerySnapshot ->
                        for (documentSnapshot in task.documents) {
                            val documentReference = documentSnapshot.reference

                            val uuid = documentReference.id
                            val customTheme =
                                globalPreferencesViewModel?.colorThemeControl?.getThemeByUUID(uuid)
                            customTheme?.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE)
                        }
                    }
                    .addOnFailureListener { e: Exception ->
                        Log.e("Firestore", "Could not retrieve unlock history!")
                        e.printStackTrace()
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    /**
     * refreshFragment
     */
    public override fun refreshFragment() {
        var ft = parentFragmentManager.beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this@AccountFragment).commitNow()
        ft = parentFragmentManager.beginTransaction()
        ft.attach(this@AccountFragment).commitNow()
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result: FirebaseAuthUIAuthenticationResult ->
        try {
            onSignInResultAccount(result)
        } catch (e: RuntimeException) {
            val message = "Login Error: " + e.message
            val toast = Toast.makeText(
                requireActivity(),
                message,
                com.google.android.material.R.integer.material_motion_duration_short_2
            )
            toast.show()
        }
    }

    /**
     *
     */
    override fun manualSignInAccount() {
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
                Toast.makeText(requireActivity(), "Internet not available.", Toast.LENGTH_SHORT)
                    .show()

                return
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            return
        }

        val providers = List.of(
            GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(signInIntent)
    }

    /**
     *
     */
    private fun onSignInResultAccount(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            val user = currentFirebaseUser

            if (user != null) {
                val message = "Welcome " + user.displayName
                val toast = Toast.makeText(
                    requireActivity(),
                    message,
                    com.google.android.material.R.integer.material_motion_duration_short_2
                )
                toast.show()

                refreshFragment()

                // Generate a Firestore document for the User with default data if needed
                try {
                    buildUserDocument()
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }

                userPurchaseHistory
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
                com.google.android.material.R.integer.material_motion_duration_short_2
            )
            toast.show()
        }
    }

    override fun onSignInAccountSuccess() {
    }

    override fun onSignOutAccountSuccess() {
    }

    override fun signOutAccount() {
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
                            com.google.android.material.R.integer.material_motion_duration_short_2
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

    override fun deleteAccount() {
        AuthUI.getInstance()
            .delete(requireContext())
            .addOnCompleteListener { task: Task<Void?>? ->
                val message = "Successfully removed account."
                val toast = Toast.makeText(
                    requireActivity(),
                    message,
                    com.google.android.material.R.integer.material_motion_duration_short_2
                )
                toast.show()
                refreshFragment()
            }
    }

    override fun onResume() {
        super.onResume()
    }
}
