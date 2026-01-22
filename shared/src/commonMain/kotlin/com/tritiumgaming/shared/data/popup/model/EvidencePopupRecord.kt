package com.tritiumgaming.shared.data.popup.model

data class EvidencePopupRecord(
    val id: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier,
    val name: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTitle,
    val description: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceDescription,
    val icon: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIcon,
    val animation: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceAnimation,
    val equipmentType: com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType,
    val equipmentTierAnimations: List<com.tritiumgaming.shared.data.evidence.model.EvidenceTier> = emptyList(),
) {

    fun getAnimationAt(i: Int): com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTierAnimation {
        return equipmentTierAnimations[i].animation
    }

    fun getDescriptionAt(i: Int): com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierInformation {
        return equipmentType.items[i].info
    }

    fun getUnlockLevelAt(i: Int): com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUnlockLevel {
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