package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app

import android.app.Application
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container.dataStore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.AccountManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.SignInCredentialManager

class PETApplication : Application(), AccountManagementService {

    /*val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "pet-db"
    ).build()*/

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