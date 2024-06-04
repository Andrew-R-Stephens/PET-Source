package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming

import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.TritiumGaming.phasmophobiaevidencepicker.R

class CustomTheme

@JvmOverloads
constructor(
    val iD: String?,
    @StringRes val name: Int,
    @StyleRes val style: Int,
    unlocked: Boolean = false
) {

    enum class Availability {
        LOCKED, UNLOCKED_DEFAULT, UNLOCKED_PURCHASE
    }

    var unlockedState: Availability =
        if (unlocked) Availability.UNLOCKED_DEFAULT
        else Availability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != Availability.LOCKED

    fun setUnlocked(state: Availability) {
        if (unlockedState == Availability.UNLOCKED_DEFAULT) {
            return
        }
        this.unlockedState = state
    }

    fun revertUnlockStatus() {
        if (unlockedState == Availability.UNLOCKED_PURCHASE) {
            unlockedState = Availability.LOCKED
        }
    }

    override fun toString(): String {
        return "CustomTheme{" +
                "name=" + name +
                ", hashID='" + iD + '\'' +
                ", styleId=" + style +
                ", unlockedState=" + unlockedState +
                '}'
    }

    companion object {
        val defaultTheme: CustomTheme
            get() = CustomTheme(
                "CzjtxSbXRwIpX8SYR0ttngAND",
                R.string.settings_colorblindnessmode_defaultName,
                R.style.Theme_PhasmophobiaEvidenceTool
            )
    }
}
