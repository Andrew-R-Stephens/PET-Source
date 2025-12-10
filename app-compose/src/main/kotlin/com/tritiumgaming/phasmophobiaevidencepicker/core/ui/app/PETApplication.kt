package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.app

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.tritiumgaming.feature.about.app.container.AboutContainer
import com.tritiumgaming.feature.about.app.container.AboutContainerProvider
import com.tritiumgaming.feature.core.container.CoreContainer
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainer
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.newsletter.app.container.NewsletterContainer
import com.tritiumgaming.feature.newsletter.app.container.NewsletterContainerProvider
import com.tritiumgaming.feature.start.app.container.StartContainer
import com.tritiumgaming.feature.start.app.container.StartContainerProvider
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.core.container.AppContainerProvider
import com.tritiumgaming.feature.account.app.container.AccountContainer
import com.tritiumgaming.feature.account.app.container.AccountContainerProvider
import com.tritiumgaming.feature.codex.app.container.CodexContainer
import com.tritiumgaming.feature.codex.app.container.CodexContainerProvider
import com.tritiumgaming.feature.investigation.app.container.InvestigationContainer
import com.tritiumgaming.feature.investigation.app.container.InvestigationContainerProvider
import com.tritiumgaming.feature.language.app.container.LanguageContainer
import com.tritiumgaming.feature.language.app.container.LanguageContainerProvider
import com.tritiumgaming.feature.maps.app.container.MapViewerContainer
import com.tritiumgaming.feature.maps.app.container.MapViewerContainerProvider
import com.tritiumgaming.feature.missions.app.container.MissionsContainer
import com.tritiumgaming.feature.missions.app.container.MissionsContainerProvider
import com.tritiumgaming.feature.settings.app.container.SettingsContainer
import com.tritiumgaming.feature.settings.app.container.SettingsContainerProvider

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class PETApplication : Application(),
    AppContainerProvider,
    AccountContainerProvider,
    LanguageContainerProvider,
    SettingsContainerProvider,
    StartContainerProvider,
    AboutContainerProvider,
    NewsletterContainerProvider,
    MarketplaceContainerProvider,
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

    lateinit var accountContainer: AccountContainer
    lateinit var languageContainer: LanguageContainer
    lateinit var settingsContainer: SettingsContainer
    lateinit var aboutContainer: AboutContainer
    lateinit var startContainer: StartContainer
    lateinit var newsletterContainer: NewsletterContainer
    lateinit var marketplaceContainer: MarketplaceContainer

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

        accountContainer = AccountContainer(
            getSignInCredentialsUseCase = coreContainer.getSignInCredentialsUseCase,
            signInAccountUseCase = coreContainer.signInAccountUseCase,
            signOutAccountUseCase = coreContainer.signOutAccountUseCase,
            deactivateAccountUseCase = coreContainer.deactivateAccountUseCase,
            observeAccountCreditsUseCase = coreContainer.observeAccountCreditsUseCase,
            observeAccountUnlockedPalettesUseCase = coreContainer.observeAccountUnlockedPalettesUseCase,
            observeAccountUnlockedTypographiesUseCase = coreContainer.observeAccountUnlockedTypographiesUseCase
        )

        languageContainer = LanguageContainer(
            // Languages
            getAvailableLanguagesUseCase = coreContainer.getLanguagesUseCase,
            getDefaultLanguageUseCase = coreContainer.getDefaultLanguageUseCase,
            setDefaultLanguageUseCase = coreContainer.setDefaultLanguageUseCase,
            initLanguageDataStoreUseCase = coreContainer.setupLanguageUseCase,
            initFlowLanguageUseCase = coreContainer.initFlowLanguageUseCase,
            saveCurrentLanguageUseCase = coreContainer.saveCurrentLanguageUseCase,
            loadCurrentLanguageUseCase = coreContainer.loadCurrentLanguageUseCase,
        )

        settingsContainer = SettingsContainer(
            // Global Preferences
            setupGlobalPreferencesUseCase = coreContainer.setupGlobalPreferencesUseCase,
            initFlowGlobalPreferencesUseCase = coreContainer.initFlowGlobalPreferencesUseCase,
            setAllowCellularDataUseCase = coreContainer.setAllowCellularDataUseCase,
            setAllowHuntWarnAudioUseCase = coreContainer.setAllowHuntWarnAudioUseCase,
            setAllowIntroductionUseCase = coreContainer.setAllowIntroductionUseCase,
            setDisableScreenSaverUseCase = coreContainer.setDisableScreenSaverUseCase,
            setEnableGhostReorderUseCase = coreContainer.setEnableGhostReorderUseCase,
            setEnableRTLUseCase = coreContainer.setEnableRTLUseCase,
            setMaxHuntWarnFlashTimeUseCase = coreContainer.setMaxHuntWarnFlashTimeUseCase,
            // Typographies
            saveCurrentTypographyUseCase = coreContainer.saveCurrentTypographyUseCase,
            getTypographyByUUIDUseCase = coreContainer.getTypographyByUUIDUseCase,
            findNextAvailableTypographyUseCase = coreContainer.findNextAvailableTypographyUseCase,
            // Palettes
            saveCurrentPaletteUseCase = coreContainer.saveCurrentPaletteUseCase,
            getPaletteByUUIDUseCase = coreContainer.getPaletteByUUIDUseCase,
            findNextAvailablePaletteUseCase = coreContainer.findNextAvailablePaletteUseCase
        )

        aboutContainer = AboutContainer()

        startContainer = StartContainer(
            setupNewsletterUseCase = coreContainer.setupNewsletterUseCase,
            getFlowNewsletterDatastoreUseCase = coreContainer.getFlowNewsletterDatastoreUseCase,
            getFlowNewsletterInboxesUseCase= coreContainer.getFlowNewsletterInboxesUseCase,
            getNewsletterInboxesUseCase= coreContainer.getNewsletterInboxesUseCase,
            setupGlobalPreferencesUseCase= coreContainer.setupGlobalPreferencesUseCase,
            initFlowGlobalPreferencesUseCase= coreContainer.initFlowGlobalPreferencesUseCase,
            setAllowIntroductionUseCase= coreContainer.setAllowIntroductionUseCase,
            initReviewTrackerDataStoreUseCase= coreContainer.setupReviewTrackerUseCase,
            initFlowReviewTrackerUseCase= coreContainer.initializeReviewTrackerUseCase,
            setReviewRequestStatusUseCase= coreContainer.setReviewRequestStatusUseCase,
            setAppTimeAliveUseCase= coreContainer.setAppTimeAliveUseCase,
            incrementAppTimesOpenedUseCase = coreContainer.incrementAppTimesOpenedUseCase,
            setAppTimesOpenedUseCase= coreContainer.setAppTimesOpenedUseCase,
        )

        newsletterContainer = NewsletterContainer(
            setupNewsletterUseCase = coreContainer.setupNewsletterUseCase,
            getFlowNewsletterDatastoreUseCase = coreContainer.getFlowNewsletterDatastoreUseCase,
            getFlowNewsletterInboxesUseCase= coreContainer.getFlowNewsletterInboxesUseCase,
            getNewsletterInboxesUseCase= coreContainer.getNewsletterInboxesUseCase,
            saveNewsletterInboxLastReadDateUseCase= coreContainer.saveNewsletterInboxLastReadDateUseCase
        )

        marketplaceContainer = MarketplaceContainer(
            applicationContext = applicationContext,
            getSignInCredentialsUseCase = coreContainer.getSignInCredentialsUseCase,
            signInAccountUseCase = coreContainer.signInAccountUseCase,
            signOutAccountUseCase = coreContainer.signOutAccountUseCase,
            deactivateAccountUseCase = coreContainer.deactivateAccountUseCase,
            observeAccountCreditsUseCase = coreContainer.observeAccountCreditsUseCase,
            observeAccountUnlockedPalettesUseCase = coreContainer.observeAccountUnlockedPalettesUseCase,
            observeAccountUnlockedTypographiesUseCase = coreContainer.observeAccountUnlockedTypographiesUseCase,
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
        )

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
    override fun provideAccountContainer(): AccountContainer = accountContainer
    override fun provideLanguageContainer(): LanguageContainer = languageContainer
    override fun provideSettingsContainer(): SettingsContainer = settingsContainer
    override fun provideStartContainer(): StartContainer = startContainer
    override fun provideAboutContainer(): AboutContainer = aboutContainer
    override fun provideNewsletterContainer(): NewsletterContainer = newsletterContainer
    override fun provideMarketplaceContainer(): MarketplaceContainer = marketplaceContainer
    override fun provideCodexContainer(): CodexContainer = codexContainer
    override fun provideMissionsContainer(): MissionsContainer = missionsContainer
    override fun provideMapViewerContainer(): MapViewerContainer = mapViewerContainer
    override fun provideInvestigationContainer(): InvestigationContainer = investigationContainer

}