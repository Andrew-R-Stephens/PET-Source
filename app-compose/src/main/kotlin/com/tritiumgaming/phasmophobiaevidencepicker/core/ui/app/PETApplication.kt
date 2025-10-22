package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.app

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.tritiumgaming.feature.core.container.CoreContainer
import com.tritiumgaming.feature.home.app.container.HomeContainer
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.feature.operation.app.container.OperationContainer
import com.tritiumgaming.feature.operation.app.container.OperationContainerProvider
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class PETApplication : Application(), AppContainerProvider, HomeContainerProvider, OperationContainerProvider {

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

    lateinit var appContainer: AppContainer
    lateinit var homeContainer: HomeContainer
    lateinit var operationsContainer: OperationContainer

    override fun onCreate() {

        super.onCreate()

        coreContainer = CoreContainer(applicationContext, dataStore, firestore, firebaseAuth)

        appContainer = AppContainer(
            setupGlobalPreferencesUseCase = coreContainer.setupGlobalPreferencesUseCase,
            initFlowGlobalPreferencesUseCase = coreContainer.initFlowGlobalPreferencesUseCase,
            getTypographyByUUIDUseCase = coreContainer.getTypographyByUUIDUseCase,
            getPaletteByUUIDUseCase = coreContainer.getPaletteByUUIDUseCase,
        )

        homeContainer = HomeContainer(
            applicationContext = applicationContext,
            dataStore = dataStore,
            getSignInCredentialsUseCase = coreContainer.getSignInCredentialsUseCase,
            signInAccountUseCase = coreContainer.signInAccountUseCase,
            signOutAccountUseCase = coreContainer.signOutAccountUseCase,
            deactivateAccountUseCase = coreContainer.deactivateAccountUseCase,
            observeAccountCreditsUseCase = coreContainer.observeAccountCreditsUseCase,
            observeAccountUnlockedPalettesUseCase = coreContainer.observeAccountUnlockedPalettesUseCase,
            observeAccountUnlockedTypographiesUseCase = coreContainer.observeAccountUnlockedTypographiesUseCase,
            setupGlobalPreferencesUseCase = coreContainer.setupGlobalPreferencesUseCase,
            // Global Preferences
            initFlowGlobalPreferencesUseCase = coreContainer.initFlowGlobalPreferencesUseCase,
            setAllowCellularDataUseCase = coreContainer.setAllowCellularDataUseCase,
            setAllowHuntWarnAudioUseCase = coreContainer.setAllowHuntWarnAudioUseCase,
            setAllowIntroductionUseCase = coreContainer.setAllowIntroductionUseCase,
            setDisableScreenSaverUseCase = coreContainer.setDisableScreenSaverUseCase,
            setEnableGhostReorderUseCase = coreContainer.setEnableGhostReorderUseCase,
            setEnableRTLUseCase = coreContainer.setEnableRTLUseCase,
            setMaxHuntWarnFlashTimeUseCase = coreContainer.setMaxHuntWarnFlashTimeUseCase,
            // Languages
            getAvailableLanguagesUseCase = coreContainer.getLanguagesUseCase,
            getDefaultLanguageUseCase = coreContainer.getDefaultLanguageUseCase,
            setDefaultLanguageUseCase = coreContainer.setDefaultLanguageUseCase,
            initLanguageDataStoreUseCase = coreContainer.setupLanguageUseCase,
            initFlowLanguageUseCase = coreContainer.initFlowLanguageUseCase,
            saveCurrentLanguageUseCase = coreContainer.saveCurrentLanguageUseCase,
            getCurrentLanguageUseCase = coreContainer.getCurrentLanguageUseCase,
            loadCurrentLanguageUseCase = coreContainer.loadCurrentLanguageUseCase,
            // Typographies
            getAvailableTypographiesUseCase = coreContainer.getAvailableTypographiesUseCase,
            saveCurrentTypographyUseCase = coreContainer.saveCurrentTypographyUseCase,
            getTypographyByUUIDUseCase = coreContainer.getTypographyByUUIDUseCase,
            findNextAvailableTypographyUseCase = coreContainer.findNextAvailableTypographyUseCase,
            // Palettes
            saveCurrentPaletteUseCase = coreContainer.saveCurrentPaletteUseCase,
            getAvailablePalettesUseCase = coreContainer.getAvailablePalettesUseCase,
            getPaletteByUUIDUseCase = coreContainer.getPaletteByUUIDUseCase,
            findNextAvailablePaletteUseCase = coreContainer.findNextAvailablePaletteUseCase,
            // Reviews
            initReviewTrackerDataStoreUseCase = coreContainer.setupReviewTrackerUseCase,
            initFlowReviewTrackerUseCase = coreContainer.initializeReviewTrackerUseCase,
            setReviewRequestStatusUseCase = coreContainer.setReviewRequestStatusUseCase,
            getReviewRequestStatusUseCase = coreContainer.getReviewRequestStatusUseCase,
            loadReviewRequestStatusUseCase = coreContainer.loadReviewRequestStatusUseCase,
            setAppTimeAliveUseCase = coreContainer.setAppTimeAliveUseCase,
            getAppTimeAliveUseCase = coreContainer.getAppTimeAliveUseCase,
            loadAppTimeAliveUseCase = coreContainer.loadAppTimeAliveUseCase,
            setAppTimesOpenedUseCase = coreContainer.setAppTimesOpenedUseCase,
            getAppTimesOpenedUseCase = coreContainer.getAppTimesOpenedUseCase,
            loadAppTimesOpenedUseCase = coreContainer.loadAppTimesOpenedUseCase
        )

        operationsContainer = OperationContainer(
            applicationContext = applicationContext,
            // User Preferences
            getAllowHuntWarnAudioUseCase = coreContainer.getAllowHuntWarnAudioUseCase,
            getEnableGhostReorderUseCase = coreContainer.getEnableGhostReorderUseCase,
            getEnableRTLUseCase = coreContainer.getEnableRTLUseCase,
            getMaxHuntWarnFlashTimeUseCase = coreContainer.getMaxHuntWarnFlashTimeUseCase,
        )

    }

    override fun provideAppContainer(): AppContainer = appContainer
    override fun provideHomeContainer(): HomeContainer = homeContainer
    override fun provideOperationContainer(): OperationContainer = operationsContainer

}