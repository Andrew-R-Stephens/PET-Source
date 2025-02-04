package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.ExtendedTheme

@Deprecated("Deprecated in project")
class ThemeModel(
    val iD: String?,
    @StringRes val name: Int,
    val theme: ExtendedTheme,
    unlocked: Boolean = false
) {

    enum class Availability {
        LOCKED, UNLOCKED_DEFAULT, UNLOCKED_PURCHASE
    }

    private var unlockedState: Availability =
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
                ", unlockedState=" + unlockedState +
                '}'
    }

}
