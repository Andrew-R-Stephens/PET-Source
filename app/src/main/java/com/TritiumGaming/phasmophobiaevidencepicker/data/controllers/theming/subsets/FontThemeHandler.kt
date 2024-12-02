package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets

import android.util.Log
import androidx.datastore.preferences.core.edit
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.AThemeHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository.PreferencesKeys.KEY_FONT_THEME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FontThemeHandler(
    private val repository: FontThemeRepository
) : AThemeHandler(
    repository.themes, repository.defaultStyle
) {

    private val themeFlow: Flow<String> = repository.dataStore.data
        .map { preferences ->
            preferences[KEY_FONT_THEME] ?: defaultStyle.toString()
        }
    suspend fun initThemeFlow() {
        themeFlow.collect { iD ->
            setThemeID(iD)
            Log.d("Theme", "Collected Theme ID: $iD")
        }
    }

    fun setThemeID(iD: String) {
        setID(iD)
        /*repository.dataStore.edit { preferences ->
            preferences[KEY_COLOR_THEME] = iD
        }*/
    }

    suspend fun saveTheme() {
        Log.d("FontThemeHandler", "Saving")
        repository.dataStore.edit { preferences ->
            preferences[KEY_FONT_THEME] = iD.value
        }
        Log.d("FontThemeHandler", "Finalizing Save")
    }

}
