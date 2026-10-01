package com.tritiumgaming.data.marketplace.account.model

import com.tritiumgaming.data.account.model.AccountPalette
import com.tritiumgaming.data.marketplace.model.FeatureAvailability
import com.tritiumgaming.data.marketplace.model.FeatureAvailability.LOCKED
import com.tritiumgaming.data.marketplace.model.FeatureAvailability.UNLOCKED_DEFAULT
import com.tritiumgaming.data.marketplace.model.FeatureAvailability.UNLOCKED_PURCHASE
import com.tritiumgaming.data.palette.mappers.PaletteResources.PaletteType

data class AccountMarketPalette (
    val uuid: String,
    internal val name: String? = "",
    internal val group: String? = "",
    internal val buyCredits: Long = 0L,
    internal val priority: Long? = 0L,
    internal val unlocked: Boolean = false,
    internal val palette: PaletteType? = null
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
        return "AccountMarketPalette(uuid='$uuid', name='$name', group='$group', " +
                "buyCredits=$buyCredits, priority=$priority, unlocked=$unlocked, " +
                "palette=${palette?.name})"
    }
    
}

fun List<AccountPalette>.toAccountMarketPalette() = map {
    it.toAccountMarketPalette()
}

fun AccountPalette.toAccountMarketPalette(): AccountMarketPalette {
    return AccountMarketPalette(
        uuid = uuid,
        unlocked = unlocked
    )
}