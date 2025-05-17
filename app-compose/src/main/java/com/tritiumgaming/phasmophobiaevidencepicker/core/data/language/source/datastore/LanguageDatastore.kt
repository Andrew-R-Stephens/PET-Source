package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LanguageDatastore (
    context: Context,
    private val dataStore: DataStore<Preferences>
) {

    val flow: Flow<LanguageDatastore.LanguagePreferences> = dataStore.data
        .map { preferences ->
            mapPreferences(preferences)
        }

    fun initialSetupEvent() {
        liveData {
            emit(fetchInitialPreferences())
        }
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

    suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences) =
        LanguageDatastore.LanguagePreferences(
            preferences[KEY_CURRENT_LANGUAGE_CODE]
                ?: LanguageDataSource.Companion.DEFAULT_LANGUAGE
        )

    private companion object {
        lateinit var KEY_CURRENT_LANGUAGE_CODE: Preferences.Key<String>
    }

}