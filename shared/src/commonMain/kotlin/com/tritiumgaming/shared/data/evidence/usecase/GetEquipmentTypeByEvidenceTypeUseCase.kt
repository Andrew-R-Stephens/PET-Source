package com.tritiumgaming.shared.data.evidence.usecase

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.model.EvidenceType

class GetEquipmentTypeByEvidenceTypeUseCase {
    operator fun invoke(
        evidenceType: EvidenceType
    ): EquipmentIdentifier {
        val evidenceId = evidenceType.id

        return when(evidenceId) {
            EvidenceIdentifier.DOTS -> EquipmentIdentifier.DOTS
            EvidenceIdentifier.EMF_5 -> EquipmentIdentifier.EMF
            EvidenceIdentifier.ULTRAVIOLET_LIGHT -> EquipmentIdentifier.UV_LIGHT
            EvidenceIdentifier.FREEZING_TEMPERATURE -> EquipmentIdentifier.THERMOMETER
            EvidenceIdentifier.GHOST_ORBS -> EquipmentIdentifier.VIDEO_CAMERA
            EvidenceIdentifier.GHOST_WRITING -> EquipmentIdentifier.GHOST_WRITING_BOOK
            EvidenceIdentifier.SPIRIT_BOX -> EquipmentIdentifier.SPIRIT_BOX
        }
    }
}
