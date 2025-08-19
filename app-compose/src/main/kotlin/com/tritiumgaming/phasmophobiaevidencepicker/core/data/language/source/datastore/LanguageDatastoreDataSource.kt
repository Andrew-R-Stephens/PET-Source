package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguageDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
) : LanguageDatastore {

    val flow: Flow<LanguageDatastore.LanguagePreferences> = dataStore.data
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        KEY_CURRENT_LANGUAGE_CODE = stringPreferencesKey(
            context.resources.getString(R.string.preference_language)
        )
    }

    override suspend fun saveCurrentLanguageCode(languageCode: String) {
        dataStore.edit { preferences ->
            preferences[KEY_CURRENT_LANGUAGE_CODE] = languageCode
        }
    }

    override fun getCurrentLanguageCode(): String {
        var currentLanguageCode = DEFAULT_LANGUAGE
        dataStore.data.map { preferences ->
            preferences[KEY_CURRENT_LANGUAGE_CODE]?.let { currentLanguageCode = it }
        }
        return currentLanguageCode
    }

    override fun initializeDatastoreLiveData() {
        liveData { emit(fetchDatastoreInitialPreferences()) }
    }

    override suspend fun initDatastoreFlow(onUpdate: (LanguageDatastore.LanguagePreferences) -> Unit) =
        flow.collect { onUpdate(it) }

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences) =
        LanguageDatastore.LanguagePreferences(
            preferences[KEY_CURRENT_LANGUAGE_CODE]
                ?: DEFAULT_LANGUAGE
        )

    companion object PreferenceKeys {
        lateinit var KEY_CURRENT_LANGUAGE_CODE: Preferences.Key<String>

        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }

}
