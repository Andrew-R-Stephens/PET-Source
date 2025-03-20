package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.EvidencePopupModel

/**
 * Evidence enums
 */
class EvidenceModel(
    var id: Int = 0,
    var name: Int = 0,
    @DrawableRes var icon: Int = R.drawable.ic_ev_dots,
    val popupModel: EvidencePopupModel = EvidencePopupModel()
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
