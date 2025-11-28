package com.tritiumgaming.shared.data.evidence.model

data class Evidence(
    val id: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier,
    val name: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTitle,
    val description: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceDescription,
    val icon: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIcon,
    val animation: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceAnimation,
    val tiers: List<com.tritiumgaming.shared.data.evidence.model.EvidenceTier> = emptyList()
)
