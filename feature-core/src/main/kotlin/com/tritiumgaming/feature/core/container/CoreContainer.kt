package com.tritiumgaming.feature.core.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.GoogleMobileAdsConsentManager
import com.tritiumgaming.core.domain.market.user.repository.CredentialsRepository
import com.tritiumgaming.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.data.account.repository.CredentialsRepositoryImpl
import com.tritiumgaming.data.account.repository.FirestoreAccountRepository
import com.tritiumgaming.data.account.repository.FirestoreAccountRepositoryImpl
import com.tritiumgaming.data.account.source.remote.CredentialsDataSourceImpl
import com.tritiumgaming.data.account.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.RemoveAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.data.ads.mappers.RewardedAdsResources
import com.tritiumgaming.data.ads.mappers.asString
import com.tritiumgaming.data.ads.repository.RewardedAdRepository
import com.tritiumgaming.data.ads.repository.RewardedAdRepositoryImpl
import com.tritiumgaming.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.data.ads.usecase.LoadRewardedAdUseCase
import com.tritiumgaming.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.data.challenge.repository.ChallengeRepository
import com.tritiumgaming.data.challenge.repository.ChallengeRepositoryImpl
import com.tritiumgaming.data.challenge.source.ChallengeDataSource
import com.tritiumgaming.data.challenge.source.local.ChallengeLocalDataSource
import com.tritiumgaming.data.challenge.usecase.GetChallengesUseCase
import com.tritiumgaming.data.challenge.usecase.GetCurrentChallengeUseCase
import com.tritiumgaming.data.customdifficulty.repository.CustomDifficultyRepository
import com.tritiumgaming.data.customdifficulty.repository.CustomDifficultyRepositoryImpl
import com.tritiumgaming.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.data.customdifficulty.usecase.UpdateCustomDifficultyUseCase
import com.tritiumgaming.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.data.globalpreferences.source.datastore.GlobalPreferencesDatastoreDataSource
import com.tritiumgaming.data.language.repository.LanguageRepository
import com.tritiumgaming.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.data.language.source.datastore.LanguageDatastoreDataSource
import com.tritiumgaming.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.data.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.data.marketplace.bundle.repository.MarketCatalogBundleRepository
import com.tritiumgaming.data.marketplace.bundle.repository.MarketCatalogBundleRepositoryImpl
import com.tritiumgaming.data.marketplace.bundle.source.remote.MarketBundleFirestoreDataSourceImpl
import com.tritiumgaming.data.marketplace.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.data.marketplace.palette.repository.MarketCatalogPaletteRepository
import com.tritiumgaming.data.marketplace.palette.repository.MarketCatalogPaletteRepositoryImpl
import com.tritiumgaming.data.marketplace.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.data.marketplace.palette.usecase.FetchUnlockedPalettesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.data.marketplace.typography.repository.MarketCatalogTypographyRepository
import com.tritiumgaming.data.marketplace.typography.repository.MarketCatalogTypographyRepositoryImpl
import com.tritiumgaming.data.marketplace.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.data.marketplace.typography.usecase.FetchUnlockedTypographiesUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.data.mission.repository.MissionRepository
import com.tritiumgaming.data.mission.repository.MissionRepositoryImpl
import com.tritiumgaming.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.data.mission.usecase.FetchAllMissionsUseCase
import com.tritiumgaming.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.data.newsletter.source.datastore.NewsletterDatastoreDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSourceImpl
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.data.newsletter.usecase.GetNewsletterLastFetchDateFlowUseCase
import com.tritiumgaming.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.data.palette.source.local.PaletteLocalDataSourceImpl
import com.tritiumgaming.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.data.review.repository.ReviewTrackerRepository
import com.tritiumgaming.data.review.repository.ReviewTrackerRepositoryImpl
import com.tritiumgaming.data.review.source.ReviewTrackerDatastore
import com.tritiumgaming.data.review.source.datastore.ReviewTrackerDatastoreDataSource
import com.tritiumgaming.data.review.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.data.review.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.data.review.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.data.review.usecase.timesopened.IncrementAppTimesOpenedByUseCase
import com.tritiumgaming.data.review.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.data.typography.source.local.TypographyLocalDataSourceImpl
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.data.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.data.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.data.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.data.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.data.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.data.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.data.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.data.usecase.SetEnableRTLUseCase
import com.tritiumgaming.data.usecase.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.data.usecase.SetUiDensityTypeUseCase
import com.tritiumgaming.database.LocalDatabase
import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.repository.impl.OperationRepositoryImpl
import com.tritiumstudios.data.operation.usecase.GetOperationStateUseCase
import com.tritiumstudios.data.operation.usecase.ResetOperationUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationDifficultyUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationEvidenceUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationGhostDetailsUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationHuntWarningUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationMapUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationMissionDataUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationOverridesUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationPhaseUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationSanityUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationTemperatureUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationWeatherUseCase
import com.tritiumstudios.data.operation.usecase.bundle.InvestigationUseCaseBundle
import com.tritiumstudios.data.operation.usecase.bundle.MissionsUseCaseBundle
import com.tritiumstudios.data.policy.repository.PolicyRepository
import com.tritiumstudios.data.policy.repository.PolicyRepositoryImpl
import com.tritiumstudios.data.policy.source.datastore.PolicyDatastoreDataSource
import com.tritiumstudios.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumstudios.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumstudios.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumstudios.data.policy.usecase.InitializeMobileAdsUseCase
import com.tritiumstudios.data.policy.usecase.IsPrivacyOptionsRequiredUseCase
import com.tritiumstudios.data.policy.usecase.SetAllowAnalyticsUseCase
import com.tritiumstudios.data.policy.usecase.SetAllowPersonalizedAdsUseCase
import com.tritiumstudios.data.policy.usecase.ShowPrivacyOptionsFormUseCase
import com.tritiumstudios.data.wearable.repository.WearableRepository
import com.tritiumstudios.data.wearable.repository.WearableRepositoryImpl
import com.tritiumstudios.data.wearable.usecase.ObserveWearableOperationDataUseCase
import com.tritiumstudios.data.wearable.usecase.PushOperationDataToWearableUseCase
import com.tritiumstudios.data.wearable.usecase.SendWearableToggleMessageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CoreContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>,
    firestore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth,
    firebaseFunctions: FirebaseFunctions,
    localDatabase: LocalDatabase
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val googleMobileAdsConsentManager =
        GoogleMobileAdsConsentManager.getInstance(applicationContext)

    private val customDifficultyRepository: CustomDifficultyRepository by lazy {
        CustomDifficultyRepositoryImpl(
            customDifficultyDao = localDatabase.customDifficultyDao(),
            scope = coroutineScope
        )
    }

    val getCustomDifficultiesUseCase = GetCustomDifficultiesUseCase(
        repository = customDifficultyRepository
    )

    val updateCustomDifficultyUseCase = UpdateCustomDifficultyUseCase(
        repository = customDifficultyRepository
    )
    // Challenges
    private val challengeRepository: ChallengeRepository by lazy {
        val challengeLocalDataSource: ChallengeDataSource = ChallengeLocalDataSource()

        ChallengeRepositoryImpl(
            localSource = challengeLocalDataSource
        )
    }
    val getChallengesUseCase = GetChallengesUseCase(
        repository = challengeRepository
    )
    val getCurrentChallengeUseCase = GetCurrentChallengeUseCase(
        useCase = getChallengesUseCase
    )

    // Newsletter
    private val newsletterRepository: NewsletterRepository by lazy {
        val newsletterLocalDataSource: NewsletterLocalDataSource = NewsletterLocalDataSourceImpl(
            applicationContext = applicationContext
        )
        val newsletterRemoteDataSource: NewsletterRemoteDataSource = NewsletterRemoteDataSourceImpl(
            newsletterApi = NewsletterService(),
            dispatcher = Dispatchers.IO
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
    /*val setupNewsletterUseCase = SetupNewsletterUseCase(
        repository = newsletterRepository
    )*/
    val getFlowNewsletterDatastoreUseCase = GetFlowNewsletterDatastoreUseCase(
        repository = newsletterRepository
    )
    val getFlowNewsletterInboxesUseCase = GetFlowNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    val getNewsletterLastFetchDateFlowUseCase = GetNewsletterLastFetchDateFlowUseCase(
        repository = newsletterRepository
    )
    val getNewsletterInboxesUseCase = FetchNewsletterInboxesUseCase(
        repository = newsletterRepository
    )
    val saveNewsletterInboxLastReadDateUseCase = SaveNewsletterInboxLastReadDateUseCase(
        repository = newsletterRepository
    )

    // Rewarded Ads
    private val rewardedAdRepository: RewardedAdRepository by lazy {
        RewardedAdRepositoryImpl(
            RewardedAdsResources.AdUnitID.REWARDED_AD_1.asString()
        ).apply {
            loadAd(applicationContext)
        }
    }

    val loadRewardedAdUseCase = LoadRewardedAdUseCase(rewardedAdRepository)
    val showRewardedAdUseCase = ShowRewardedAdUseCase(rewardedAdRepository)
    val getRewardedAdFlowUseCase = GetRewardedAdFlowUseCase(rewardedAdRepository)


    val globalPreferencesRepository: GlobalPreferencesRepository by lazy {
        val globalPreferencesDataSource = GlobalPreferencesDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        GlobalPreferencesRepositoryImpl(
            dataStoreSource = globalPreferencesDataSource
        )
    }

    val initFlowGlobalPreferencesUseCase = InitFlowUserPreferencesUseCase(
        repository = globalPreferencesRepository
    )
    val setAllowCellularDataUseCase = SetAllowCellularDataUseCase(
        repository = globalPreferencesRepository
    )
    val setAllowIntroductionUseCase = SetAllowIntroductionUseCase(
        repository = globalPreferencesRepository
    )
    val setDisableScreenSaverUseCase = SetDisableScreenSaverUseCase(
        repository = globalPreferencesRepository
    )
    val setEnableGhostReorderUseCase = SetEnableGhostReorderUseCase(
        repository = globalPreferencesRepository
    )
    val setEnableRTLUseCase = SetEnableRTLUseCase(
        repository = globalPreferencesRepository
    )
    val setUiDensityTypeUseCase = SetUiDensityTypeUseCase(
        repository = globalPreferencesRepository
    )
    val setAllowHuntWarnAudioUseCase = SetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository
    )
    val setMaxHuntWarnFlashTimeUseCase = SetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository
    )
    val saveCurrentTypographyUseCase = SaveCurrentTypographyUseCase(
        repository = globalPreferencesRepository
    )
    val saveCurrentPaletteUseCase = SaveCurrentPaletteUseCase(
        repository = globalPreferencesRepository
    )

    /**
     * Policy
     */
    val policyRepository: PolicyRepository by lazy {
        val policyDataSource = PolicyDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )

        PolicyRepositoryImpl(
            dataStoreSource = policyDataSource,
            googleMobileAdsConsentManager = googleMobileAdsConsentManager
        )
    }

    val initFlowPolicyUseCase = InitFlowPolicyUseCase(
        repository = policyRepository
    )
    val applyPolicyUseCase = ApplyPolicyUseCase(
        repository = policyRepository
    )
    val setAllowAnalyticsUseCase = SetAllowAnalyticsUseCase(
        repository = policyRepository
    )
    val setAllowPersonalizedAdsUseCase = SetAllowPersonalizedAdsUseCase(
        repository = policyRepository
    )
    val isPrivacyOptionsRequiredUseCase = IsPrivacyOptionsRequiredUseCase(
        repository = policyRepository
    )
    val showPrivacyOptionsFormUseCase = ShowPrivacyOptionsFormUseCase(
        repository = policyRepository
    )
    val gatherAdsConsentUseCase = GatherAdsConsentUseCase(
        repository = policyRepository
    )
    val initializeMobileAdsUseCase = InitializeMobileAdsUseCase(
        repository = policyRepository
    )

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
        credentialsRepository = credentialsRepository
    )
    val signInAccountUseCase = SignInAccountUseCase(
        credentialsRepository = credentialsRepository
    )
    val signOutAccountUseCase = SignOutAccountUseCase(
        credentialsRepository = credentialsRepository
    )
    val deactivateAccountUseCase = DeactivateAccountUseCase(
        credentialsRepository = credentialsRepository
    )

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
            firebaseAuth = firebaseAuth,
            firebaseFunctions = firebaseFunctions
        )

        FirestoreAccountRepositoryImpl(
            authRemoteDataSource = firestoreAuthRemoteDataSource,
            userRemoteDataSource = firestoreUserRemoteDataSource,
            accountRemoteDataSource = firestoreAccountDataSource,
            scope = coroutineScope
        )
    }


    val setMarketplaceAgreementStateUseCase = SetMarketplaceAgreementStateUseCase(
        repository = firestoreAccountRepository
    )
    val observeMarketplaceAgreementStateUseCase = ObserveMarketplaceAgreementStateUseCase(
        repository = firestoreAccountRepository
    )
    val addAccountCreditsUseCase = AddAccountCreditsUseCase(
        repository = firestoreAccountRepository
    )
    val removeAccountCreditsUseCase = RemoveAccountCreditsUseCase(
        repository = firestoreAccountRepository
    )
    val observeAccountCreditsUseCase = ObserveAccountCreditsUseCase(
        repository = firestoreAccountRepository
    )
    val observeAccountUnlockedPalettesUseCase = ObserveAccountUnlockedPalettesUseCase(
        repository = firestoreAccountRepository
    )
    val observeAccountUnlockedTypographiesUseCase = ObserveAccountUnlockedTypographiesUseCase(
        repository = firestoreAccountRepository
    )
    val purchaseMarketplaceItemUseCase = PurchaseMarketplaceItemUseCase(
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

    /*val setupReviewTrackerUseCase = SetupReviewTrackerUseCase(
        repository = reviewTrackerRepository
    )*/
    val initializeReviewTrackerUseCase = InitFlowReviewTrackerUseCase(
        repository = reviewTrackerRepository
    )
    val setReviewRequestStatusUseCase = SetReviewRequestStatusUseCase(
        repository = reviewTrackerRepository
    )
    val setAppTimeAliveUseCase = SetAppTimeAliveUseCase(
        repository = reviewTrackerRepository
    )
    val setAppTimesOpenedUseCase = SetAppTimesOpenedUseCase(
        repository = reviewTrackerRepository
    )
    val incrementAppTimesOpenedUseCase = IncrementAppTimesOpenedByUseCase(
        repository = reviewTrackerRepository
    )

    /**
     *  Market Bundle
     */
    internal val bundleRepository: MarketCatalogBundleRepository by lazy {
        val firestoreBundleDataSource = MarketBundleFirestoreDataSourceImpl(
            firebaseFunctions = firebaseFunctions
        )

        MarketCatalogBundleRepositoryImpl(
            firestoreDataSource = firestoreBundleDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val getMarketCatalogBundlesUseCase = GetMarketCatalogBundlesUseCase(
        repository = bundleRepository
    )

    /**
     * Market Typography
     */
    internal val typographyRepository: MarketCatalogTypographyRepository by lazy {
        val typographyLocalDataSource = TypographyLocalDataSourceImpl()
        val typographyFirestoreDataSource = MarketTypographyFirestoreDataSource(
            firebaseFunctions = firebaseFunctions
        )

        MarketCatalogTypographyRepositoryImpl(
            localDataSource = typographyLocalDataSource,
            firestoreDataSource = typographyFirestoreDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val fetchUnlockedTypographiesUseCase = FetchUnlockedTypographiesUseCase(
        marketRepository = typographyRepository,
        accountRepository = firestoreAccountRepository
    )
    val getNextUnlockedTypographyUseCase = GetNextUnlockedTypographyUseCase()
    val getMarketCatalogTypographiesUseCase = GetMarketCatalogTypographiesUseCase(
        repository = typographyRepository
    )
    val getMarketCatalogTypographyByUUIDUseCase = GetMarketCatalogTypographyByUUIDUseCase(
        repository = typographyRepository
    )

    /**
     * Market Palette
     */
    internal val paletteRepository: MarketCatalogPaletteRepository by lazy {
        val paletteLocalDataSource = PaletteLocalDataSourceImpl()
        val paletteFirestoreDataSource = MarketPaletteFirestoreDataSource(
            firebaseFunctions = firebaseFunctions
        )

        MarketCatalogPaletteRepositoryImpl(
            localDataSource = paletteLocalDataSource,
            firestoreDataSource = paletteFirestoreDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val fetchUnlockedPaletteUseCase = FetchUnlockedPalettesUseCase(
        marketRepository = paletteRepository,
        accountRepository = firestoreAccountRepository
    )
    val findNextAvailablePaletteUseCase = GetNextUnlockedPaletteUseCase()
    val getMarketCatalogPalettesUseCase = GetMarketCatalogPalettesUseCase(
        repository = paletteRepository
    )
    val getMarketCatalogPaletteByUUIDUseCase = GetMarketCatalogPaletteByUUIDUseCase(
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
    val initFlowLanguageUseCase = InitFlowLanguageUseCase(
        repository = languageRepository
    )
    val saveCurrentLanguageUseCase = SaveCurrentLanguageUseCase(
        repository = languageRepository
    )

    private val operationRepository: OperationRepository by lazy {
        OperationRepositoryImpl()
    }

    private val missionRepository: MissionRepository by lazy {
        val missionLocalDataSource = MissionLocalDataSource(
            applicationContext = applicationContext
        )
        MissionRepositoryImpl(
            localSource = missionLocalDataSource
        )
    }

    private val wearableRepository: WearableRepository by lazy {
        WearableRepositoryImpl(applicationContext)
    }

    val pushOperationDataToWearableUseCase = PushOperationDataToWearableUseCase(
        repository = wearableRepository
    )
    val sendWearableToggleMessageUseCase = SendWearableToggleMessageUseCase(
        repository = wearableRepository
    )
    val observeWearableOperationDataUseCase = ObserveWearableOperationDataUseCase(
        repository = wearableRepository
    )

    val investigationUseCaseBundle = InvestigationUseCaseBundle(
        getOperationStateUseCase = GetOperationStateUseCase(
            repository = operationRepository
        ),
        updateOperationMapUseCase = UpdateOperationMapUseCase(
            repository = operationRepository
        ),
        updateOperationDifficultyUseCase = UpdateOperationDifficultyUseCase(
            repository = operationRepository
        ),
        updateOperationSanityUseCase = UpdateOperationSanityUseCase(
            repository = operationRepository
        ),
        updateOperationPhaseUseCase = UpdateOperationPhaseUseCase(
            repository = operationRepository
        ),
        updateOperationHuntWarningUseCase = UpdateOperationHuntWarningUseCase(
            repository = operationRepository
        ),
        updateOperationEvidenceUseCase = UpdateOperationEvidenceUseCase(
            repository = operationRepository
        ),
        updateOperationGhostDetailsUseCase = UpdateOperationGhostDetailsUseCase(
            repository = operationRepository
        ),
        updateOperationMissionDataUseCase = UpdateOperationMissionDataUseCase(
            repository = operationRepository
        ),
        updateOperationOverridesUseCase = UpdateOperationOverridesUseCase(
            repository = operationRepository
        ),
        updateOperationWeatherUseCase = UpdateOperationWeatherUseCase(
            repository = operationRepository
        ),
        updateOperationTemperatureUseCase = UpdateOperationTemperatureUseCase(
            repository = operationRepository
        ),
        fetchAllMissionsUseCase = FetchAllMissionsUseCase(
            missionRepository = missionRepository
        ),
        resetOperationUseCase = ResetOperationUseCase(
            repository = operationRepository
        ),
        getCustomDifficultiesUseCase = getCustomDifficultiesUseCase
    )

    val missionsUseCaseBundle = MissionsUseCaseBundle(
        getOperationStateUseCase = GetOperationStateUseCase(
            repository = operationRepository
        ),
        updateOperationGhostDetailsUseCase = UpdateOperationGhostDetailsUseCase(
            repository = operationRepository
        ),
        updateOperationMissionDataUseCase = UpdateOperationMissionDataUseCase(
            repository = operationRepository
        ),
    )

}