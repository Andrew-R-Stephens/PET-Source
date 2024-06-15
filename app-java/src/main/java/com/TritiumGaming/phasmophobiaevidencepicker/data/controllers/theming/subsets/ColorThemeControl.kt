package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.AThemeControl
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel

class ColorThemeControl(context: Context) : AThemeControl(context) {

    init { defaultStyle = R.style.NonColorblind_Base }

    @SuppressLint("Recycle, ResourceType")
    override fun build(context: Context) {
        // COLORBLIND DATA
        val subThemesArray =
            context.resources.obtainTypedArray(R.array.settings_themes_color_array)

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

            themeArray.recycle()
        }

        subThemesArray.recycle()
    }
}
