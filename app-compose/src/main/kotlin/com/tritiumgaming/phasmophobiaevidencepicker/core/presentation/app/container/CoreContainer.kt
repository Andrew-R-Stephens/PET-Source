package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore.GlobalPreferencesDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.repository.MarketBundleRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSourceImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository.MarketPaletteRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.datastore.MarketPaletteDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.MarketPaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.MarketTypographyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore.MarketTypographyDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.repository.ReviewTrackerRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackerDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository.CredentialsRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository.FirestoreAccountRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.CredentialsDataSourceImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.GetAvailablePalettesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.SaveCurrentPaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup.InitFlowPaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup.SetupPaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.FindNextAvailableTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.GetAvailableTypographiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup.SetupTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit.RemoveAccountCreditsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import kotlinx.coroutines.Dispatchers

class CoreContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>,
    firestore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
) {

    // Global Preferences
    private val globalPreferencesRepository: GlobalPreferencesRepository =
        GlobalPreferencesRepositoryImpl(
            dataStoreSource = GlobalPreferencesDatastoreDataSource(
                context = applicationContext,
                dataStore = dataStore
            )
        )
    internal val setupGlobalPreferencesUseCase = SetupGlobalPreferencesUseCase(
        repository = globalPreferencesRepository)
    internal val initFlowGlobalPreferencesUseCase = InitFlowGlobalPreferencesUseCase(
        repository = globalPreferencesRepository)
    internal val setAllowCellularDataUseCase = SetAllowCellularDataUseCase(
        repository = globalPreferencesRepository)
    internal val setAllowHuntWarnAudioUseCase = SetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository)
    internal val setAllowIntroductionUseCase = SetAllowIntroductionUseCase(
        repository = globalPreferencesRepository)
    internal val setDisableScreenSaverUseCase = SetDisableScreenSaverUseCase(
        repository = globalPreferencesRepository)
    internal val setEnableGhostReorderUseCase = SetEnableGhostReorderUseCase(
        repository = globalPreferencesRepository)
    internal val setEnableRTLUseCase = SetEnableRTLUseCase(
        repository = globalPreferencesRepository)
    internal val setMaxHuntWarnFlashTimeUseCase = SetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository)

    /**
     * Credentials Service
     */
    private val credentialsDataSource = CredentialsDataSourceImpl(
        context = applicationContext
    )
    private val credentialsRepository = CredentialsRepositoryImpl(
        credentialsDataSource = credentialsDataSource
    )
    internal val getSignInCredentialsUseCase = GetSignInCredentialsUseCase(
        credentialsRepository = credentialsRepository)
    internal val signInAccountUseCase = SignInAccountUseCase(
        credentialsRepository = credentialsRepository)
    internal val signOutAccountUseCase = SignOutAccountUseCase(
        credentialsRepository = credentialsRepository)
    internal val deactivateAccountUseCase = DeactivateAccountUseCase(
        credentialsRepository = credentialsRepository)

    /**
     * Account
     */
    private val firestoreUserRemoteDataSource = FirestoreUserRemoteDataSource(
        firestore = firestore
    )
    private val firestoreAuthRemoteDataSource = FirestoreAuthRemoteDataSource(
        firebaseAuth = firebaseAuth
    )
    private val firestoreAccountDataSource = FirestoreAccountRemoteDataSource(
        firestore = firestore,
        firebaseAuth = firebaseAuth
    )
    private val firestoreAccountRepository = FirestoreAccountRepositoryImpl(
        authRemoteDataSource = firestoreAuthRemoteDataSource,
        userRemoteDataSource = firestoreUserRemoteDataSource,
        accountRemoteDataSource = firestoreAccountDataSource
    )
    internal val setMarketplaceAgreementStateUseCase = SetMarketplaceAgreementStateUseCase(
        repository = firestoreAccountRepository)
    internal val addAccountCreditsUseCase = AddAccountCreditsUseCase(
        repository = firestoreAccountRepository)
    internal val removeAccountCreditsUseCase = RemoveAccountCreditsUseCase(
        repository = firestoreAccountRepository)
    internal val observeAccountCreditsUseCase = ObserveAccountCreditsUseCase(
        repository = firestoreAccountRepository)
    internal val observeAccountUnlockedPalettesUseCase =
        ObserveAccountUnlockedPalettesUseCase(
            repository = firestoreAccountRepository)
    internal val observeAccountUnlockedTypographiesUseCase =
        ObserveAccountUnlockedTypographiesUseCase(
            repository = firestoreAccountRepository)

    /**
     * Review Tracker
     */
    private val reviewTrackerRepository: ReviewTrackerRepository =
        ReviewTrackerRepositoryImpl(
            dataStoreSource = ReviewTrackerDatastoreDataSource(
                context = applicationContext,
                dataStore = dataStore
            )
        )
    internal val getReviewRequestStatusUseCase = GetReviewRequestStatusUseCase(
        repository = reviewTrackerRepository)
    internal val loadReviewRequestStatusUseCase = LoadReviewRequestStatusUseCase(
        repository = reviewTrackerRepository)
    internal val setupReviewTrackerUseCase = SetupReviewTrackerUseCase(
        repository = reviewTrackerRepository)
    internal val initializeReviewTrackerUseCase = InitFlowReviewTrackerUseCase(
        repository = reviewTrackerRepository)
    internal val setReviewRequestStatusUseCase = SetReviewRequestStatusUseCase(
        repository = reviewTrackerRepository)
    internal val setAppTimeAliveUseCase = SetAppTimeAliveUseCase(
        repository = reviewTrackerRepository)
    internal val getAppTimeAliveUseCase = GetAppTimeAliveUseCase(
        repository = reviewTrackerRepository)
    internal val loadAppTimeAliveUseCase = LoadAppTimeAliveUseCase(
        repository = reviewTrackerRepository)
    internal val setAppTimesOpenedUseCase = SetAppTimesOpenedUseCase(
        repository = reviewTrackerRepository)
    internal val getAppTimesOpenedUseCase = GetAppTimesOpenedUseCase(
        repository = reviewTrackerRepository)
    internal val loadAppTimesOpenedUseCase = LoadAppTimesOpenedUseCase(
        repository = reviewTrackerRepository)

    /**
     *  Market Bundle
     */
    private val firestoreBundleDataSource = MarketBundleFirestoreDataSourceImpl(
        firestore = firestore
    )
    private val bundleRepository = MarketBundleRepositoryImpl(
        firestoreDataSource = firestoreBundleDataSource
    )

    /**
     * Market Typography
     */
    private val typographyLocalDataSource = MarketTypographyLocalDataSource()
    private val typographyFirestoreDataSource = MarketTypographyFirestoreDataSource(
        firestore = firestore
    )
    private val typographyDatastoreDataSource = MarketTypographyDatastoreDataSource(
        context = applicationContext,
        dataStore = dataStore
    )
    private val typographyRepository: MarketTypographyRepository = MarketTypographyRepositoryImpl(
        localDataSource = typographyLocalDataSource,
        firestoreDataSource = typographyFirestoreDataSource,
        dataStoreSource = typographyDatastoreDataSource,
        coroutineDispatcher = Dispatchers.IO
    )
    internal val findNextAvailableTypographyUseCase = FindNextAvailableTypographyUseCase(
        marketRepository = typographyRepository,
        accountRepository = firestoreAccountRepository
    )
    internal val setupTypographyUseCase = SetupTypographyUseCase(
        repository = typographyRepository)
    internal val initFlowTypographyUseCase = InitFlowTypographyUseCase(
        repository = typographyRepository)
    internal val saveCurrentTypographyUseCase = SaveCurrentTypographyUseCase(
        repository = typographyRepository)
    internal val getAvailableTypographiesUseCase = GetAvailableTypographiesUseCase(
        repository = typographyRepository)
    internal val getTypographyByUUIDUseCase = GetTypographyByUUIDUseCase(
        repository = typographyRepository)

    /**
     * Market Palette
     */
    private val paletteLocalDataSource = MarketPaletteLocalDataSource()
    private val paletteFirestoreDataSource = MarketPaletteFirestoreDataSource(
        firestore = firestore
    )
    private val paletteDatastoreDataSource = MarketPaletteDatastoreDataSource(
        context = applicationContext,
        dataStore = dataStore
    )
    private val paletteRepository: MarketPaletteRepository = MarketPaletteRepositoryImpl(
        localDataSource = paletteLocalDataSource,
        firestoreDataSource = paletteFirestoreDataSource,
        dataStoreSource = paletteDatastoreDataSource,
        coroutineDispatcher = Dispatchers.IO
    )
    internal val findNextAvailablePaletteUseCase = FindNextAvailablePaletteUseCase(
        marketRepository = paletteRepository,
        accountRepository = firestoreAccountRepository
    )
    internal val setupPaletteUseCase = SetupPaletteUseCase(
        repository = paletteRepository)
    internal val initFlowPaletteUseCase = InitFlowPaletteUseCase(
        repository = paletteRepository)
    internal val saveCurrentPaletteUseCase = SaveCurrentPaletteUseCase(
        repository = paletteRepository)
    internal val getAvailablePalettesUseCase = GetAvailablePalettesUseCase(
        repository = paletteRepository)
    internal val getPaletteByUUIDUseCase = GetPaletteByUUIDUseCase(
        repository = paletteRepository)


    /**
     * Language
     */
    private val languageLocalDataSource = LanguageLocalDataSource(applicationContext)
    private val languageDatastoreDataSource = LanguageDatastoreDataSource(
        context = applicationContext,
        dataStore = dataStore
    )
    private val languageRepository: LanguageRepository = LanguageRepositoryImpl(
        localDataSource = languageLocalDataSource,
        dataStoreSource = languageDatastoreDataSource
    )
    internal val getLanguagesUseCase = GetAvailableLanguagesUseCase(
        repository = languageRepository)
    internal val setupLanguageUseCase = SetupLanguageUseCase(
        repository = languageRepository)
    internal val initializeLanguageUseCase = InitFlowLanguageUseCase(
        repository = languageRepository)
    internal val setCurrentLanguageUseCase = SaveCurrentLanguageUseCase(
        repository = languageRepository)
    internal val getCurrentLanguageUseCase = GetCurrentLanguageUseCase(
        repository = languageRepository)
    internal val loadCurrentLanguageUseCase = LoadCurrentLanguageUseCase(
        repository = languageRepository)

}
