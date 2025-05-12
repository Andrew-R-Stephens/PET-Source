package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.bundle

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.palette.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.palette.PaletteEntity.Availability

data class MarketBundle (
    val marketBundleEntity: MarketBundleEntity? = null,
    val uuid: String = marketBundleEntity?.uuid ?: ""
)

class MarketBundleEntity(
    val uuid: String,
    val name: String,
    val buyCredits: Long = 0L,
    val items: List<String> = listOf()
) {

    var palettes: ArrayList<PaletteEntity>? = null

    private var unlockedState: Availability = Availability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != Availability.LOCKED

    val discountedBuyCredits: Long
        get() {
            if (lockedItemCount == 0) { return 0 }

            val ratio = palettes?.size?.let { size ->
                lockedItemCount / (size.toDouble().coerceAtLeast(1.0))
            } ?: 1.0

            return (buyCredits * ratio).toLong()
        }

    val lockedItemCount: Int
        get() {
            palettes?.let { themes ->
                var lockedThemeCount = themes.size
                for (customTheme in themes) {
                    if (customTheme.isUnlocked) {
                        lockedThemeCount--
                    }
                }
                return lockedThemeCount
            } ?: return 0
        }

    private fun setUnlockedState() {
        palettes?.let { if (lockedItemCount <= 1) { unlockedState = Availability.UNLOCKED_PURCHASE } }
    }

    override fun toString(): String {
        val themeOut = StringBuilder()
        palettes?.let{ themes -> for (t in themes) { themeOut.append(t) }}

        return "${ super.toString() } $themeOut $unlockedState"
    }

}