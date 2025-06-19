package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTierRequiredLevel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTitle

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

@StringRes fun EvidenceCost.toStringResource(): Int =
    when (this) {
        EvidenceCost.DOTS -> R.integer.equipment_requirement_buycost_65
        EvidenceCost.EMF_5 -> R.integer.equipment_requirement_buycost_45
        EvidenceCost.ULTRAVIOLET_LIGHT -> R.integer.equipment_requirement_buycost_20
        EvidenceCost.FREEZING_TEMPERATURE -> R.integer.equipment_requirement_buycost_30
        EvidenceCost.GHOST_ORBS -> R.integer.equipment_requirement_buycost_50
        EvidenceCost.GHOST_WRITING -> R.integer.equipment_requirement_buycost_40
        EvidenceCost.SPIRIT_BOX -> R.integer.equipment_requirement_buycost_50
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

@StringRes fun EvidenceTierDescription.toStringResource(): Int =
    when (this) {
        EvidenceTierDescription.DOTS_1 -> R.string.shop_equipment_dots_data_info_1
        EvidenceTierDescription.DOTS_2 -> R.string.shop_equipment_dots_data_info_2
        EvidenceTierDescription.DOTS_3 -> R.string.shop_equipment_dots_data_info_3
        EvidenceTierDescription.EMF_5_1 -> R.string.shop_equipment_emf_data_info_1
        EvidenceTierDescription.EMF_5_2 -> R.string.shop_equipment_emf_data_info_2
        EvidenceTierDescription.EMF_5_3 -> R.string.shop_equipment_emf_data_info_3
        EvidenceTierDescription.ULTRAVIOLET_LIGHT_1 -> R.string.shop_equipment_uvlight_data_info_1
        EvidenceTierDescription.ULTRAVIOLET_LIGHT_2 -> R.string.shop_equipment_uvlight_data_info_2
        EvidenceTierDescription.ULTRAVIOLET_LIGHT_3 -> R.string.shop_equipment_uvlight_data_info_3
        EvidenceTierDescription.FREEZING_TEMPERATURE_1 -> R.string.shop_equipment_thermometer_data_info_1
        EvidenceTierDescription.FREEZING_TEMPERATURE_2 -> R.string.shop_equipment_thermometer_data_info_2
        EvidenceTierDescription.FREEZING_TEMPERATURE_3 -> R.string.shop_equipment_thermometer_data_info_3
        EvidenceTierDescription.GHOST_ORBS_1 -> R.string.shop_equipment_videocamera_data_info_1
        EvidenceTierDescription.GHOST_ORBS_2 -> R.string.shop_equipment_videocamera_data_info_2
        EvidenceTierDescription.GHOST_ORBS_3 -> R.string.shop_equipment_videocamera_data_info_3
        EvidenceTierDescription.GHOST_WRITING_1 -> R.string.shop_equipment_ghostwritingbook_data_info_1
        EvidenceTierDescription.GHOST_WRITING_2 -> R.string.shop_equipment_ghostwritingbook_data_info_2
        EvidenceTierDescription.GHOST_WRITING_3 -> R.string.shop_equipment_ghostwritingbook_data_info_3
        EvidenceTierDescription.SPIRIT_BOX_1 -> R.string.shop_equipment_spiritbox_data_info_1
        EvidenceTierDescription.SPIRIT_BOX_2 -> R.string.shop_equipment_spiritbox_data_info_2
        EvidenceTierDescription.SPIRIT_BOX_3 -> R.string.shop_equipment_spiritbox_data_info_3
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
        EvidenceTierAnimation.GHOST_WRITING_1 -> R.drawable.anim_writing_1
        EvidenceTierAnimation.GHOST_WRITING_2 -> R.drawable.anim_writing_2
        EvidenceTierAnimation.GHOST_WRITING_3 -> R.drawable.anim_writing_3
        EvidenceTierAnimation.SPIRIT_BOX_1 -> R.drawable.example_spirit_box_cropped_0
        EvidenceTierAnimation.SPIRIT_BOX_2 -> R.drawable.example_spirit_box_cropped_0
        EvidenceTierAnimation.SPIRIT_BOX_3 -> R.drawable.example_spirit_box_cropped_0
    }

@StringRes fun EvidenceTierRequiredLevel.toStringResource(): Int =
    when (this) {
        EvidenceTierRequiredLevel.DOTS_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.DOTS_2 -> R.integer.equipment_requirement_level_29
        EvidenceTierRequiredLevel.DOTS_3 -> R.integer.equipment_requirement_level_60
        EvidenceTierRequiredLevel.EMF_5_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.EMF_5_2 -> R.integer.equipment_requirement_level_20
        EvidenceTierRequiredLevel.EMF_5_3 -> R.integer.equipment_requirement_level_52
        EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_2 -> R.integer.equipment_requirement_level_21
        EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_3 -> R.integer.equipment_requirement_level_56
        EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_2 -> R.integer.equipment_requirement_level_36
        EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_3 -> R.integer.equipment_requirement_level_64
        EvidenceTierRequiredLevel.GHOST_ORBS_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.GHOST_ORBS_2 -> R.integer.equipment_requirement_level_33
        EvidenceTierRequiredLevel.GHOST_ORBS_3 -> R.integer.equipment_requirement_level_61
        EvidenceTierRequiredLevel.GHOST_WRITING_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.GHOST_WRITING_2 -> R.integer.equipment_requirement_level_23
        EvidenceTierRequiredLevel.GHOST_WRITING_3 -> R.integer.equipment_requirement_level_63
        EvidenceTierRequiredLevel.SPIRIT_BOX_1 -> R.integer.equipment_requirement_level_0
        EvidenceTierRequiredLevel.SPIRIT_BOX_2 -> R.integer.equipment_requirement_level_27
        EvidenceTierRequiredLevel.SPIRIT_BOX_3 -> R.integer.equipment_requirement_level_54
    }
