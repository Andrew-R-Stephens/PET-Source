package com.tritiumgaming.shared.data.account.model

import com.tritiumgaming.shared.data.market.model.FeatureAvailability
import com.tritiumgaming.shared.data.market.model.FeatureAvailability.LOCKED
import com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_DEFAULT
import com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_PURCHASE
import com.tritiumgaming.shared.data.market.typography.model.TypographyResources.TypographyType

data class AccountMarketTypography (
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val typography: TypographyType? = null
) {

    private var unlockedState: FeatureAvailability =
        if (unlocked) UNLOCKED_DEFAULT
        else LOCKED
        get() =
            if (unlocked) UNLOCKED_DEFAULT
            else LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != LOCKED

    fun setUnlocked(state: Boolean) {
        if (unlockedState == UNLOCKED_DEFAULT) { return }

        this.unlockedState =
            if(state) {
                UNLOCKED_PURCHASE
            }
            else { this.unlockedState }
    }

    fun setUnlocked(state: FeatureAvailability) {
        if (unlockedState == UNLOCKED_DEFAULT) { return }

        this.unlockedState = state
    }

    fun revertUnlockStatus() {
        if (unlockedState == UNLOCKED_PURCHASE) {
            unlockedState = LOCKED
        }
    }

    override fun toString(): String {
        return "AccountMarketTypography(uuid='$uuid', name='$name', group='$group', " +
                "buyCredits=$buyCredits, priority=$priority, unlocked=$unlocked, " +
                "typography=${typography})"
    }
    
}

