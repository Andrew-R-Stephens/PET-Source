package com.TritiumGaming.phasmophobiaevidencepicker.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel

class ColorThemeRepository(
    val dataStore: DataStore<Preferences>,
    context: Context
) {

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
}
