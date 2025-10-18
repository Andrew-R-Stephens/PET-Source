package com.tritiumgaming.feature.operation.ui.investigation.journal.popups.evidence

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R

enum class EvidenceTypePage(
    val pageIndex: Int,
    @field:StringRes val label: Int
) {
    EVIDENCE(0, R.string.evidence_section_overview),
    EQUIPMENT(1, R.string.equipment_section_equipment)
}

enum class EvidenceTierPage(
    val pageIndex: Int,
    @field:StringRes val label: Int
) {
    EVIDENCE(0, R.string.evidence_section_overview),
    EQUIPMENT(1, R.string.equipment_section_equipment)
}