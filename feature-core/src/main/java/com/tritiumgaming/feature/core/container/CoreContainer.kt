package com.tritiumgaming.feature.core.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tritiumgaming.data.account.repository.CredentialsRepositoryImpl
import com.tritiumgaming.data.account.repository.FirestoreAccountRepositoryImpl
import com.tritiumgaming.data.account.source.remote.CredentialsDataSourceImpl
import com.tritiumgaming.data.account.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.data.globalpreferences.source.datastore.GlobalPreferencesDatastoreDataSource
import com.tritiumgaming.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.data.language.source.datastore.LanguageDatastoreDataSource
import com.tritiumgaming.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.data.marketplace.bundle.repository.MarketBundleRepositoryImpl
import com.tritiumgaming.data.marketplace.bundle.source.remote.MarketBundleFirestoreDataSourceImpl
import com.tritiumgaming.data.palette.repository.MarketPaletteRepositoryImpl
import com.tritiumgaming.data.palette.source.local.MarketPaletteLocalDataSource
import com.tritiumgaming.data.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.data.palette.repository.MarketTypographyRepositoryImpl
import com.tritiumgaming.data.palette.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.data.palette.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.data.review.repository.ReviewTrackerRepositoryImpl
import com.tritiumgaming.data.review.source.datastore.ReviewTrackerDatastoreDataSource
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
import com.tritiumgaming.shared.core.domain.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.shared.core.domain.market.bundle.repository.MarketBundleRemoteRepository
import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.market.palette.usecase.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.shared.core.domain.market.typography.usecase.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.SaveCurrentTypographyUseCase
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
import com.tritiumgaming.shared.core.domain.user.usecase.account.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.SignOutAccountUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.RemoveAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import kotlinx.coroutines.Dispatchers

class CoreContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>,
    firestore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
) {

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
        repository = globalPreferencesRepository
    )
    val initFlowGlobalPreferencesUseCase = InitFlowGlobalPreferencesUseCase(
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
    val setAllowHuntWarnAudioUseCase = SetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository
    )
    val setMaxHuntWarnFlashTimeUseCase = SetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository
    )
    val getEnableGhostReorderUseCase = GetEnableGhostReorderUseCase(
        repository = globalPreferencesRepository
    )
    val getEnableRTLUseCase = GetEnableRTLUseCase(
        repository = globalPreferencesRepository
    )
    val getMaxHuntWarnFlashTimeUseCase = GetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository
    )
    val getAllowHuntWarnAudioUseCase = GetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository
    )
    val saveCurrentTypographyUseCase = SaveCurrentTypographyUseCase(
        repository = globalPreferencesRepository
    )
    val saveCurrentPaletteUseCase = SaveCurrentPaletteUseCase(
        repository = globalPreferencesRepository
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
            firebaseAuth = firebaseAuth
        )

        FirestoreAccountRepositoryImpl(
            authRemoteDataSource = firestoreAuthRemoteDataSource,
            userRemoteDataSource = firestoreUserRemoteDataSource,
            accountRemoteDataSource = firestoreAccountDataSource
        )
    }


    val setMarketplaceAgreementStateUseCase = SetMarketplaceAgreementStateUseCase(
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

        MarketTypographyRepositoryImpl(
            localDataSource = typographyLocalDataSource,
            firestoreDataSource = typographyFirestoreDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val findNextAvailableTypographyUseCase = FindNextAvailableTypographyUseCase(
        marketRepository = typographyRepository,
        accountRepository = firestoreAccountRepository
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

        MarketPaletteRepositoryImpl(
            localDataSource = paletteLocalDataSource,
            firestoreDataSource = paletteFirestoreDataSource,
            coroutineDispatcher = Dispatchers.IO
        )
    }

    val findNextAvailablePaletteUseCase = FindNextAvailablePaletteUseCase(
        marketRepository = paletteRepository,
        accountRepository = firestoreAccountRepository
    )
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

}