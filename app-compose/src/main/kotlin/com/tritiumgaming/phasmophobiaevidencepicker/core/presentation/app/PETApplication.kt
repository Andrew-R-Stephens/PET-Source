package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container.CoreContainer
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.AccountManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.SignInCredentialManager
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.container.MainMenuContainer
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container.OperationContainer

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class PETApplication : Application(), AccountManagementService {

    /*val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "pet-db"
    ).build()*/

    private val firestore: FirebaseFirestore by lazy {
        Firebase.firestore
    }
    private val firebaseAuth by lazy {
        Firebase.auth
    }

    lateinit var coreContainer: CoreContainer
    lateinit var mainMenuContainer: MainMenuContainer
    lateinit var operationsContainer: OperationContainer

    override fun onCreate() {

        super.onCreate()

        coreContainer = CoreContainer(applicationContext, dataStore, firestore, firebaseAuth)
        mainMenuContainer = MainMenuContainer(applicationContext, dataStore)
        operationsContainer = OperationContainer(applicationContext, dataStore)

        signIn(
            option = SignInCredentialManager.SignInOptions.SILENT,
            onSuccess = {
                Log.d("Firebase",
                    "Signed in as: ${Firebase.auth.currentUser?.displayName}")
            })

    }

}