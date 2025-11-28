package com.tritiumgaming.shared.data.evidence.model

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources

/**
 * Evidence enums
 */
data class EvidenceType(
    val id: EvidenceResources.EvidenceIdentifier,
    val name: EvidenceResources.EvidenceTitle,
    val icon: EvidenceResources.EvidenceIcon,
) {

    override fun equals(other: Any?): Boolean {
        if(other !is EvidenceType) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + icon.hashCode()
        return result
    }

}