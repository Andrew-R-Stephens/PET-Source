package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme

abstract class MarketplaceItemModel {
    var uuid: String? = null
        protected set
    var name: String? = null
        protected set
    var buyCredits: Long = 0L
        protected set
    var priority: Long? = 0L

    constructor()

    constructor(uuid: String? = null, buyCredits: Long = 0, name: String? = null, priority: Long? = 0L) {
        this.uuid = uuid
        this.buyCredits = buyCredits
        this.name = name
        this.priority = priority
    }

    override fun toString(): String {
        return "$uuid $name $buyCredits"
    }
}

