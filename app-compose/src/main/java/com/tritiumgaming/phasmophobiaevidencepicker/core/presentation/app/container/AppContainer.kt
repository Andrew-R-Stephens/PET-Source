package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.local.GlobalPreferencesDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.LanguageLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.MarketRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.local.ReviewTrackingDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.language.LanguageManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme.TypographyManager
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository.AppInfoRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local.AppInfoLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.SimpleMapRepository

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class AppContainer(context: Context, dataStore: DataStore<Preferences>) {

    val appInfoRepository: AppInfoRepository =
        AppInfoRepository(
            context = context,
            localSource = AppInfoLocalDataSource()
        )

    val newsletterRepository: NewsletterRepository =
        NewsletterRepository(
            context = context
        )
    val newsletterDatastore: NewsletterDatastore =
        NewsletterDatastore(
            context = context,
            dataStore = dataStore
        )

    val reviewTrackingRepository: ReviewTrackingDatastore =
        ReviewTrackingDatastore(
            context = context,
            dataStore = dataStore
        )

    val globalPreferencesRepository: GlobalPreferencesRepository =
        GlobalPreferencesRepository()
    val globalPreferencesDatastore: GlobalPreferencesDatastore =
        GlobalPreferencesDatastore(
            context = context,
            dataStore = dataStore
        )
    val globalPreferencesManager: GlobalPreferencesManager = GlobalPreferencesManager(
        repository = globalPreferencesRepository,
        datastore = globalPreferencesDatastore
    )

    val typographyDatastore = TypographyDatastore(
        context = context,
        dataStore = dataStore
    )
    val typographyRepository: TypographyRepository =
        TypographyRepository(
            context = context,
            networkDataSource = MarketRemoteDataSource(),
            localDataSource = TypographyLocalDataSource(),
        )
    val typographyManager: TypographyManager = TypographyManager(
        repository = typographyRepository,
        datastore = typographyDatastore
    )

    val paletteDatastore = PaletteDatastore(
        context = context,
        dataStore = dataStore
    )
    val paletteRepository: PaletteRepository =
        PaletteRepository(
            context = context,
            remoteDataSource = MarketRemoteDataSource(),
            localDataSource = PaletteLocalDataSource(),
        )
    val paletteManager: PaletteManager = PaletteManager(
        repository = paletteRepository,
        datastore = paletteDatastore
    )

    val languageRepository: LanguageRepository =
        LanguageRepository(
            context = context,
            languageLocalDataSource = LanguageLocalDataSource()
        )
    val languageDatastore = LanguageDatastore(
        context = context,
        dataStore = dataStore
    )
    val languageManager: LanguageManager = LanguageManager(
        repository = languageRepository,
        datastore = languageDatastore
    )

    val evidenceRepository: EvidenceRepository =
        EvidenceRepository(
            context = context
        )

    val ghostRepository: GhostRepository =
        GhostRepository(
            evidenceRepository = evidenceRepository,
            context = context
        )

    val difficultyRepository: DifficultyRepository =
        DifficultyRepository(
            context = context
        )

    val simpleMapRepository: SimpleMapRepository =
        SimpleMapRepository(
            context = context
        )

    val complexMapRepository: ComplexMapRepository =
        ComplexMapRepository(
            context = context
        )
    val codexRepository: CodexRepository =
        CodexRepository(
            context = context
        )

    val missionRepository: MissionRepository =
        MissionRepository(
            context = context
        )

}