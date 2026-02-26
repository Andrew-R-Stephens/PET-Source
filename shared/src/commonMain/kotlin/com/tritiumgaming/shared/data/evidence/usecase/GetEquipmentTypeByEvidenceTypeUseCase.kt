package com.tritiumgaming.shared.data.evidence.usecase

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType

class GetEquipmentTypeByEvidenceTypeUseCase {
        operator fun invoke(
            evidenceType: EvidenceType
        ): EquipmentResources.EquipmentIdentifier {
            val evidenceId = evidenceType.id

            return when(evidenceId) {
                EvidenceResources.EvidenceIdentifier.DOTS -> EquipmentResources.EquipmentIdentifier.DOTS
                EvidenceResources.EvidenceIdentifier.EMF_5 -> EquipmentResources.EquipmentIdentifier.EMF
                EvidenceResources.EvidenceIdentifier.ULTRAVIOLET_LIGHT -> EquipmentResources.EquipmentIdentifier.UV_LIGHT
                EvidenceResources.EvidenceIdentifier.FREEZING_TEMPERATURE -> EquipmentResources.EquipmentIdentifier.THERMOMETER
                EvidenceResources.EvidenceIdentifier.GHOST_ORBS -> EquipmentResources.EquipmentIdentifier.VIDEO_CAMERA
                EvidenceResources.EvidenceIdentifier.GHOST_WRITING -> EquipmentResources.EquipmentIdentifier.GHOST_WRITING_BOOK
                EvidenceResources.EvidenceIdentifier.SPIRIT_BOX -> EquipmentResources.EquipmentIdentifier.SPIRIT_BOX
            }
        }
    }
