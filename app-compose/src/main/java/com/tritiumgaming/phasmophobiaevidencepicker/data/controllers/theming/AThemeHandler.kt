package com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming

import android.util.Log
import androidx.annotation.StyleRes
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class AThemeHandler(
    protected var themes: ArrayList<ThemeModel> = ArrayList(),
    @StyleRes protected var defaultStyle: Int = 0
) {

    private var savedIndex: Int = 0
        set(value) {
            setID(getThemeAtIndex(savedIndex).iD ?: "")
            field = value
        }
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

    val savedTheme: ThemeModel
        get() = getThemeAtIndex(savedIndex)

    val currentName: Int
        get() = currentTheme.name

    protected val _iD : MutableStateFlow<String> = MutableStateFlow("0")
    val iD = _iD.asStateFlow()
    fun setID(iD: String) {
        _iD.update { iD }
        Log.d("Theme", this.iD.value)
    }
    fun setID() {
        _iD.update { getThemeAtIndex(savedIndex).iD ?: "" }
        Log.d("Theme", this.iD.value)
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
