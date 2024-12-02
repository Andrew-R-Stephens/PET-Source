package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets

import android.util.Log
import androidx.datastore.preferences.core.edit
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.AThemeHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository.Companion.KEY_COLOR_THEME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ColorThemeHandler(
    private val repository: ColorThemeRepository
) : AThemeHandler(
    repository.themes, repository.defaultStyle
) {

    private val _themeFlow: Flow<String> = repository.dataStore.data
        .map { preferences ->
            preferences[KEY_COLOR_THEME] ?: defaultStyle.toString()
        }
    suspend fun initThemeFlow() {
        _themeFlow.collect { iD ->
            setThemeID(iD)
            Log.d("Theme", "Collected Theme ID: $iD")
        }
    }

    fun setThemeID(iD: String) {
        setID(iD)
        /*repository.dataStore.edit { preferences ->
            preferences[KEY_FONT_THEME] = iD
        }*/
    }

    suspend fun saveTheme() {
        Log.d("ColorThemeHandler", "Saving")
        repository.dataStore.edit { preferences ->
            preferences[KEY_COLOR_THEME] = iD.value
        }
        Log.d("ColorThemeHandler", "Finalizing Save")
    }

}