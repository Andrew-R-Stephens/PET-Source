package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.AThemeControl
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel

class FontThemeControl(context: Context) : AThemeControl(context) {

    init { defaultStyle = R.style.Fonts_Base }

    @SuppressLint("Recycle, ResourceType")
    override fun build(context: Context) {
        // FONT DATA
        val subThemesArray =
            context.resources.obtainTypedArray(R.array.settings_themes_fonts_array)

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

            themeArray.recycle()
        }

        subThemesArray.recycle()
    }
}
