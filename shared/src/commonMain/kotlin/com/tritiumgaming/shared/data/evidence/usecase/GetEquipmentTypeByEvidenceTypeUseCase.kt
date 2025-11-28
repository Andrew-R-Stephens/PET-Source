package com.tritiumgaming.shared.data.evidence.usecase

class GetEquipmentTypeByEvidenceTypeUseCase() {
        operator fun invoke(
            evidenceType: com.tritiumgaming.shared.data.evidence.model.EvidenceType
        ): com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier {
            val evidenceId = evidenceType.id

            return when(evidenceId) {
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.DOTS -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.DOTS
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.EMF_5 -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.EMF
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.ULTRAVIOLET_LIGHT -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.UV_LIGHT
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.FREEZING_TEMPERATURE -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.THERMOMETER
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.GHOST_ORBS -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.VIDEO_CAMERA
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.GHOST_WRITING -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.GHOST_WRITING_BOOK
                com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier.SPIRIT_BOX -> com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SPIRIT_BOX
            }
        }
    }
