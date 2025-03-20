package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.GhostPopupModel

class GhostModel(
    var id: Int = 0,
    @StringRes var name: Int = 0,
    val popupModel: GhostPopupModel = GhostPopupModel(),
    val evidence: GhostEvidenceModel = GhostEvidenceModel(),
)
