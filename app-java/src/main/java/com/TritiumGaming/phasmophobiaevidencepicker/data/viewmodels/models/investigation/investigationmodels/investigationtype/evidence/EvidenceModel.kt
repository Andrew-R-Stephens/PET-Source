package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence

import androidx.annotation.DrawableRes
import com.TritiumGaming.phasmophobiaevidencepicker.R

/**
 * Evidence enums
 */
class EvidenceModel {

    var name: String? = null

    @DrawableRes var icon: Int = 0
        @DrawableRes get

    var ruling: Ruling = Ruling.NEUTRAL

    enum class Ruling {
        NEGATIVE, NEUTRAL, POSITIVE
    }

    init {
        name = "Null"
        icon = R.drawable.icon_ev_dots
    }

    fun isRuling(r: Ruling): Boolean {
        return ruling == r
    }

    override fun toString(): String {
        return ruling.name
    }
}
