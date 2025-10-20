package com.tritiumgaming.shared.operation.domain.evidence.model

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle

data class Evidence(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val description: EvidenceDescription,
    val icon: EvidenceIcon,
    val animation: EvidenceAnimation,
    val tiers: List<EvidenceTier> = emptyList()
)
