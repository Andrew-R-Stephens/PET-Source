package com.tritiumgaming.shared.data.evidence.mapper

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier

class EvidenceResources {

    enum class EvidenceIdentifier {
        DOTS,
        EMF_5,
        ULTRAVIOLET_LIGHT,
        FREEZING_TEMPERATURE,
        GHOST_ORBS,
        GHOST_WRITING,
        SPIRIT_BOX,
    }

    enum class EvidenceTitle {
        DOTS,
        EMF_5,
        ULTRAVIOLET_LIGHT,
        FREEZING_TEMPERATURE,
        GHOST_ORBS,
        GHOST_WRITING,
        SPIRIT_BOX,
    }

    enum class EvidenceIcon {
        DOTS,
        EMF_5,
        ULTRAVIOLET_LIGHT,
        FREEZING_TEMPERATURE,
        GHOST_ORBS,
        GHOST_WRITING,
        SPIRIT_BOX,
    }

    enum class EvidenceDescription {
        DOTS,
        EMF_5,
        ULTRAVIOLET_LIGHT,
        FREEZING_TEMPERATURE,
        GHOST_ORBS,
        GHOST_WRITING,
        SPIRIT_BOX,
    }

    enum class EvidenceAnimation {
        DOTS,
        EMF_5,
        ULTRAVIOLET_LIGHT,
        FREEZING_TEMPERATURE,
        GHOST_ORBS,
        GHOST_WRITING,
        SPIRIT_BOX,
    }

    enum class EvidenceTierAnimation {
        DOTS_1,
        DOTS_2,
        DOTS_3,
        EMF_5_1,
        EMF_5_2,
        EMF_5_3,
        ULTRAVIOLET_LIGHT_1,
        ULTRAVIOLET_LIGHT_2,
        ULTRAVIOLET_LIGHT_3,
        FREEZING_TEMPERATURE_1,
        FREEZING_TEMPERATURE_2,
        FREEZING_TEMPERATURE_3,
        GHOST_ORBS_1,
        GHOST_ORBS_2,
        GHOST_ORBS_3,
        GHOST_WRITING_1,
        GHOST_WRITING_2,
        GHOST_WRITING_3,
        SPIRIT_BOX_1,
        SPIRIT_BOX_2,
        SPIRIT_BOX_3,
    }

}

fun EvidenceIdentifier.toEquipmentIdentifier(): EquipmentIdentifier = when(this) {
    EvidenceIdentifier.DOTS -> EquipmentIdentifier.DOTS
    EvidenceIdentifier.EMF_5 -> EquipmentIdentifier.EMF
    EvidenceIdentifier.ULTRAVIOLET_LIGHT -> EquipmentIdentifier.UV_LIGHT
    EvidenceIdentifier.FREEZING_TEMPERATURE -> EquipmentIdentifier.THERMOMETER
    EvidenceIdentifier.GHOST_ORBS -> EquipmentIdentifier.VIDEO_CAMERA
    EvidenceIdentifier.GHOST_WRITING -> EquipmentIdentifier.GHOST_WRITING_BOOK
    EvidenceIdentifier.SPIRIT_BOX -> EquipmentIdentifier.SPIRIT_BOX
}