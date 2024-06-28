package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.bundle

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.MarketplaceItemModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel.Availability

class MarketThemeBundleModel(
    uuid: String?, theme: MarketThemeBundleModel, themes: List<ThemeModel>
) : MarketplaceItemModel(theme.buyCredits, theme.name) {
    var themes: ArrayList<ThemeModel>? = null
        private set
    private var unlockedState: Availability = Availability.LOCKED

    val isUnlocked: Boolean
        get() = unlockedState != Availability.LOCKED

    val discountedBuyCredits: Long
        get() {
            val lockedThemeCount = lockedItemCount

            if (lockedThemeCount == 0) {
                return 0
            }

            val ratio = lockedThemeCount / themes!!.size.toDouble()

            return (buyCredits * ratio).toLong()
        }

    val lockedItemCount: Int
        get() {
            var lockedThemeCount = themes!!.size

            for (customTheme in themes!!) {
                if (customTheme.isUnlocked) {
                    lockedThemeCount--
                }
            }
            return lockedThemeCount
        }

    init {
        super.uuid = uuid
        addThemes(themes)
        setUnlockedState()
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

        return super.toString() + " " + buyCredits + " " + themeOut + " " + unlockedState
    }
}
