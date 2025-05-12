package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.JournalItemModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.EvidencePopupModel

/**
 * Evidence enums
 */
class EvidenceModel(
    id: Int = 0,
    @StringRes name: Int = 0,
    @DrawableRes var icon: Int = R.drawable.ic_ev_dots,
    val popupModel: EvidencePopupModel = EvidencePopupModel()
): JournalItemModel(id = id, name = name) {

    override fun equals(other: Any?): Boolean {
        if(other !is EvidenceModel) return false
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
