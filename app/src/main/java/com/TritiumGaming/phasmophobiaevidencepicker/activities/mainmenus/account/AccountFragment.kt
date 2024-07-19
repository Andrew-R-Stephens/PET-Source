package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.account

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.buildUserDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.TritiumGaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.DeleteAccountDialog
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.LogoutDialog
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot

class AccountFragment : MainMenuFirebaseFragment() {

    private var confirmationDialog: ComposeView? = null

    private val userPurchaseHistory: Unit
        get() {
            var unlockHistoryCollection: CollectionReference? = null
            try { unlockHistoryCollection = FirestoreUnlockHistory.unlockHistoryCollection }
            catch (e: Exception) { e.printStackTrace() }

            if (unlockHistoryCollection == null) { return }

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
            } catch (e: Exception) { e.printStackTrace() }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.init()

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val backButton: PETImageButton? = view.findViewById(R.id.button_left)

        val accountDetailsList: View? = view.findViewById(R.id.scrollview_account_details)

        val loginButton: SignInButton? = view.findViewById(R.id.settings_account_login_button)
        val logoutButton: AppCompatButton? = view.findViewById(R.id.settings_account_logout_button)
        val deleteButton: AppCompatButton? = view.findViewById(R.id.settings_account_delete_button)

        val accountNameTextView: AppCompatTextView? = view.findViewById(R.id.account_name_in)
        val accountEmailTextView: AppCompatTextView? = view.findViewById(R.id.account_email_in)

        confirmationDialog = view.findViewById(R.id.confirmationDialog)

        backButton?.setOnClickListener { v: View? ->
            v?.let {
                try { findNavController(v).popBackStack() }
                catch (e: IllegalStateException) { e.printStackTrace() } }
        }

        loginButton?.setOnClickListener {
            manualSignInAccount()
        }

        logoutButton?.setOnClickListener{
            confirmationDialog?.setContent {
                LogoutDialog(
                    onConfirm = { signOutAccount() },
                    onCancel = { confirmationDialog?.visibility = GONE }
                )
            }
            confirmationDialog?.visibility = VISIBLE
        }

        deleteButton?.setOnClickListener{
            confirmationDialog?.setContent {
                DeleteAccountDialog(
                    onConfirm = { deleteAccount() },
                    onCancel = { confirmationDialog?.visibility = GONE }
                )
            }
            confirmationDialog?.visibility = VISIBLE
        }

        currentFirebaseUser?.let { user ->
            accountNameTextView?.text = user.displayName
            accountEmailTextView?.text = user.email
            accountDetailsList?.visibility = VISIBLE
        } ?: {
            accountDetailsList?.visibility = GONE
        }
    }

    public override fun refreshFragment() {
        var ft = parentFragmentManager.beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this@AccountFragment).commitNow()
        ft = parentFragmentManager.beginTransaction()
        ft.attach(this@AccountFragment).commitNow()
    }


    override fun onSignInAccountSuccess() {
        // Successfully signed in
        val user = currentFirebaseUser

        if (user != null) {
            val message = "${getString(R.string.alert_account_welcome)} ${user.displayName}"
            Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()

            refreshFragment()

            // Generate a Firestore document for the User with default data if needed
            try {
                buildUserDocument()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            userPurchaseHistory
        }
    }

    override fun onSignOutAccountSuccess() {
        confirmationDialog?.visibility = GONE
        refreshFragment()

    }

    override fun onDeleteAccountSuccess() {
        confirmationDialog?.visibility = GONE
        refreshFragment()
    }

}
