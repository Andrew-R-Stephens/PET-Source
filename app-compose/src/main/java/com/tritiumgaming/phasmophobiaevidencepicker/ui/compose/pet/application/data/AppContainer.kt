package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.reviews.ReviewTrackingRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        // TODO change to real file
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class AppContainer(context: Context, dataStore: DataStore<Preferences>) {
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

    val fontThemeRepository: FontThemeRepository =
        FontThemeRepository(
            context = context,
            dataStore = dataStore
        )

    val colorThemeRepository: ColorThemeRepository =
        ColorThemeRepository(
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

}