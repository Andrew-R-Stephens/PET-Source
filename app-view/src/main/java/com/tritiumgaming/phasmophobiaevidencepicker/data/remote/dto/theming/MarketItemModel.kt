package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.dto.theming

abstract class MarketItemModel {
    var uuid: String? = null
    var name: String? = null
    var buyCredits: Long = 0L
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

