package com.tritiumgaming.shared.operation.domain.popup.model

import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentTierInformation
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentUnlockLevel
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceTier

data class EvidencePopupRecord(
    val id: EvidenceResources.EvidenceIdentifier,
    val name: EvidenceTitle,
    val description: EvidenceDescription,
    val evidenceTiers: List<EvidenceTier> = emptyList(),
    val equipmentTiers: EquipmentType
) {

    fun getAnimationAt(i: Int): EvidenceTierAnimation {
        return evidenceTiers[i].animation
    }

    fun getDescriptionAt(i: Int): EquipmentTierInformation {
        return equipmentTiers.items[i].info
    }

    fun getUnlockLevelAt(i: Int): EquipmentUnlockLevel {
        return equipmentTiers.items[i].upgradeLevelData
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as EvidencePopupRecord

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + evidenceTiers.hashCode()
        return result
    }

}