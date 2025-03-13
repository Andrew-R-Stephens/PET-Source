package com.tritiumgaming.phasmophobiaevidencepicker.app

import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.phasmophobiaevidencepicker.app.container.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.app.container.dataStore
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.transactions.types.FirestorePurchaseHistory
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AccountManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.SignInCredentialManager

class PETApplication : Application(), AccountManagementService {

    lateinit var container: AppContainer

    override fun onCreate() {

        super.onCreate()

        container = AppContainer(applicationContext, dataStore)

        signIn(
            option = SignInCredentialManager.SignInOptions.SILENT,
            onSuccess = {
                Log.d("Firebase",
                    "Signed in as: ${Firebase.auth.currentUser?.displayName}")
            })

    }

}

