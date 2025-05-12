package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.investigation.popups.EvidencePopup

/**
 * Evidence enums
 */
class EvidenceType(
    @StringRes val id: Int = 0,
    @StringRes val name: Int = 0,
    @DrawableRes val icon: Int = R.drawable.ic_ev_dots,
    val popupModel: EvidencePopup = EvidencePopup()
) {

    override fun equals(other: Any?): Boolean {
        if(other !is EvidenceType) return false
        val result = id == other.id
        return result
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name
        result = 31 * result + icon
        result = 31 * result + popupModel.hashCode()
        return result
    }

}