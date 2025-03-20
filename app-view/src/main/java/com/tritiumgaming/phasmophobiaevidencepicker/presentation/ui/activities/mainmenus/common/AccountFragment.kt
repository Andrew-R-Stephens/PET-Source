package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.common

import android.annotation.SuppressLint
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
import com.google.android.gms.common.SignInButton
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.SignInCredentialManager
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.DeleteAccountDialog
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.LogoutDialog
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.views.PETImageButton

class AccountFragment : MainMenuFragment() {

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            signIn(
                requireActivity(),
                SignInCredentialManager.SignInOptions.GOOGLE,
                onSuccess = {

                    createUserDocument(

                        onSuccess = {
                            try {
                                currentFirebaseUser?.let { user ->
                                    val message =
                                        "${requireActivity().getString(R.string.alert_account_welcome)} ${user.displayName}"
                                    val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
                                    toast.show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        },
                        onComplete = {
                            refreshFragment()
                        }
                    )
                }
            )
        }

        logoutButton?.setOnClickListener{
            confirmationDialog?.setContent {
                LogoutDialog(
                    onConfirm = {

                        signOut(
                            onSuccess = {
                                confirmationDialog?.visibility = GONE

                                refreshFragment()
                            }
                        )

                    },
                    onCancel = {
                        confirmationDialog?.visibility = GONE
                    }
                )
            }

            confirmationDialog?.visibility = VISIBLE
        }

        deleteButton?.setOnClickListener{
            confirmationDialog?.setContent {
                DeleteAccountDialog(
                    onConfirm = {

                        deleteAccount(
                            onSuccess = {
                                confirmationDialog?.visibility = GONE

                                refreshFragment()
                            }
                        )

                    },
                    onCancel = {
                        confirmationDialog?.visibility = GONE
                    }
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

}
