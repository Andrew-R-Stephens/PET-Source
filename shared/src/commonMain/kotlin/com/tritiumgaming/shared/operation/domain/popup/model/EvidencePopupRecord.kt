package com.tritiumgaming.shared.operation.domain.popup.model

import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentTierInformation
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources.EquipmentUnlockLevel
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceTier

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