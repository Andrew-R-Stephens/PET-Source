package com.tritiumgaming.shared.data.account.model

data class AccountMarketPalette (
    internal val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val palette: com.tritiumgaming.shared.data.market.palette.model.PaletteResources.PaletteType? = null
) {

    private var unlockedState: com.tritiumgaming.shared.data.market.model.FeatureAvailability =
        if (unlocked) com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_DEFAULT
        else com.tritiumgaming.shared.data.market.model.FeatureAvailability.LOCKED
        get() =
            if (unlocked) com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_DEFAULT
            else com.tritiumgaming.shared.data.market.model.FeatureAvailability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != com.tritiumgaming.shared.data.market.model.FeatureAvailability.LOCKED

    fun setUnlocked(state: Boolean) {
        if (unlockedState == com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_DEFAULT) { return }

        this.unlockedState =
            if(state) { com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_PURCHASE }
            else { this.unlockedState }
    }

    fun setUnlocked(state: com.tritiumgaming.shared.data.market.model.FeatureAvailability) {
        if (unlockedState == com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_DEFAULT) { return }

        this.unlockedState = state
    }

    fun revertUnlockStatus() {
        if (unlockedState == com.tritiumgaming.shared.data.market.model.FeatureAvailability.UNLOCKED_PURCHASE) {
            unlockedState = com.tritiumgaming.shared.data.market.model.FeatureAvailability.LOCKED
        }
    }

    override fun toString(): String {
        return "AccountMarketPalette(uuid='$uuid', name='$name', group='$group', " +
                "buyCredits=$buyCredits, priority=$priority, unlocked=$unlocked, " +
                "palette=${palette?.name})"
    }
    
}

