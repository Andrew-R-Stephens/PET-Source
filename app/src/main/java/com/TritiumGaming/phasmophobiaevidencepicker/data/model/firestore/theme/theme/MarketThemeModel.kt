package com.TritiumGaming.phasmophobiaevidencepicker.data.model.firestore.theme.theme

import com.TritiumGaming.phasmophobiaevidencepicker.data.model.firestore.theme.MarketItemModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel.Companion.defaultTheme

class MarketThemeModel : MarketItemModel {

    private var theme: ThemeModel? = null

    var group: String? = null

    val isUnlocked: Boolean
        get() = theme?.isUnlocked == true

    val style: Int
        get() {
            return theme?.style ?: defaultTheme.style
        }

    constructor()

    constructor(buyCredits: Long? = 0L, group: String? = null, name: String? = null):
            super(name = name, buyCredits = buyCredits ?: 0L) {
        this.group = group
    }

    constructor(uuid: String?, marketTheme: MarketThemeModel, theme: ThemeModel?):
            super(uuid, marketTheme.buyCredits, marketTheme.name) {
        this.theme = theme
        this.group = marketTheme.group
    }

    override fun toString(): String {
        return "${ super.toString() } $group $theme"
    }
}
