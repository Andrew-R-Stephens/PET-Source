package com.tritiumgaming.shared.operation.domain.evidence.model

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceCost
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle

data class Evidence(
    val id: String,
    val name: EvidenceTitle,
    val icon: EvidenceIcon,
    val buyCost: EvidenceCost,
    val tiers: List<EvidenceTier> = emptyList()
)
