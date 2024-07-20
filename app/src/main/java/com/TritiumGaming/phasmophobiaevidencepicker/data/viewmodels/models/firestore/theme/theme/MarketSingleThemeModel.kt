package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.theme

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.MarketplaceItemModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel.Companion.defaultTheme

class MarketSingleThemeModel : MarketplaceItemModel {
    var group: String? = null

    private var theme: ThemeModel? = null

    val isUnlocked: Boolean
        get() = theme?.isUnlocked == true

    val style: Int
        get() {
            return theme?.style ?: defaultTheme.style
        }

    constructor()

    constructor(buyCredits: Long, group: String?, name: String?) {
        this.buyCredits = buyCredits
        this.group = group
        this.name = name
    }

    constructor(uuid: String?, marketTheme: MarketSingleThemeModel, theme: ThemeModel?) {
        super.uuid = uuid
        this.theme = theme
        this.buyCredits = marketTheme.buyCredits
        this.group = marketTheme.group
        this.name = marketTheme.name
    }

    override fun toString(): String {
        return super.toString() + " " + buyCredits + " " + group + " " + theme
    }
}
