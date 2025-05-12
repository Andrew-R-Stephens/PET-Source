package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.JournalItemModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.GhostPopupModel

class GhostModel(
    id: Int = 0,
    @StringRes name: Int = 0,
    val popupModel: GhostPopupModel = GhostPopupModel(),
    val evidence: GhostEvidenceModel = GhostEvidenceModel(),
) : JournalItemModel(id = id, name = name)
