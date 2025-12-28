package com.tritiumgaming.shared.data.account.model

import com.tritiumgaming.shared.data.market.model.FeatureAvailability
import com.tritiumgaming.shared.data.market.palette.model.PaletteResources

data class AccountMarketPalette (
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val palette: PaletteResources.PaletteType? = null
) {

    private var unlockedState: FeatureAvailability =
        if (unlocked) FeatureAvailability.UNLOCKED_DEFAULT
        else FeatureAvailability.LOCKED
        get() =
            if (unlocked) FeatureAvailability.UNLOCKED_DEFAULT
            else FeatureAvailability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != FeatureAvailability.LOCKED

    fun setUnlocked(state: Boolean) {
        if (unlockedState == FeatureAvailability.UNLOCKED_DEFAULT) { return }

        this.unlockedState =
            if(state) { FeatureAvailability.UNLOCKED_PURCHASE }
            else { this.unlockedState }
    }

    fun setUnlocked(state: FeatureAvailability) {
        if (unlockedState == FeatureAvailability.UNLOCKED_DEFAULT) { return }

        this.unlockedState = state
    }

    fun revertUnlockStatus() {
        if (unlockedState == FeatureAvailability.UNLOCKED_PURCHASE) {
            unlockedState = FeatureAvailability.LOCKED
        }
    }

    override fun toString(): String {
        return "AccountMarketPalette(uuid='$uuid', name='$name', group='$group', " +
                "buyCredits=$buyCredits, priority=$priority, unlocked=$unlocked, " +
                "palette=${palette?.name})"
    }
    
}

