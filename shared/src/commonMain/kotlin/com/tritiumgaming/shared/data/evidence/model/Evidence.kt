package com.tritiumgaming.shared.data.evidence.model

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTitle

data class Evidence(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val description: EvidenceDescription,
    val icon: EvidenceIcon,
    val animation: EvidenceAnimation,
    val tiers: List<EvidenceTier> = emptyList()
)
