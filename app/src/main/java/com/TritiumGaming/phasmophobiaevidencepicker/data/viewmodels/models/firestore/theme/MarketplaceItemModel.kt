package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme

abstract class MarketplaceItemModel {
    var uuid: String? = null
        protected set
    var name: String? = null
        protected set
    var buyCredits: Long = 0
        protected set

    constructor()

    constructor(uuid: String?, buyCredits: Long, name: String?) {
        this.uuid = uuid
        this.buyCredits = buyCredits
        this.name = name
    }

    constructor(buyCredits: Long, name: String?) {
        this.buyCredits = buyCredits
        this.name = name
    }

    override fun toString(): String {
        return "$uuid $name $buyCredits"
    }
}

