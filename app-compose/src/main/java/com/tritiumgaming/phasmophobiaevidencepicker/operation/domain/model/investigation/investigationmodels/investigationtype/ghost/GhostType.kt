package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.investigation.popups.GhostPopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghostevidence.GhostEvidence

class GhostType(
    var id: String = "",
    @StringRes var name: Int = 0,
    var evidence: GhostEvidence = GhostEvidence(),
)
