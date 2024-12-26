package com.tritiumgaming.phasmophobiaevidencepicker.data.model.firestore.theme.bundle

import com.tritiumgaming.phasmophobiaevidencepicker.data.model.firestore.theme.MarketItemModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel.Availability

class MarketBundleModel: MarketItemModel {

    constructor()

    constructor(buyCredits: Long? = 0, name: String? = null):
            super(buyCredits = buyCredits ?: 0, name = name)

    constructor(uuid: String? = null, theme: MarketBundleModel, themes: List<ThemeModel>):
            super(uuid, theme.buyCredits, theme.name) {
                addThemes(themes)
                setUnlockedState()
    }

    var themes: ArrayList<ThemeModel>? = null
        private set
    private var unlockedState: Availability = Availability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != Availability.LOCKED

    val discountedBuyCredits: Long
        get() {
            if (lockedItemCount == 0) { return 0 }

            val ratio = themes?.size?.let { size ->
                lockedItemCount / (size.toDouble().coerceAtLeast(1.0))
            } ?: 1.0

            return (buyCredits * ratio).toLong()
        }

    val lockedItemCount: Int
        get() {
            themes?.let { themes ->
                var lockedThemeCount = themes.size
                for (customTheme in themes) {
                    if (customTheme.isUnlocked) {
                        lockedThemeCount--
                    }
                }
                return lockedThemeCount
            } ?: return 0
        }

    private fun addThemes(themes: List<ThemeModel>) {
        this.themes = ArrayList()
        this.themes?.addAll(themes)
    }

    private fun setUnlockedState() {
        themes?.let { if (lockedItemCount <= 1) { unlockedState = Availability.UNLOCKED_PURCHASE } }
    }

    override fun toString(): String {
        val themeOut = StringBuilder()
        themes?.let{ themes -> for (t in themes) { themeOut.append(t) }}

        return "${ super.toString() } $themeOut $unlockedState"
    }
}