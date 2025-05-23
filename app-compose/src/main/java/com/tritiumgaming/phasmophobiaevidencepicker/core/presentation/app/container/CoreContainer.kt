package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore.GlobalPreferencesDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.BundleRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository.PaletteRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.datastore.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.PaletteRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.TypographyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore.TypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.repository.ReviewTrackerDatastoreRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackerDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetLanguagesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.GetPalettesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.FindNextAvailablePaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.GetTypographyUsecase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.FindNextAvailableTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.language.LanguageManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.TypographyManager

class CoreContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    // Global Preferences
    private val globalPreferencesRepository: GlobalPreferencesRepository =
        GlobalPreferencesRepositoryImpl()
    private val globalPreferencesDatastore: GlobalPreferencesDatastore =
        GlobalPreferencesDatastore(
            context = applicationContext,
            dataStore = dataStore
        )
    val globalPreferencesManager: GlobalPreferencesManager = GlobalPreferencesManager(
        repository = globalPreferencesRepository,
        datastore = globalPreferencesDatastore
    )

    // Review Tracker
    val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository =
        ReviewTrackerDatastoreRepositoryImpl(
            dataStoreSource = ReviewTrackerDatastoreDataSource(
                context = applicationContext,
                datastore = dataStore
            )
        )
    val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase = GetReviewRequestStatusUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase = LoadReviewRequestStatusUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val setupReviewTrackerUseCase = SetupReviewTrackerUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val initializeReviewTrackerUseCase = InitFlowReviewTrackerUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase = SetReviewRequestStatusUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val setAppTimeAliveUseCase: SetAppTimeAliveUseCase = SetAppTimeAliveUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val getAppTimeAliveUseCase: GetAppTimeAliveUseCase = GetAppTimeAliveUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase = LoadAppTimeAliveUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val setAppTimesOpenedUseCase = SetAppTimesOpenedUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val getAppTimesOpenedUseCase = GetAppTimesOpenedUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )
    val loadAppTimesOpenedUseCase = LoadAppTimesOpenedUseCase(
        reviewTrackerDatastoreRepository = reviewTrackerDatastoreRepository
    )

    // Market Bundle
    private val bundleRemoteDataSource: BundleDataSource = BundleRemoteDataSource()

    // Market Typography
    private val typographyLocalDataSource = TypographyLocalDataSource()
    private val typographyRemoteDataSource = TypographyRemoteDataSource()
    private val typographyDatastore = TypographyDatastore(
        context = applicationContext,
        dataStore = dataStore
    )
    private val typographyRepository: TypographyRepository =
        TypographyRepositoryImpl(
            networkDataSource = typographyRemoteDataSource,
            localDataSource = typographyLocalDataSource

        )
    //Market Typography Use Cases
    private val fetchTypographiesUseCase = GetTypographyUsecase(
        repository = typographyRepository
    )
    private val findNextAvailableTypographyUseCase = FindNextAvailableTypographyUseCase()
    private val getTypographyByUUIDUseCase = GetTypographyByUUIDUseCase()
    val typographyManager: TypographyManager = TypographyManager(
        datastore = typographyDatastore,
        getTypographiesUseCase = fetchTypographiesUseCase,
        getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
        findNextAvailableTypographyUseCase = findNextAvailableTypographyUseCase
    )

    // Market Palette
    private val paletteLocalDataSource = PaletteLocalDataSource()
    private val paletteRemoteDataSource = PaletteRemoteDataSource()
    private val paletteDatastore = PaletteDatastore(
        context = applicationContext,
        dataStore = dataStore
    )
    private val paletteRepository: PaletteRepository =
        PaletteRepositoryImpl(
            remotePaletteSource = paletteRemoteDataSource,
            localPaletteSource = paletteLocalDataSource
        )
    //Market Palette Use Cases
    private val fetchPalettesUseCase = GetPalettesUseCase(
        repository = paletteRepository
    )
    private val findNextAvailablePaletteUseCase = FindNextAvailablePaletteUseCase()
    private val getPaletteByUUIDUseCase = GetPaletteByUUIDUseCase()
    // Market Palette Manager
    val paletteManager: PaletteManager = PaletteManager(
        datastore = paletteDatastore,
        getPalettesUseCase = fetchPalettesUseCase,
        getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
        findNextAvailablePaletteUseCase = findNextAvailablePaletteUseCase
    )

    // Language
    val languageDataSource = LanguageLocalDataSource(applicationContext)
    private val languageRepository: LanguageRepository = LanguageRepositoryImpl(
            dataSource = languageDataSource
        )
    private val languageDatastore = LanguageDatastore(
        context = applicationContext,
        dataStore = dataStore
    )
    val getLanguagesUseCase = GetLanguagesUseCase(repository = languageRepository)
    val languageManager: LanguageManager = LanguageManager(
        datastore = languageDatastore,
        getLanguagesUseCase = getLanguagesUseCase
    )

}
