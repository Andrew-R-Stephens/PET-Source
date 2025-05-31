package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.FeatureAvailability
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

data class MarketTypography (
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = true,
    val typography: ExtendedTypography? = null
) {

    private var unlockedState: FeatureAvailability =
        if (unlocked) FeatureAvailability.UNLOCKED_DEFAULT
        else FeatureAvailability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != FeatureAvailability.LOCKED

    fun setUnlocked(state: FeatureAvailability) {
        if (unlockedState == FeatureAvailability.UNLOCKED_DEFAULT) {
            return
        }
        this.unlockedState = state
    }

    fun revertUnlockStatus() {
        if (unlockedState == FeatureAvailability.UNLOCKED_PURCHASE) {
            unlockedState = FeatureAvailability.LOCKED
        }
    }

    override fun toString(): String {
        return "TypographyEntity(uuid='$uuid', name='$name', group='$group', " +
                "buyCredits=$buyCredits, priority=$priority, unlocked=$unlocked, " +
                "typography=${typography})"
    }

}
