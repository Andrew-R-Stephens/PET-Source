package com.tritiumstudios.feature.investigation.app.mappers.evidence

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTitle

@StringRes fun EvidenceIdentifier.toStringResource(): Int =
    when (this) {
        EvidenceIdentifier.DOTS -> R.string.evidence_id_dots
        EvidenceIdentifier.EMF_5 -> R.string.evidence_id_emf
        EvidenceIdentifier.ULTRAVIOLET_LIGHT -> R.string.evidence_id_ultraviolet
        EvidenceIdentifier.FREEZING_TEMPERATURE -> R.string.evidence_id_temperatures
        EvidenceIdentifier.GHOST_ORBS -> R.string.evidence_id_orbs
        EvidenceIdentifier.GHOST_WRITING -> R.string.evidence_id_book
        EvidenceIdentifier.SPIRIT_BOX -> R.string.evidence_id_box
    }

@StringRes fun EvidenceTitle.toStringResource(): Int =
    when (this) {
        EvidenceTitle.DOTS -> R.string.evidence_type_dots
        EvidenceTitle.EMF_5 -> R.string.evidence_type_emf
        EvidenceTitle.ULTRAVIOLET_LIGHT -> R.string.evidence_type_ultraviolet
        EvidenceTitle.FREEZING_TEMPERATURE -> R.string.evidence_type_temperatures
        EvidenceTitle.GHOST_ORBS -> R.string.evidence_type_orbs
        EvidenceTitle.GHOST_WRITING -> R.string.evidence_type_book
        EvidenceTitle.SPIRIT_BOX -> R.string.evidence_type_box
    }

@DrawableRes fun EvidenceIcon.toDrawableResource(): Int =
    when (this) {
        EvidenceIcon.DOTS -> R.drawable.ic_ev_dots
        EvidenceIcon.EMF_5 -> R.drawable.ic_ev_emf
        EvidenceIcon.ULTRAVIOLET_LIGHT -> R.drawable.ic_ev_uv
        EvidenceIcon.FREEZING_TEMPERATURE -> R.drawable.ic_ev_freezing
        EvidenceIcon.GHOST_ORBS -> R.drawable.ic_ev_orbs
        EvidenceIcon.GHOST_WRITING -> R.drawable.ic_ev_writing
        EvidenceIcon.SPIRIT_BOX -> R.drawable.ic_ev_spiritbox
    }

@StringRes fun EvidenceDescription.toStringResource(): Int =
    when (this) {
        EvidenceDescription.DOTS -> R.string.evidence_info_overview_dots
        EvidenceDescription.EMF_5 -> R.string.evidence_info_overview_emf
        EvidenceDescription.ULTRAVIOLET_LIGHT -> R.string.evidence_info_overview_ultraviolet
        EvidenceDescription.FREEZING_TEMPERATURE -> R.string.evidence_info_overview_thermometer
        EvidenceDescription.GHOST_ORBS -> R.string.evidence_info_overview_vcam
        EvidenceDescription.GHOST_WRITING -> R.string.evidence_info_overview_book
        EvidenceDescription.SPIRIT_BOX -> R.string.evidence_info_overview_sbox
    }

@DrawableRes fun EvidenceAnimation.toDrawableResource(): Int =
    when (this) {
        EvidenceAnimation.DOTS -> R.drawable.example_dots_0
        EvidenceAnimation.EMF_5 -> R.drawable.example_emf5_0
        EvidenceAnimation.ULTRAVIOLET_LIGHT -> R.drawable.example_fingerprints_normal_cropped_0
        EvidenceAnimation.FREEZING_TEMPERATURE -> R.drawable.example_thermometer_freezing_cropped_0
        EvidenceAnimation.GHOST_ORBS -> R.drawable.example_ghost_orbs_0
        EvidenceAnimation.GHOST_WRITING -> R.drawable.example_ghost_writing_cropped_0
        EvidenceAnimation.SPIRIT_BOX -> R.drawable.example_spirit_box_cropped_0
    }

@DrawableRes fun EvidenceTierAnimation.toDrawableResource(): Int =
    when (this) {
        EvidenceTierAnimation.DOTS_1 -> R.drawable.anim_dots_1
        EvidenceTierAnimation.DOTS_2 -> R.drawable.anim_dots_2
        EvidenceTierAnimation.DOTS_3 -> R.drawable.anim_dots_3
        EvidenceTierAnimation.EMF_5_1 -> R.drawable.anim_emf_1
        EvidenceTierAnimation.EMF_5_2 -> R.drawable.anim_emf_2
        EvidenceTierAnimation.EMF_5_3 -> R.drawable.anim_emf_3
        EvidenceTierAnimation.ULTRAVIOLET_LIGHT_1 -> R.drawable.anim_uv_1
        EvidenceTierAnimation.ULTRAVIOLET_LIGHT_2 -> R.drawable.anim_uv_2
        EvidenceTierAnimation.ULTRAVIOLET_LIGHT_3 -> R.drawable.anim_uv_3
        EvidenceTierAnimation.FREEZING_TEMPERATURE_1 -> R.drawable.anim_thermo_1
        EvidenceTierAnimation.FREEZING_TEMPERATURE_2 -> R.drawable.anim_thermo_2
        EvidenceTierAnimation.FREEZING_TEMPERATURE_3 -> R.drawable.anim_thermo_3
        EvidenceTierAnimation.GHOST_ORBS_1 -> R.drawable.anim_orbs_1
        EvidenceTierAnimation.GHOST_ORBS_2 -> R.drawable.anim_orbs_2
        EvidenceTierAnimation.GHOST_ORBS_3 -> R.drawable.anim_orbs_3
        EvidenceTierAnimation.GHOST_WRITING_1 -> R.drawable.example_ghost_writing_cropped_0
        EvidenceTierAnimation.GHOST_WRITING_2 -> R.drawable.example_ghost_writing_cropped_0
        EvidenceTierAnimation.GHOST_WRITING_3 -> R.drawable.example_ghost_writing_cropped_0
        EvidenceTierAnimation.SPIRIT_BOX_1 -> R.drawable.example_spirit_box_cropped_0
        EvidenceTierAnimation.SPIRIT_BOX_2 -> R.drawable.example_spirit_box_cropped_0
        EvidenceTierAnimation.SPIRIT_BOX_3 -> R.drawable.example_spirit_box_cropped_0
    }
