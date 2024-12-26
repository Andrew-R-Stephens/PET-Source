package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguageRepository(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {

    data class LanguagePreference(
        val languageCode: String
    )

    val flow: Flow<LanguagePreference> = dataStore.data
        .map { preferences ->
            mapLanguagePreference(preferences)
        }

    private val _languageList: ArrayList<LanguageObject> = ArrayList()
    val languageList:  ArrayList< LanguageObject>
        get() = _languageList


    init {
        Log.d("LanguagesRepository", "Initializing")

        KEY_CURRENT_LANGUAGE_CODE = stringPreferencesKey(
            context.resources.getString(R.string.preference_language))

        val languageNames = ArrayList(
            listOf(*context.resources.getStringArray(R.array.language_names)))
        val languageCodes = ArrayList(
            listOf(*context.resources.getStringArray(R.array.language_codes)))

        if(languageNames.size == languageCodes.size) {
            languageNames.forEachIndexed { index: Int, name: String ->
                languageList.add(LanguageObject(name, languageCodes[index]))
            }
        }

        // OVERRIDE DEFAULT LANGUAGE
        languageList.forEach { language ->
            if(language.abbreviation.equals(Locale.getDefault().language, ignoreCase = true)) {
                DEFAULT_LANGUAGE = Locale.getDefault().language
            }
        }

    }

    suspend fun setCurrentLanguageCode(languageCode: String) {
        dataStore.edit { preferences ->
            preferences[KEY_CURRENT_LANGUAGE_CODE] = languageCode
        }
    }

    suspend fun fetchInitialPreferences() =
        mapLanguagePreference(dataStore.data.first().toPreferences())

    private fun mapLanguagePreference(preferences: Preferences) =
        LanguagePreference(
            preferences[KEY_CURRENT_LANGUAGE_CODE] ?: DEFAULT_LANGUAGE
        )

    companion object {
        lateinit var KEY_CURRENT_LANGUAGE_CODE: Preferences.Key<String>

        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }

    data class LanguageObject(
        val name: String,
        val abbreviation: String
    )

}