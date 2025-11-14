package com.tritiumstudios.feature.investigation.ui.popups.evidence

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
    @field:StringRes val label: Int
) {
    TIER_1(R.string.equipment_tiers_1),
    TIER_2(R.string.equipment_tiers_2),
    TIER_3(R.string.equipment_tiers_3)
}