package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository.GlobalPreferencesRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore.GlobalPreferencesDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local.LanguageLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository.PaletteRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.datastore.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.MarketRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.TypographyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore.TypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackingDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.language.LanguageManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme.TypographyManager

class CoreContainer(context: Context, dataStore: DataStore<Preferences>) {

    private val globalPreferencesRepository: GlobalPreferencesRepositoryImpl =
        GlobalPreferencesRepositoryImpl()
    private val globalPreferencesDatastore: GlobalPreferencesDatastore =
        GlobalPreferencesDatastore(
            context = context,
            dataStore = dataStore
        )
    val globalPreferencesManager: GlobalPreferencesManager = GlobalPreferencesManager(
        repository = globalPreferencesRepository,
        datastore = globalPreferencesDatastore
    )

    val reviewTrackingRepository: ReviewTrackingDatastore =
        ReviewTrackingDatastore(
            context = context,
            dataStore = dataStore
        )

    private val typographyLocalDataSource = TypographyLocalDataSource()
    private val typographyRemoteDataSource = MarketRemoteDataSource()
    private val typographyDatastore = TypographyDatastore(
        context = context,
        dataStore = dataStore
    )
    private val typographyRepository: TypographyRepositoryImpl =
        TypographyRepositoryImpl(
            context = context,
            networkDataSource = typographyRemoteDataSource,
            localDataSource = typographyLocalDataSource,
        )
    val typographyManager: TypographyManager = TypographyManager(
        repository = typographyRepository,
        datastore = typographyDatastore
    )

    private val paletteLocalDataSource = PaletteLocalDataSource()
    private val paletteRemoteDataSource = MarketRemoteDataSource()
    private val paletteDatastore = PaletteDatastore(
        context = context,
        dataStore = dataStore
    )
    private val paletteRepository: PaletteRepositoryImpl =
        PaletteRepositoryImpl(
            context = context,
            remoteDataSource = paletteRemoteDataSource,
            localDataSource = paletteLocalDataSource,
        )
    val paletteManager: PaletteManager = PaletteManager(
        repository = paletteRepository,
        datastore = paletteDatastore
    )

    private val languageRepository: LanguageRepositoryImpl = LanguageRepositoryImpl(
            context = context,
            languageLocalDataSource = LanguageLocalDataSource()
        )
    private val languageDatastore = LanguageDatastore(
        context = context,
        dataStore = dataStore
    )
    val languageManager: LanguageManager = LanguageManager(
        repository = languageRepository,
        datastore = languageDatastore
    )

}
