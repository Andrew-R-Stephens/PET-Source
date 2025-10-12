package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.app

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.tritiumgaming.core.di.CoreContainerImpl
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

    lateinit var coreContainer: CoreContainerImpl

    lateinit var appContainer: AppContainer
    lateinit var homeContainer: HomeContainer
    lateinit var operationsContainer: OperationContainer

    override fun onCreate() {

        super.onCreate()

        coreContainer = CoreContainerImpl(applicationContext, dataStore, firestore, firebaseAuth)

        appContainer = AppContainer(
            initTypographyDataStoreUseCase = coreContainer.initTypographyDataStoreUseCase,
            initFlowTypographyUseCase = coreContainer.initFlowTypographyUseCase,
            saveCurrentTypographyUseCase = coreContainer.saveCurrentTypographyUseCase,
            getTypographyByUUIDUseCase = coreContainer.getTypographyByUUIDUseCase,
            initPaletteDataStoreUseCase = coreContainer.initPaletteDataStoreUseCase,
            initFlowPaletteUseCase = coreContainer.initFlowPaletteUseCase,
            saveCurrentPaletteUseCase = coreContainer.saveCurrentPaletteUseCase,
            getPaletteByUUIDUseCase = coreContainer.getPaletteByUUIDUseCase,
        )

        homeContainer = HomeContainer(
            getContributorsUseCase = coreContainer.getContributorsUseCase,
            setupNewsletterUseCase = coreContainer.setupNewsletterUseCase,
            initFlowNewsletterUseCase = coreContainer.initFlowNewsletterUseCase,
            getNewsletterInboxesUseCase = coreContainer.getNewsletterInboxesUseCase,
            saveNewsletterInboxLastReadDateUseCase = coreContainer.saveNewsletterInboxLastReadDateUseCase,
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
            initTypographyDataStoreUseCase = coreContainer.initTypographyDataStoreUseCase,
            initFlowTypographyUseCase = coreContainer.initFlowTypographyUseCase,
            saveCurrentTypographyUseCase = coreContainer.saveCurrentTypographyUseCase,
            getTypographyByUUIDUseCase = coreContainer.getTypographyByUUIDUseCase,
            findNextAvailableTypographyUseCase = coreContainer.findNextAvailableTypographyUseCase,
            // Palettes
            initPaletteDataStoreUseCase = coreContainer.initPaletteDataStoreUseCase,
            initFlowPaletteUseCase = coreContainer.initFlowPaletteUseCase,
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
            // Ghosts
            fetchGhostsUseCase = coreContainer.fetchGhostsUseCase,
            getGhostByIdUseCase = coreContainer.getGhostByIdUseCase,
            // Evidence
            fetchEvidencesUseCase = coreContainer.fetchEvidencesUseCase,
            getEvidenceByIdUseCase = coreContainer.getEvidenceByIdUseCase,
            // Journal
            fetchGhostEvidencesUseCase = coreContainer.fetchGhostEvidencesUseCase,
            initRuledEvidenceUseCase = coreContainer.initRuledEvidenceUseCase,
            // Difficulty
            fetchDifficultiesUseCase = coreContainer.fetchDifficultiesUseCase,
            getDifficultyTypeUseCase = coreContainer.getDifficultyTypeUseCase,
            getDifficultyNameUseCase = coreContainer.getDifficultyNameUseCase,
            getDifficultyModifierUseCase = coreContainer.getDifficultyModifierUseCase,
            getDifficultyTimeUseCase = coreContainer.getDifficultyTimeUseCase,
            getDifficultyResponseTypeUseCase = coreContainer.getDifficultyResponseTypeUseCase,
            getDifficultyInitialSanityUseCase = coreContainer.getDifficultyInitialSanityUseCase,
            incrementDifficultyIndexUseCase = coreContainer.incrementDifficultyIndexUseCase,
            decrementDifficultyIndexUseCase = coreContainer.decrementDifficultyIndexUseCase,
            // Objectives
            fetchAllMissionsUseCase = coreContainer.fetchAllMissionsUseCase,
            fetchAllFirstNamesUseCase = coreContainer.fetchAllFirstNamesUseCase,
            fetchAllMaleNamesUseCase = coreContainer.fetchAllMaleNamesUseCase,
            fetchAllFemaleNamesUseCase = coreContainer.fetchAllFemaleNamesUseCase,
            fetchAllSurnamesUseCase = coreContainer.fetchAllSurnamesUseCase,
            // Map
            fetchMapModifiersUseCase = coreContainer.fetchMapModifiersUseCase,
            fetchSimpleMapsUseCase = coreContainer.fetchSimpleMapsUseCase,
            fetchMapThumbnailsUseCase = coreContainer.fetchMapThumbnailsUseCase,
            incrementMapIndexUseCase = coreContainer.incrementMapIndexUseCase,
            decrementMapIndexUseCase = coreContainer.decrementMapIndexUseCase,
            incrementMapFloorIndexUseCase = coreContainer.incrementMapFloorIndexUseCase,
            decrementMapFloorIndexUseCase = coreContainer.decrementMapFloorIndexUseCase,
            getSimpleMapIdUseCase = coreContainer.getSimpleMapIdUseCase,
            getSimpleMapNameUseCase = coreContainer.getSimpleMapNameUseCase,
            getSimpleMapSizeUseCase = coreContainer.getSimpleMapSizeUseCase,
            getSimpleMapSetupModifierUseCase = coreContainer.getSimpleMapSetupModifierUseCase,
            getSimpleMapNormalModifierUseCase = coreContainer.getSimpleMapNormalModifierUseCase,
            getMapModifierUseCase = coreContainer.getMapModifierUseCase,
            // Codex
            fetchComplexMapsUseCase = coreContainer.fetchComplexMapsUseCase,
            fetchCodexAchievementsUseCase = coreContainer.fetchCodexAchievementsUseCase,
            fetchCodexEquipmentUseCase = coreContainer.fetchCodexEquipmentUseCase,
            fetchCodexPossessionsUseCase = coreContainer.fetchCodexPossessionsUseCase,
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