package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ColorThemeRepository(
    val dataStore: DataStore<Preferences>,
    context: Context
) {

    data class ColorPreferences(
        val colorID: String
    )

    val flow: Flow<ColorPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    companion object {
        lateinit var KEY_COLOR_THEME: Preferences.Key<String>
    }

    val defaultStyle = R.style.NonColorblind_Base
    var themes: ArrayList<ThemeModel> = ArrayList()

    init {
        Log.d("ColorTHeme Repository", "Initializing")

        KEY_COLOR_THEME =
            stringPreferencesKey(context.resources.getString(R.string.preference_savedTheme))

        // COLORBLIND DATA
        val subThemesArray =
            context.resources.obtainTypedArray(R.array.settings_themes_color_array)

        @SuppressLint("ResourceType")
        for (i in 0 until subThemesArray.length()) {
            val themeArray =
                context.resources.obtainTypedArray(subThemesArray.getResourceId(i, 0))

            val themeIDsArray =
                context.resources.obtainTypedArray(themeArray.getResourceId(0, 0))
            val themeNamesArray =
                context.resources.obtainTypedArray(themeArray.getResourceId(1, 0))
            val themeStyleArray =
                context.resources.obtainTypedArray(themeArray.getResourceId(2, 0))

            val isUnlocked =
                context.resources.getBoolean(themeArray.getResourceId(3, 0))

            if ((themeNamesArray.length() == themeStyleArray.length()) &&
                (themeNamesArray.length() == themeIDsArray.length())
            ) {
                val themeCount = themeNamesArray.length()

                for (k in 0 until themeCount) {
                    val idRes = themeIDsArray.getString(k)
                    @StringRes val nameRes = themeNamesArray.getResourceId(k, 0)
                    @StyleRes val styleRes = themeStyleArray.getResourceId(k, 0)

                    val tempTheme = ThemeModel(
                        idRes, nameRes, styleRes,
                        isUnlocked
                    )
                    themes.add(tempTheme)
                }
            }
            themeIDsArray.recycle()
            themeNamesArray.recycle()
            themeStyleArray.recycle()

            themeArray.recycle()
        }

        subThemesArray.recycle()
    }

    suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): ColorPreferences {
        return ColorPreferences(
            preferences[KEY_COLOR_THEME] ?: "0"
        )
    }

}
