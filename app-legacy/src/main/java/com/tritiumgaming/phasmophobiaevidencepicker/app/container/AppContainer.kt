package com.tritiumgaming.phasmophobiaevidencepicker.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class AppContainer(context: Context, dataStore: DataStore<Preferences>) {

    /*
    val appInfoRepository: AppInfoRepository =
        AppInfoRepository(
            context = context
        )

    val newsletterRepository: NewsletterRepository =
        NewsletterRepository(
            context = context,
            dataStore = dataStore
        )

    val globalPreferencesRepository: GlobalPreferencesRepository =
        GlobalPreferencesRepository(
            context = context,
            dataStore = dataStore
        )

    val reviewTrackingRepository: ReviewTrackingRepository =
        ReviewTrackingRepository(
            context = context,
            dataStore = dataStore
        )

    val typographyRepository: TypographyRepository =
        TypographyRepository(
            context = context,
            dataStore = dataStore
        )

    val paletteRepository: PaletteRepository =
        PaletteRepository(
            networkSource = NetworkMarketDataSource(),
            localSource = LocalPalettesMap,
            context = context,
            dataStore = dataStore
        )

    val languageRepository: LanguageRepository =
        LanguageRepository(
            context = context,
            dataStore = dataStore
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

    val mapRepository: MapRepository =
        MapRepository(
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
    */

}