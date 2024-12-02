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

class FontThemeRepository(
    val dataStore: DataStore<Preferences>,
    context: Context
) {

    companion object PreferencesKeys {
        lateinit var KEY_FONT_THEME: Preferences.Key<String>
    }

    val defaultStyle = R.style.Fonts_Base
    var themes: ArrayList<ThemeModel> = ArrayList()

    init {
        Log.d("FontTheme Repository", "Initializing")

        KEY_FONT_THEME = stringPreferencesKey(context.resources.getString(R.string.preference_savedFont))

        // FONT DATA
        val subThemesArray =
            context.resources.obtainTypedArray(R.array.settings_themes_fonts_array)

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

            if ((themeNamesArray.length() == themeStyleArray.length()) &&
                (themeNamesArray.length() == themeIDsArray.length())
            ) {
                val themeCount = themeNamesArray.length()

                for (k in 0 until themeCount) {
                    val idRes = themeIDsArray.getString(k)
                    @StringRes val nameRes = themeNamesArray.getResourceId(k, 0)
                    @StyleRes val styleRes = themeStyleArray.getResourceId(k, 0)

                    val tempTheme = ThemeModel(idRes, nameRes, styleRes, true)

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
