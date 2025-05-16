package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.ghost

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.ghostevidence.GhostEvidence

class GhostType(
    var id: String = "",
    @StringRes var name: Int = 0,
    var evidence: GhostEvidence = GhostEvidence(),
)
