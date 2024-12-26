package com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming.subsets

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming.AThemeHandler
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository.PreferencesKeys.KEY_FONT_THEME
import kotlinx.coroutines.flow.update

class FontThemeHandler(
    private val repository: FontThemeRepository
) : AThemeHandler(
    repository.themes, repository.defaultStyle
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    suspend fun initFlow() {
        repository.flow.collect { fontPreferences ->
            _iD.update { fontPreferences.fontID }
            Log.d("Font", "Collecting from flow:\n\tID -> ${fontPreferences.fontID}")
        }
    }

    fun setThemeID(iD: String) {
        setID(iD)
    }

    suspend fun saveTheme() {
        Log.d("FontThemeHandler", "Saving")
        repository.dataStore.edit { preferences ->
            preferences[KEY_FONT_THEME] = iD.value
        }
        Log.d("FontThemeHandler", "Finalizing Save")
    }

}
