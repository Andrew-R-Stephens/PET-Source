package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.PreferenceKeys.KEY_CURRENT_LANGUAGE_CODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LanguageDatastore (
    context: Context,
    private val dataStore: DataStore<Preferences>
): DatastoreInterface<LanguageDatastore.LanguagePreferences> {

    override val flow: Flow<LanguageDatastore.LanguagePreferences> = dataStore.data
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        KEY_CURRENT_LANGUAGE_CODE = stringPreferencesKey(
            context.resources.getString(R.string.preference_language)
        )
    }

    suspend fun setCurrentLanguageCode(languageCode: String) {
        dataStore.edit { preferences ->
            preferences[KEY_CURRENT_LANGUAGE_CODE] = languageCode
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences) =
        LanguageDatastore.LanguagePreferences(
            preferences[KEY_CURRENT_LANGUAGE_CODE]
                ?: DEFAULT_LANGUAGE
        )

}