package com.tritiumgaming.shared.operation.domain.popup.model

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceCost
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierRequiredLevel
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceTier

data class EvidencePopupRecord(
    val id: String,
    val name: EvidenceTitle,
    val cost: EvidenceCost,
    val tiers: List<EvidenceTier> = emptyList()
) {

    fun getAnimationAt(i: Int): EvidenceTierAnimation {
        return tiers[i].animation
    }

    fun getDescriptionAt(i: Int): EvidenceTierDescription {
        return tiers[i].description
    }

    fun getUnlockLevelAt(i: Int): EvidenceTierRequiredLevel {
        return tiers[i].levelRequirement
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as EvidencePopupRecord

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + cost.hashCode()
        result = 31 * result + tiers.hashCode()
        return result
    }

}