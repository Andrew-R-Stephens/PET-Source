package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore.GlobalPreferencesDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.BundleRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository.PaletteRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.datastore.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.PaletteRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.TypographyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore.TypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackingDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.language.LanguageManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme.TypographyManager

class CoreContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

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

    val reviewTrackingRepository: ReviewTrackingDatastore =
        ReviewTrackingDatastore(
            context = applicationContext,
            dataStore = dataStore
        )

    private val bundleRemoteDataSource: BundleDataSource = BundleRemoteDataSource()

    private val typographyLocalDataSource = TypographyLocalDataSource()
    private val typographyRemoteDataSource = TypographyRemoteDataSource()
    private val typographyDatastore = TypographyDatastore(
        context = applicationContext,
        dataStore = dataStore
    )
    private val typographyRepository: TypographyRepositoryImpl =
        TypographyRepositoryImpl(
            networkDataSource = typographyRemoteDataSource,
            localDataSource = typographyLocalDataSource

        )
    val typographyManager: TypographyManager = TypographyManager(
        repository = typographyRepository,
        datastore = typographyDatastore
    )

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
    val paletteManager: PaletteManager = PaletteManager(
        repository = paletteRepository,
        datastore = paletteDatastore
    )

    val languageDataSource = LanguageLocalDataSource(applicationContext)
    private val languageRepository: LanguageRepository = LanguageRepositoryImpl(
            dataSource = languageDataSource
        )
    private val languageDatastore = LanguageDatastore(
        context = applicationContext,
        dataStore = dataStore
    )
    val languageManager: LanguageManager = LanguageManager(
        repository = languageRepository,
        datastore = languageDatastore
    )

}
