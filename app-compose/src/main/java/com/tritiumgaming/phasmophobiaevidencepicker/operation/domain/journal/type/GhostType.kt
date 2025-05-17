package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type

import androidx.annotation.StringRes

class GhostType(
    var id: String = "",
    @StringRes var name: Int = 0,
    var evidence: GhostEvidence = GhostEvidence(),
)