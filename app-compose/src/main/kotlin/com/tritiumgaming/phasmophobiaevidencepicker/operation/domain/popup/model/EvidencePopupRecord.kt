package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTierRequiredLevel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceTier

data class EvidencePopupRecord(
    val id: String,
    val cost: EvidenceCost,
    val tiers: List<EvidenceTier> = emptyList()
) {

    fun getCost(c: Context): EvidenceCost {
        return cost
    }

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
        if (javaClass != other?.javaClass) return false

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