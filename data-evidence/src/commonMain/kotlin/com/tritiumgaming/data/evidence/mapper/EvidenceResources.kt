package com.tritiumgaming.data.evidence.mapper

import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.DOTS
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.EMF
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.GHOST_WRITING_BOOK
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SPIRIT_BOX
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.THERMOMETER
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.UV_LIGHT
import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier.VIDEO_CAMERA
import com.tritiumgaming.data.evidence.mapper.EvidenceResources.EvidenceIdentifier

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
    EvidenceIdentifier.DOTS -> DOTS
    EvidenceIdentifier.EMF_5 -> EMF
    EvidenceIdentifier.ULTRAVIOLET_LIGHT -> UV_LIGHT
    EvidenceIdentifier.FREEZING_TEMPERATURE -> THERMOMETER
    EvidenceIdentifier.GHOST_ORBS -> VIDEO_CAMERA
    EvidenceIdentifier.GHOST_WRITING -> GHOST_WRITING_BOOK
    EvidenceIdentifier.SPIRIT_BOX -> SPIRIT_BOX
}