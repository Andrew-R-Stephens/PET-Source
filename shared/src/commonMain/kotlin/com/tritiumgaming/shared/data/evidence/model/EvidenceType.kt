package com.tritiumgaming.shared.data.evidence.model

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTitle

/**
 * Evidence enums
 */
data class EvidenceType(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val icon: EvidenceIcon
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