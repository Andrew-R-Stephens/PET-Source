package com.tritiumgaming.shared.data.popup.model

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.*
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.*
import com.tritiumgaming.shared.data.evidence.model.EvidenceTier

data class EvidencePopupRecord(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val description: EvidenceDescription,
    val icon: EvidenceIcon,
    val animation: EvidenceAnimation,
    val equipmentType: EquipmentType,
    val equipmentTierAnimations: List<EvidenceTier> = emptyList(),
) {

    fun getAnimationAt(i: Int): EvidenceTierAnimation {
        return equipmentTierAnimations[i].animation
    }

    fun getDescriptionAt(i: Int): EquipmentTierInformation {
        return equipmentType.items[i].info
    }

    fun getUnlockLevelAt(i: Int): EquipmentUnlockLevel {
        return equipmentType.items[i].upgradeLevelData
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as EvidencePopupRecord

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + equipmentTierAnimations.hashCode()
        return result
    }

}