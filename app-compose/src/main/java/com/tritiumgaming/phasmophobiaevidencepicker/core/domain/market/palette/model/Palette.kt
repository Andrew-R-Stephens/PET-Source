package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

data class Palette (
    val marketThemeEntity: PaletteEntity? = null,
    private val uuid: String = marketThemeEntity?.uuid ?: "",
    val palette: ExtendedPalette
) {
    fun getAsPair(): Pair<String, Palette> {
        return Pair(uuid, this)
    }
}

class PaletteEntity(
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = false
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
                ", hashID='" + uuid + '\'' +
                ", unlockedState=" + unlockedState +
                '}'
    }

}