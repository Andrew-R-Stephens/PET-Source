package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StyleRes
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme.Companion.defaultTheme

/**
 * ColorSpaceData class
 *
 * @author TritiumGamingStudios
 */
abstract class AThemeControl(context: Context) {
    @StyleRes
    protected var defaultStyle: Int = 0

    protected var themes: ArrayList<CustomTheme> = ArrayList()

    var savedIndex: Int = 0
    var selectedIndex: Int = 0
        set(value) {
            var tempIndex = value
            if (tempIndex < 0 || tempIndex >= themes.size) {
                tempIndex = getIndexOfID(defaultTheme.iD)
            }

            field = tempIndex
        }

    val currentTheme: CustomTheme
        get() {
            if (selectedIndex >= themes.size || selectedIndex < 0) {
                return defaultTheme
            }

            val theme = themes[selectedIndex]

            return theme
        }

    val currentName: Int
        get() = currentTheme.name

    val iD: String
        get() = currentTheme.iD ?: defaultTheme.iD ?: ""

    init {
        build(context)
    }

    fun init() {
        saveSelectedIndex(getIndexOfID(defaultTheme.iD))
        selectedIndex = savedIndex
    }

    fun init(savedID: String) {
        saveSelectedIndex(getIndexOfID(savedID))
        selectedIndex  = savedIndex
    }

    @SuppressLint("Recycle, ResourceType")
    protected abstract fun build(context: Context)

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

    fun saveSelectedIndex(savedIndex: Int) {
        var savedIndex = savedIndex
        if (savedIndex < 0 || savedIndex >= themes.size) {
            savedIndex = getIndexOfID(defaultTheme.iD)
        }

        this.savedIndex = savedIndex
    }

    fun revertToSavedIndex() {
        selectedIndex = savedIndex
    }

    @JvmOverloads
    fun iterateSelectedIndex(dir: Int, start: Int = selectedIndex) {
        selectedIndex += dir

        if (selectedIndex < 0) {
            selectedIndex = themes.size - 1
        } else if (selectedIndex >= themes.size) {
            selectedIndex = 0
        }

        if (!currentTheme.isUnlocked) {
            if (start != selectedIndex) {
                iterateSelectedIndex(dir, start)
            }
        }
    }

    fun getThemeAtIndex(index: Int): CustomTheme {
        if (index >= themes.size || selectedIndex < 0) {
            return defaultTheme
        }

        val theme = themes[index]

        return theme
    }

    fun getThemeByUUID(uuid: String): CustomTheme {
        for (customTheme in themes) {
            if (customTheme.iD == uuid) {
                return customTheme
            }
        }
        return defaultTheme
    }

    fun revertAllUnlockedStatuses() {
        for (theme in themes) {
            theme.revertUnlockStatus()
        }
    }
}
