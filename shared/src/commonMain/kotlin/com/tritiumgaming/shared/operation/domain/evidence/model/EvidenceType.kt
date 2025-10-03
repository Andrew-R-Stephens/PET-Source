package com.tritiumgaming.shared.operation.domain.evidence.model

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle

/**
 * Evidence enums
 */
data class EvidenceType(
    val id: String,
    val name: EvidenceTitle,
    val icon: EvidenceIcon,
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