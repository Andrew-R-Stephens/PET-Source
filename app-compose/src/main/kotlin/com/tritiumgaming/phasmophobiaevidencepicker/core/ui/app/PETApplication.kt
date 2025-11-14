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
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider
import com.tritiumstudios.feature.codex.app.container.CodexContainer
import com.tritiumstudios.feature.codex.app.container.CodexContainerProvider
import com.tritiumstudios.feature.investigation.app.container.InvestigationContainer
import com.tritiumstudios.feature.investigation.app.container.InvestigationContainerProvider
import com.tritiumstudios.feature.maps.app.container.MapViewerContainer
import com.tritiumstudios.feature.maps.app.container.MapViewerContainerProvider
import com.tritiumstudios.feature.missions.app.container.MissionsContainer
import com.tritiumstudios.feature.missions.app.container.MissionsContainerProvider

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class PETApplication : Application(),
    AppContainerProvider,
    HomeContainerProvider,
    /*OperationContainerProvider,*/
    InvestigationContainerProvider,
    MissionsContainerProvider,
    CodexContainerProvider,
    MapViewerContainerProvider {

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
    //lateinit var operationsContainer: OperationContainer
    lateinit var codexContainer: CodexContainer
    lateinit var missionsContainer: MissionsContainer
    lateinit var mapViewerContainer: MapViewerContainer
    lateinit var investigationContainer: InvestigationContainer

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

        /*operationsContainer = OperationContainer(
            applicationContext = applicationContext,
            // User Preferences
            getAllowHuntWarnAudioUseCase = coreContainer.getAllowHuntWarnAudioUseCase,
            getEnableGhostReorderUseCase = coreContainer.getEnableGhostReorderUseCase,
            getEnableRTLUseCase = coreContainer.getEnableRTLUseCase,
            getMaxHuntWarnFlashTimeUseCase = coreContainer.getMaxHuntWarnFlashTimeUseCase,
        )*/

        investigationContainer = InvestigationContainer(
            applicationContext = applicationContext,
            // User Preferences
            getAllowHuntWarnAudioUseCase = coreContainer.getAllowHuntWarnAudioUseCase,
            getEnableGhostReorderUseCase = coreContainer.getEnableGhostReorderUseCase,
            getEnableRTLUseCase = coreContainer.getEnableRTLUseCase,
            getMaxHuntWarnFlashTimeUseCase = coreContainer.getMaxHuntWarnFlashTimeUseCase,
        )

        codexContainer = CodexContainer()

        missionsContainer = MissionsContainer(
            applicationContext = applicationContext
        )

        mapViewerContainer = MapViewerContainer(
            applicationContext = applicationContext
        )

    }

    override fun provideAppContainer(): AppContainer = appContainer
    override fun provideHomeContainer(): HomeContainer = homeContainer
    //override fun provideOperationContainer(): OperationContainer = operationsContainer
    override fun provideCodexContainer(): CodexContainer = codexContainer
    override fun provideMissionsContainer(): MissionsContainer = missionsContainer
    override fun provideMapViewerContainer(): MapViewerContainer = mapViewerContainer
    override fun provideInvestigationContainer(): InvestigationContainer = investigationContainer

}