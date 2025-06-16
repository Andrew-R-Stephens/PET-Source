package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources

fun MissionResources.MissionContent.toStringResource(): Int =
    when (this) {
        MissionResources.MissionContent.WITNESS_A_GHOST_EVENT -> R.string.objective_info_ghostevent
        MissionResources.MissionContent.PHOTOGRAPH_THE_GHOST -> R.string.objective_info_ghostphotograph
        MissionResources.MissionContent.GET_EMF_READING -> R.string.objective_info_emfreader
        MissionResources.MissionContent.GET_MOTION_SENSOR_ACTIVITY -> R.string.objective_info_motionsensor
        MissionResources.MissionContent.SMUDGE_THE_GHOST_LOCATION -> R.string.objective_info_smudgestick
        MissionResources.MissionContent.PREVENT_HUNT_WITH_CRUCIFIX -> R.string.objective_info_crucifix
        MissionResources.MissionContent.GHOST_WALKS_THROUGH_SALT -> R.string.objective_info_salt
        MissionResources.MissionContent.ESCAPE_A_GHOST_HUNT -> R.string.objective_info_escapehunt
        MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE -> R.string.objective_info_repelwithsmudge
        MissionResources.MissionContent.GHOST_BLOW_OUT_CANDLE -> R.string.objective_info_extinguishcandle
        MissionResources.MissionContent.GET_AVERAGE_SANITY_AT_OR_BELOW_25 -> R.string.objective_info_lowsanity
    }