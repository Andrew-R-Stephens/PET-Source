package com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming.subsets

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming.AThemeHandler
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository.Companion.KEY_COLOR_THEME
import kotlinx.coroutines.flow.update

class ColorThemeHandler(
    private val repository: ColorThemeRepository
) : AThemeHandler(
    repository.themes, repository.defaultStyle
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    suspend fun initFlow() {
        repository.flow.collect { colorPreferences ->
            _iD.update { colorPreferences.colorID }
            Log.d("Color", "Collecting from flow:\n\tID -> ${colorPreferences.colorID}")
        }
    }

    fun setThemeID(iD: String) {
        setID(iD)
    }

    suspend fun saveTheme() {
        Log.d("ColorThemeHandler", "Saving")
        repository.dataStore.edit { preferences ->
            preferences[KEY_COLOR_THEME] = iD.value
        }
        Log.d("ColorThemeHandler", "Finalizing Save")
    }

}