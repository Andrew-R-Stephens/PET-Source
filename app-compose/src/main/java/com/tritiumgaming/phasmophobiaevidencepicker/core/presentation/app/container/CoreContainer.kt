package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore.GlobalPreferencesDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.BundleRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository.PaletteRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.datastore.PaletteDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.PaletteRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.TypographyDatastoreRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.TypographyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore.TypographyDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.repository.ReviewTrackerRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackerDatastoreDataSource
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetLanguagesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.GetPalettesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.FindNextAvailableTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.GetTypographiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup.SetupTypographyUseCase
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.PaletteManager

class CoreContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    // Global Preferences
    private val globalPreferencesRepository: GlobalPreferencesRepository =
        GlobalPreferencesRepositoryImpl(
            dataStoreSource = GlobalPreferencesDatastoreDataSource(
                context = applicationContext,
                dataStore = dataStore
            )
        )
    val setupGlobalPreferencesUseCase = SetupGlobalPreferencesUseCase(
        repository = globalPreferencesRepository)
    val initFlowGlobalPreferencesUseCase = InitFlowGlobalPreferencesUseCase(
        repository = globalPreferencesRepository)
    val setAllowCellularDataUseCase = SetAllowCellularDataUseCase(
        repository = globalPreferencesRepository)
    val setAllowHuntWarnAudioUseCase = SetAllowHuntWarnAudioUseCase(
        repository = globalPreferencesRepository)
    val setAllowIntroductionUseCase = SetAllowIntroductionUseCase(
        repository = globalPreferencesRepository)
    val setDisableScreenSaverUseCase = SetDisableScreenSaverUseCase(
        repository = globalPreferencesRepository)
    val setEnableGhostReorderUseCase = SetEnableGhostReorderUseCase(
        repository = globalPreferencesRepository)
    val setEnableRTLUseCase = SetEnableRTLUseCase(
        repository = globalPreferencesRepository)
    val setMaxHuntWarnFlashTimeUseCase = SetMaxHuntWarnFlashTimeUseCase(
        repository = globalPreferencesRepository)

    /**
     * Review Tracker
     */
    val reviewTrackerDatastoreRepository: ReviewTrackerRepository =
        ReviewTrackerRepositoryImpl(
            dataStoreSource = ReviewTrackerDatastoreDataSource(
                context = applicationContext,
                datastore = dataStore
            )
        )
    val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase = GetReviewRequestStatusUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase = LoadReviewRequestStatusUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val setupReviewTrackerUseCase = SetupReviewTrackerUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val initializeReviewTrackerUseCase = InitFlowReviewTrackerUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase = SetReviewRequestStatusUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val setAppTimeAliveUseCase: SetAppTimeAliveUseCase = SetAppTimeAliveUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val getAppTimeAliveUseCase: GetAppTimeAliveUseCase = GetAppTimeAliveUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase = LoadAppTimeAliveUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val setAppTimesOpenedUseCase = SetAppTimesOpenedUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val getAppTimesOpenedUseCase = GetAppTimesOpenedUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)
    val loadAppTimesOpenedUseCase = LoadAppTimesOpenedUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository)

    /**
     *  Market Bundle
     */
    private val bundleRemoteDataSource: BundleDataSource = BundleRemoteDataSource()

    /**
     * Market Typography
     */
    private val typographyLocalDataSource = TypographyLocalDataSource()
    private val typographyRemoteDataSource = TypographyRemoteDataSource()
    private val typographyDatastoreRepository = TypographyDatastoreRepositoryImpl(
        dataStoreSource = TypographyDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )
    )
    private val typographyRepository: TypographyRepository =
        TypographyRepositoryImpl(
            networkDataSource = typographyRemoteDataSource,
            localDataSource = typographyLocalDataSource

        )
    /**
     * Market Typography Use Cases
     */
    private val fetchTypographiesUseCase = GetTypographiesUseCase(
        repository = typographyRepository
    )
    internal val findNextAvailableTypographyUseCase = FindNextAvailableTypographyUseCase()
    internal val setupTypographyUseCase = SetupTypographyUseCase(
        datastoreRepository = typographyDatastoreRepository)
    internal val initFlowTypographyUseCase = InitFlowTypographyUseCase(
        datastoreRepository = typographyDatastoreRepository)
    internal val setCurrentTypographyUseCase = SaveCurrentTypographyUseCase(
        datastoreRepository = typographyDatastoreRepository)
    internal val getTypographiesUseCase = GetTypographiesUseCase(
        repository = typographyRepository)
    internal val getTypographyByUUIDUseCase = GetTypographyByUUIDUseCase()

    /**
     * Market Palette
     */
    private val paletteDatastore = PaletteDatastoreDataSource(
        context = applicationContext,
        dataStore = dataStore
    )
    private val paletteRepository: PaletteRepository =
        PaletteRepositoryImpl(
            remotePaletteSource = PaletteRemoteDataSource(),
            localPaletteSource = PaletteLocalDataSource(),
            dataStoreSource = PaletteDatastoreDataSource(
                context = applicationContext,
                dataStore = dataStore
            )
        )
    /**
     * Market Palette Use Cases
     */
    private val fetchPalettesUseCase = GetPalettesUseCase(
        repository = paletteRepository)
    private val findNextAvailablePaletteUseCase = FindNextAvailablePaletteUseCase()
    private val getPaletteByUUIDUseCase = GetPaletteByUUIDUseCase()
    // Market Palette Manager
    val paletteManager: PaletteManager = PaletteManager(
        datastore = paletteDatastore,
        getPalettesUseCase = fetchPalettesUseCase,
        getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
        findNextAvailablePaletteUseCase = findNextAvailablePaletteUseCase
    )

    /**
     * Language
     */
    private val languageRepository: LanguageRepository = LanguageRepositoryImpl(
        localDataSource = LanguageLocalDataSource(applicationContext),
        dataStoreSource = LanguageDatastoreDataSource(
            context = applicationContext,
            dataStore = dataStore
        )
    )
    val getLanguagesUseCase = GetLanguagesUseCase(
        repository = languageRepository)
    val setupLanguageUseCase = SetupLanguageUseCase(
        repository = languageRepository)
    val initializeLanguageUseCase = InitFlowLanguageUseCase(
        repository = languageRepository)
    val setCurrentLanguageUseCase = SaveCurrentLanguageUseCase(
        repository = languageRepository)
    val getCurrentLanguageUseCase = GetCurrentLanguageUseCase(
        repository = languageRepository)
    val loadCurrentLanguageUseCase = LoadCurrentLanguageUseCase(
        repository = languageRepository)

}
