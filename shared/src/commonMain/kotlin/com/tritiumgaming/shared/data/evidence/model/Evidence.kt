package com.tritiumgaming.shared.data.evidence.model

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.*

data class Evidence(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val description: EvidenceDescription,
    val icon: EvidenceIcon,
    val animation: EvidenceAnimation,
    val tiers: List<EvidenceTier> = emptyList()
)
