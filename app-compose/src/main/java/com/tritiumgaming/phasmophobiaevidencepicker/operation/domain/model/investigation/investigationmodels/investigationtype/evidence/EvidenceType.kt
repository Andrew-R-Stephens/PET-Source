package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

/**
 * Evidence enums
 */
class EvidenceType(
    val id: String = "0",
    @StringRes val name: Int = 0,
    @DrawableRes val icon: Int = R.drawable.ic_ev_dots
) {

    override fun equals(other: Any?): Boolean {
        if(other !is EvidenceType) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = name
        result = 31 * result + icon
        result = 31 * result + id.hashCode()
        return result
    }

}