package com.tritiumgaming.shared.operation.domain.evidence.model

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierRequiredLevel

data class EvidenceTier(
    val description: EvidenceTierDescription,
    val animation: EvidenceTierAnimation,
    val levelRequirement: EvidenceTierRequiredLevel
)