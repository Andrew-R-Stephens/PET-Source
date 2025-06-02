package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming

import android.content.Context
import androidx.annotation.StyleRes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel

abstract class AThemeControl(context: Context) {
    @StyleRes
    var defaultStyle: Int = 0

    protected var themes: ArrayList<ThemeModel> = ArrayList()

    private var savedIndex: Int = 0
    var selectedIndex: Int = 0
        set(value) {
            var tempIndex = value
            if (tempIndex < 0 || tempIndex >= themes.size) {
                tempIndex = getIndexOfID(ThemeModel.defaultTheme.iD)
            }

            field = tempIndex
        }

    val currentTheme: ThemeModel
        get() {
            if (selectedIndex >= themes.size || selectedIndex < 0) {
                return ThemeModel.defaultTheme
            }

            val theme = themes[selectedIndex]

            return theme
        }

    val currentName: Int
        get() = currentTheme.name

    val iD: String
        get() = currentTheme.iD ?: ThemeModel.defaultTheme.iD ?: ""

    protected abstract fun build(context: Context)

    init {
        build(context)
    }

    fun init(savedID: String) {
        saveIndex(getIndexOfID(savedID))
        selectedIndex  = savedIndex
    }

    private fun getIndexOfID(savedID: String?): Int {
        for (i in themes.indices) {
            if (themes[i].iD == savedID) {
                return i
            }
        }

        return 0
    }

    fun saveSelectedIndex() {
        this.savedIndex = selectedIndex
    }

    fun saveIndex(index: Int) {
        var savedIndex = index
        if (savedIndex < 0 || savedIndex >= themes.size) {
            savedIndex = getIndexOfID(ThemeModel.defaultTheme.iD)
        }

        this.savedIndex = savedIndex
    }

    fun revertToSavedIndex() {
        selectedIndex = savedIndex
    }

    @JvmOverloads
    fun iterateSelectedIndex(dir: Int, start: Int = selectedIndex) {
        var currentIndex = selectedIndex
        currentIndex += dir

        if (currentIndex < 0) {
            currentIndex = themes.size - 1
        } else if (currentIndex >= themes.size) {
            currentIndex = 0
        }
        selectedIndex = currentIndex

        if (!currentTheme.isUnlocked) {
            if (start != selectedIndex) {
                iterateSelectedIndex(dir, start)
            }
        }
    }

    fun getThemeAtIndex(index: Int): ThemeModel {
        if (index >= themes.size || selectedIndex < 0) {
            return ThemeModel.defaultTheme
        }

        val theme = themes[index]

        return theme
    }

    fun getIndexOfTheme(theme: ThemeModel): Int {
        for (i in 0..<themes.size) {
            if (themes[i].iD == theme.iD) {
                return i
            }
        }
        return 0
    }

    fun getThemeByUUID(uuid: String): ThemeModel {
        for (customTheme in themes) {
            if (customTheme.iD == uuid) {
                return customTheme
            }
        }
        return ThemeModel.defaultTheme
    }

    fun revertAllUnlockedStatuses() {
        for (theme in themes) {
            theme.revertUnlockStatus()
        }
    }
}
