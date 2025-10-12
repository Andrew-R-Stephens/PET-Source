package com.tritiumgaming.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.data.account.repository.CredentialsRepositoryImpl
import com.tritiumgaming.data.account.repository.FirestoreAccountRepositoryImpl
import com.tritiumgaming.data.account.source.remote.CredentialsDataSourceImpl
import com.tritiumgaming.data.account.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.data.account.usecase.DeactivateAccountUseCase
import com.tritiumgaming.data.account.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.data.account.usecase.SignOutAccountUseCase
import com.tritiumgaming.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.data.contributor.repository.ContributorRepositoryImpl
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.data.contributor.source.local.ContributorLocalDataSource
import com.tritiumgaming.data.difficulty.repository.DifficultyRepositoryImpl
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.data.evidence.repository.EvidenceRepositoryImpl
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.data.ghost.repository.GhostRepositoryImpl
import com.tritiumgaming.data.ghost.source.GhostDataSource
import com.tritiumgaming.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.data.ghostname.repository.GhostNameRepositoryImpl
import com.tritiumgaming.data.ghostname.source.GhostNameDataSource
import com.tritiumgaming.data.ghostname.source.local.GhostNameLocalDataSource
import com.tritiumgaming.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.data.globalpreferences.source.datastore.GlobalPreferencesDatastoreDataSource
import com.tritiumgaming.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.data.language.source.datastore.LanguageDatastoreDataSource
import com.tritiumgaming.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.data.map.complex.source.service.ComplexMapLocalService
import com.tritiumgaming.data.map.modifiers.repository.MapModifiersRepositoryImpl
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.data.map.modifiers.source.local.MapModifiersLocalDataSource
import com.tritiumgaming.data.map.simple.repository.SimpleMapRepositoryImpl
import com.tritiumgaming.data.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.data.marketplace.bundle.repository.MarketBundleRepositoryImpl
import com.tritiumgaming.data.marketplace.bundle.source.remote.MarketBundleFirestoreDataSourceImpl
import com.tritiumgaming.data.marketplace.palette.repository.MarketPaletteRepositoryImpl
import com.tritiumgaming.data.marketplace.palette.source.datastore.MarketPaletteDatastoreDataSource
import com.tritiumgaming.data.marketplace.palette.source.local.MarketPaletteLocalDataSource
import com.tritiumgaming.data.marketplace.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.data.marketplace.typography.repository.MarketTypographyRepositoryImpl
import com.tritiumgaming.data.marketplace.typography.source.datastore.MarketTypographyDatastoreDataSource
import com.tritiumgaming.data.marketplace.typography.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.data.marketplace.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.data.mission.repository.MissionRepositoryImpl
import com.tritiumgaming.data.mission.source.MissionDataSource
import com.tritiumgaming.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.data.newsletter.source.datastore.NewsletterDatastoreDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.data.review.repository.ReviewTrackerRepositoryImpl
import com.tritiumgaming.data.review.source.datastore.ReviewTrackerDatastoreDataSource
import com.tritiumgaming.shared.core.container.CoreContainer
import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetEnableGhostReorderUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetEnableRTLUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.shared.core.domain.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.shared.core.domain.market.bundle.repository.MarketBundleRemoteRepository
import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitFlowPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitPaletteDataStoreUseCase
import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitTypographyDataStoreUseCase
import com.tritiumgaming.shared.core.domain.market.user.repository.CredentialsRepository
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository
import com.tritiumgaming.shared.core.domain.reviewtracker.source.ReviewTrackerDatastore
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.RemoveAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.home.domain.appinfo.repository.ContributorRepository
import com.tritiumgaming.shared.home.domain.appinfo.usecase.ContributorUseCase
import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.home.domain.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.home.domain.newsletter.usecase.InitFlowNewsletterUseCase
import com.tritiumgaming.shared.home.domain.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.home.domain.newsletter.usecase.SetupNewsletterUseCase
import com.tritiumgaming.shared.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexAchievementsUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexEquipmentUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexPossessionsUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyTypeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.shared.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.shared.operation.domain.ghostname.repository.GhostNameRepository
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.GetEvidenceByIdUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.GetGhostByIdUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.shared.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.shared.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.repsitory.MapModifiersRepository
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.shared.operation.domain.mission.repository.MissionRepository
import com.tritiumgaming.shared.operation.domain.mission.usecase.FetchAllMissionsUseCase
import kotlinx.coroutines.Dispatchers

class CoreContainerImpl(
    applicationContext: Context,
    dataStore: DataStore<Preferences>,
    firestore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
): CoreContainer {

    internal val globalPreferencesRepository: GlobalPreferencesRepository by lazy {
        val globalPreferencesDataSource = GlobalPreferencesDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        GlobalPreferencesRepositoryImpl(
            dataStoreSource = globalPreferencesDataSource
        )
    }

    val setupGlobalPreferencesUseCase = SetupGlobalPreferencesUseCase(
        repository = globalPreferencesRepository)
    val initFlowGlobalPreferencesUseCase = InitFlowGlobalPreferencesUseCase(
        repository = globalPreferencesRepository)
    val setAllowCellularDataUseCase = SetAllowCellularDataUseCase(
        repository = globalPreferencesRepository)
    val setAllowIntroductionUseCase = SetAllowIntroductionUseCase(
        repository = globalPreferencesRepository)
    val setDisableScreenSaverUseCase = SetDisableScreenSaverUseCase(
        repository = globalPreferencesRepository)
    val setEnableGhostReorderUseCase = SetEnableGhostReorderUseCase(
        repository = globalPreferencesRepository)
    val setEnableRTLUseCase = SetEnableRTLUseCase(
        repository = globalPreferencesRepository)
    val setAllowHuntWarnAudioUseCase = SetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository)
    val setMaxHuntWarnFlashTimeUseCase = SetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository)
    val getEnableGhostReorderUseCase = GetEnableGhostReorderUseCase(
        repository = globalPreferencesRepository)
    val getEnableRTLUseCase = GetEnableRTLUseCase(
        repository = globalPreferencesRepository)
    val getMaxHuntWarnFlashTimeUseCase = GetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository)
    val getAllowHuntWarnAudioUseCase = GetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository)

    /**
     * Credentials Service
     */
    internal val credentialsRepository: CredentialsRepository by lazy {
        val credentialsDataSource = CredentialsDataSourceImpl(
            context = applicationContext
        )

        CredentialsRepositoryImpl(
            credentialsDataSource = credentialsDataSource
        )
    }

    val getSignInCredentialsUseCase = GetSignInCredentialsUseCase(
        credentialsRepository = credentialsRepository)
    val signInAccountUseCase = SignInAccountUseCase(
        credentialsRepository = credentialsRepository)
    val signOutAccountUseCase = SignOutAccountUseCase(
        credentialsRepository = credentialsRepository)
    val deactivateAccountUseCase = DeactivateAccountUseCase(
        credentialsRepository = credentialsRepository)

    /**
     * Account
     */
    internal val firestoreAccountRepository: FirestoreAccountRepository by lazy {
        val firestoreUserRemoteDataSource = FirestoreUserRemoteDataSource(
            firestore = firestore
        )
        val firestoreAuthRemoteDataSource = FirestoreAuthRemoteDataSource(
            firebaseAuth = firebaseAuth
        )
        val firestoreAccountDataSource = FirestoreAccountRemoteDataSource(
            firestore = firestore,
            firebaseAuth = firebaseAuth
        )

        FirestoreAccountRepositoryImpl(
            authRemoteDataSource = firestoreAuthRemoteDataSource,
            userRemoteDataSource = firestoreUserRemoteDataSource,
            accountRemoteDataSource = firestoreAccountDataSource
        )
    }


    val setMarketplaceAgreementStateUseCase = SetMarketplaceAgreementStateUseCase(
        repository = firestoreAccountRepository)
    val addAccountCreditsUseCase = AddAccountCreditsUseCase(
        repository = firestoreAccountRepository)
    val removeAccountCreditsUseCase = RemoveAccountCreditsUseCase(
        repository = firestoreAccountRepository)
    val observeAccountCreditsUseCase = ObserveAccountCreditsUseCase(
        repository = firestoreAccountRepository)
    val observeAccountUnlockedPalettesUseCase = ObserveAccountUnlockedPalettesUseCase(
        repository = firestoreAccountRepository)
    val observeAccountUnlockedTypographiesUseCase = 
        ObserveAccountUnlockedTypographiesUseCase(
            repository = firestoreAccountRepository
        )

    /**
     * Review Tracker
     */
    internal val reviewTrackerRepository: ReviewTrackerRepository by lazy {
        val reviewTrackerDataSource: ReviewTrackerDatastore = ReviewTrackerDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        ReviewTrackerRepositoryImpl(
            dataStoreSource = reviewTrackerDataSource
        )
    }

    val getReviewRequestStatusUseCase = GetReviewRequestStatusUseCase(
        repository = reviewTrackerRepository
    )
    val loadReviewRequestStatusUseCase = LoadReviewRequestStatusUseCase(
        repository = reviewTrackerRepository
    )
    val setupReviewTrackerUseCase = SetupReviewTrackerUseCase(
        repository = reviewTrackerRepository
    )
    val initializeReviewTrackerUseCase = InitFlowReviewTrackerUseCase(
        repository = reviewTrackerRepository
    )
    val setReviewRequestStatusUseCase = SetReviewRequestStatusUseCase(
        repository = reviewTrackerRepository
    )
    val setAppTimeAliveUseCase = SetAppTimeAliveUseCase(
        repository = reviewTrackerRepository
    )
    val getAppTimeAliveUseCase = GetAppTimeAliveUseCase(
        repository = reviewTrackerRepository
    )
    val loadAppTimeAliveUseCase = LoadAppTimeAliveUseCase(
        repository = reviewTrackerRepository
    )
    val setAppTimesOpenedUseCase = SetAppTimesOpenedUseCase(
        repository = reviewTrackerRepository
    )
    val getAppTimesOpenedUseCase = GetAppTimesOpenedUseCase(
        repository = reviewTrackerRepository
    )
    val loadAppTimesOpenedUseCase = LoadAppTimesOpenedUseCase(
        repository = reviewTrackerRepository
    )

    /**
     *  Market Bundle
     */
    internal val bundleRepository: MarketBundleRemoteRepository by lazy {
        val firestoreBundleDataSource = MarketBundleFirestoreDataSourceImpl(
            firestore = firestore
        )

        MarketBundleRepositoryImpl(
            firestoreDataSource = firestoreBundleDataSource
        )
    }

    /**
     * Market Typography
     */
    internal val typographyRepository: MarketTypographyRepository by lazy {
        val typographyLocalDataSource = MarketTypographyLocalDataSource()
        val typographyFirestoreDataSource = MarketTypographyFirestoreDataSource(
            firestore = firestore
        )
        val typographyDatastoreDataSource = MarketTypographyDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        MarketTypographyRepositoryImpl(
            localDataSource = typographyLocalDataSource,
            firestoreDataSource = typographyFirestoreDataSource,
            dataStoreSource = typographyDatastoreDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val findNextAvailableTypographyUseCase = FindNextAvailableTypographyUseCase(
        marketRepository = typographyRepository,
        accountRepository = firestoreAccountRepository
    )
    val initTypographyDataStoreUseCase = InitTypographyDataStoreUseCase(
        repository = typographyRepository
    )
    val initFlowTypographyUseCase = InitFlowTypographyUseCase(
        repository = typographyRepository
    )
    val saveCurrentTypographyUseCase = SaveCurrentTypographyUseCase(
        repository = typographyRepository
    )
    val getAvailableTypographiesUseCase = GetAvailableTypographiesUseCase(
        repository = typographyRepository
    )
    val getTypographyByUUIDUseCase = GetTypographyByUUIDUseCase(
        repository = typographyRepository
    )

    /**
     * Market Palette
     */
    internal val paletteRepository: MarketPaletteRepository by lazy {
        val paletteLocalDataSource = MarketPaletteLocalDataSource()
        val paletteFirestoreDataSource = MarketPaletteFirestoreDataSource(
            firestore = firestore
        )
        val paletteDatastoreDataSource = MarketPaletteDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        MarketPaletteRepositoryImpl(
            localDataSource = paletteLocalDataSource,
            firestoreDataSource = paletteFirestoreDataSource,
            dataStoreSource = paletteDatastoreDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }
    
    val findNextAvailablePaletteUseCase = FindNextAvailablePaletteUseCase(
        marketRepository = paletteRepository,
        accountRepository = firestoreAccountRepository
    )
    val initPaletteDataStoreUseCase = InitPaletteDataStoreUseCase(
        repository = paletteRepository)
    val initFlowPaletteUseCase = InitFlowPaletteUseCase(
        repository = paletteRepository)
    val saveCurrentPaletteUseCase = SaveCurrentPaletteUseCase(
        repository = paletteRepository)
    val getAvailablePalettesUseCase = GetAvailablePalettesUseCase(
        repository = paletteRepository
    )
    val getPaletteByUUIDUseCase = GetPaletteByUUIDUseCase(
        repository = paletteRepository
    )

    /**
     * Language
     */
    internal val languageRepository: LanguageRepository by lazy {
        val languageLocalDataSource = LanguageLocalDataSource(
            applicationContext = applicationContext
        )
        val languageDatastoreDataSource = LanguageDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        LanguageRepositoryImpl(
            localDataSource = languageLocalDataSource,
            dataStoreSource = languageDatastoreDataSource
        )
    }

    val getLanguagesUseCase = GetAvailableLanguagesUseCase(
        repository = languageRepository
    )
    val getDefaultLanguageUseCase = GetDefaultLanguageUseCase(
        repository = languageRepository
    )
    val setDefaultLanguageUseCase = SetDefaultLanguageUseCase(
        repository = languageRepository
    )
    val setupLanguageUseCase = SetupLanguageUseCase(
        repository = languageRepository
    )
    val initFlowLanguageUseCase = InitFlowLanguageUseCase(
        repository = languageRepository
    )
    val saveCurrentLanguageUseCase = SaveCurrentLanguageUseCase(
        repository = languageRepository
    )
    val getCurrentLanguageUseCase = GetCurrentLanguageUseCase(
        repository = languageRepository
    )
    val loadCurrentLanguageUseCase = LoadCurrentLanguageUseCase(
        repository = languageRepository
    )

    // App Info
    internal val contributorRepository: ContributorRepository by lazy {
        val contributorLocalDataSource: ContributorDataSource = ContributorLocalDataSource()

        ContributorRepositoryImpl(
            localSource = contributorLocalDataSource
        )
    }
    
    val getContributorsUseCase = ContributorUseCase(
        appInfoRepository = contributorRepository
    )

    // Newsletter
    internal val newsletterRepository: NewsletterRepository by lazy {
        val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSourceImpl(
            applicationContext = applicationContext
        )
        val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSourceImpl(
            newsletterApi = NewsletterService()
        )
        val newsletterDatastore = NewsletterDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )
        val connectivityManagerHelper = ConnectivityManagerHelper(
            applicationContext = applicationContext
        )

        NewsletterRepositoryImpl(
            localDataSource = newsletterLocalDataSource,
            remoteDataSource = newsletterRemoteDataSource,
            dataStoreSource = newsletterDatastore,
            connectivityManagerHelper = connectivityManagerHelper,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val setupNewsletterUseCase = SetupNewsletterUseCase(
        repository = newsletterRepository
    )
    val initFlowNewsletterUseCase = InitFlowNewsletterUseCase(
        repository = newsletterRepository
    )
    val getNewsletterInboxesUseCase = FetchNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    val saveNewsletterInboxLastReadDateUseCase = SaveNewsletterInboxLastReadDateUseCase(
        repository = newsletterRepository
    )


    /*
     * Investigation Journal
     */

    // Ghost
    internal val ghostRepository: GhostRepository by lazy {
        val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource(
            applicationContext = applicationContext
        )
        GhostRepositoryImpl(
            ghostLocalDataSource = ghostLocalDataSource
        )
    }

    val fetchGhostsUseCase = FetchGhostsUseCase(
        repository = ghostRepository
    )
    val getGhostByIdUseCase = GetGhostByIdUseCase(
        repository = ghostRepository
    )

    // Evidence
    internal val evidenceRepository: EvidenceRepository by lazy {
        val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource(
            applicationContext = applicationContext
        )
        EvidenceRepositoryImpl(
            evidenceLocalDataSource = evidenceLocalDataSource
        )
    }

    val fetchEvidencesUseCase = FetchEvidencesUseCase(
        repository = evidenceRepository
    )
    val getEvidenceByIdUseCase = GetEvidenceByIdUseCase(
        repository = evidenceRepository
    )

    // Journal
    val fetchGhostEvidencesUseCase = FetchGhostEvidencesUseCase(
        ghostRepository = ghostRepository,
        evidenceRepository = evidenceRepository
    )
    val initRuledEvidenceUseCase = InitRuledEvidenceUseCase(
        fetchEvidencesUseCase = fetchEvidencesUseCase
    )

    // Difficulty
    internal val difficultyRepository: DifficultyRepository by lazy {
        val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource(
            applicationContext = applicationContext
        )
        DifficultyRepositoryImpl(
            localSource = difficultyLocalDataSource
        )
    }

    val fetchDifficultiesUseCase = FetchDifficultiesUseCase(
        difficultyRepository = difficultyRepository
    )
    val getDifficultyTypeUseCase = GetDifficultyTypeUseCase(
        difficultyRepository = difficultyRepository
    )
    val getDifficultyNameUseCase = GetDifficultyNameUseCase(
        difficultyRepository = difficultyRepository
    )
    val getDifficultyModifierUseCase = GetDifficultyModifierUseCase(
        difficultyRepository = difficultyRepository
    )
    val getDifficultyTimeUseCase = GetDifficultyTimeUseCase(
        difficultyRepository = difficultyRepository
    )
    val getDifficultyResponseTypeUseCase = GetDifficultyResponseTypeUseCase(
        difficultyRepository = difficultyRepository
    )
    val getDifficultyInitialSanityUseCase = GetDifficultyInitialSanityUseCase(
        difficultyRepository = difficultyRepository
    )
    val incrementDifficultyIndexUseCase = IncrementDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
    )
    val decrementDifficultyIndexUseCase = DecrementDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
    )

    // Mission
    internal val missionRepository: MissionRepository by lazy {
        val missionLocalDataSource: MissionDataSource = MissionLocalDataSource(
            applicationContext = applicationContext,
        )

        MissionRepositoryImpl(
            localSource = missionLocalDataSource
        )
    }

    val fetchAllMissionsUseCase = FetchAllMissionsUseCase(
        missionRepository = missionRepository
    )

    // Ghost Name
    internal val ghostNameRepository: GhostNameRepository by lazy {
        val ghostNameLocalDataSource: GhostNameDataSource = GhostNameLocalDataSource(
            applicationContext = applicationContext
        )

        GhostNameRepositoryImpl(
            localSource = ghostNameLocalDataSource
        )
    }

    val fetchAllFirstNamesUseCase = FetchAllFirstNamesUseCase(
        repository = ghostNameRepository
    )
    val fetchAllMaleNamesUseCase = FetchAllMaleNamesUseCase(
        repository = ghostNameRepository
    )
    val fetchAllFemaleNamesUseCase = FetchAllFemaleNamesUseCase(
        repository = ghostNameRepository
    )
    val fetchAllSurnamesUseCase = FetchAllSurnamesUseCase(
        repository = ghostNameRepository
    )

    // Map Modifiers
    internal val mapModifiersRepository: MapModifiersRepository by lazy {
        val mapModifiersLocalDataSource: MapModifiersDataSource = MapModifiersLocalDataSource(
            applicationContext = applicationContext
        )
        MapModifiersRepositoryImpl(
            localSource = mapModifiersLocalDataSource
        )
    }
    
    val fetchMapModifiersUseCase = FetchMapModifiersUseCase(
        simpleMapRepository = mapModifiersRepository
    )

    // Simple Map
    internal val simpleMapRepository: SimpleMapRepository by lazy {
        val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(
            applicationContext = applicationContext
        )
        SimpleMapRepositoryImpl(
            localSource = simpleMapLocalDataSource
        )
    }

    val fetchSimpleMapsUseCase = FetchSimpleMapsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val fetchMapThumbnailsUseCase = FetchMapThumbnailsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val incrementMapIndexUseCase = IncrementMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val decrementMapIndexUseCase = DecrementMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val incrementMapFloorIndexUseCase = IncrementMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val decrementMapFloorIndexUseCase = DecrementMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val getSimpleMapIdUseCase = GetSimpleMapIdUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val getSimpleMapNameUseCase = GetSimpleMapNameUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val getSimpleMapSizeUseCase = GetSimpleMapSizeUseCase(
        simpleMapRepository = simpleMapRepository
    )
    val getSimpleMapSetupModifierUseCase = GetSimpleMapSetupModifierUseCase(
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
    )
    val getSimpleMapNormalModifierUseCase = GetSimpleMapNormalModifierUseCase(
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
    )
    val getMapModifierUseCase = GetMapModifierUseCase(
        getSimpleMapNormalModifierUseCase = getSimpleMapNormalModifierUseCase,
        getSimpleMapSetupModifierUseCase = getSimpleMapSetupModifierUseCase
    )

    // Complex Map
    val complexMapRepository: ComplexMapRepository by lazy {
        val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
            applicationContext = applicationContext,
            service = ComplexMapLocalService()
        )
        ComplexMapRepositoryImpl(
            localSource = complexMapLocalDataSource
        )
    }

    val fetchComplexMapsUseCase = FetchComplexMapsUseCase(
        complexMapRepository = complexMapRepository
    )

    /*
     * Codex
     */
    // Codex
    internal val codexRepository: CodexRepository by lazy {
        // Achievements
        val codexAchievementsLocalDataSource: CodexAchievementsLocalDataSource =
            CodexAchievementsLocalDataSource()

        // Equipment
        val codexEquipmentLocalDataSource: CodexEquipmentLocalDataSource =
            CodexEquipmentLocalDataSource()

        //Possessions
        val codexPossessionsLocalDataSource: CodexPossessionsLocalDataSource =
            CodexPossessionsLocalDataSource()

        CodexRepositoryImpl(
            achievementsLocalDataSource = codexAchievementsLocalDataSource,
            equipmentLocalDataSource = codexEquipmentLocalDataSource,
            possessionsLocalDataSource = codexPossessionsLocalDataSource
        )
    }

    val fetchCodexAchievementsUseCase = FetchCodexAchievementsUseCase(
        codexRepository = codexRepository
    )
    val fetchCodexEquipmentUseCase = FetchCodexEquipmentUseCase(
        codexRepository = codexRepository
    )
    val fetchCodexPossessionsUseCase = FetchCodexPossessionsUseCase(
        codexRepository = codexRepository
    )

}
