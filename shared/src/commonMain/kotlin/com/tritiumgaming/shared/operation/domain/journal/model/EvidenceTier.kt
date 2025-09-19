package com.tritiumgaming.shared.operation.domain.journal.model

import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTierRequiredLevel

data class EvidenceTier(
    val description: EvidenceTierDescription,
    val animation: EvidenceTierAnimation,
    val levelRequirement: EvidenceTierRequiredLevel
)