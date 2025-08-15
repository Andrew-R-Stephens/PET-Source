package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

/**
 * Evidence enums
 */
class EvidenceModel(
    var id: Int = 0,
    var name: Int = 0,
    @get:DrawableRes var icon: Int = R.drawable.ic_ev_dots
) {

    var ruling: Ruling = Ruling.NEUTRAL

    enum class Ruling {
        NEGATIVE, NEUTRAL, POSITIVE
    }

    fun isRuling(r: Ruling): Boolean {
        return ruling == r
    }

    override fun toString(): String {
        return ruling.name
    }
}
