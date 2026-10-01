package com.tritiumgaming.data.mission.mappers

import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.mission.mappers.MissionResources.MissionContent

fun MissionContent.toStringResource(): Int =
    when (this) {
        MissionContent.WITNESS_A_GHOST_EVENT -> R.string.objective_info_ghostevent
        MissionContent.PHOTOGRAPH_THE_GHOST -> R.string.objective_info_ghostphotograph
        MissionContent.GET_MOTION_SENSOR_ACTIVITY -> R.string.objective_info_motionsensor
        MissionContent.SMUDGE_THE_GHOST_LOCATION -> R.string.objective_info_smudgestick
        MissionContent.PREVENT_HUNT_WITH_CRUCIFIX -> R.string.objective_info_crucifix
        MissionContent.ESCAPE_A_GHOST_HUNT -> R.string.objective_info_escapehunt
        MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE -> R.string.objective_info_repelwithsmudge
        MissionContent.GHOST_BLOW_OUT_CANDLE -> R.string.objective_info_extinguishcandle
        MissionContent.GET_AVERAGE_SANITY_AT_OR_BELOW_25 -> R.string.objective_info_lowsanity
        MissionContent.DETECT_SOUND_PARABOLIC_MICROPHONE -> R.string.objective_info_paranormalsound
        MissionContent.CAPTURE_UNIQUE_AUDIO -> R.string.objective_info_uniqueaudio
        MissionContent.CAPTURE_UNIQUE_PHOTOGRAPH -> R.string.objective_info_uniquephotograph
        MissionContent.CAPTURE_UNIQUE_VIDEO -> R.string.objective_info_uniquevideo
        MissionContent.CAPTURE_EMF_PHOTOGRAPH -> R.string.objective_info_emfphotograph
        MissionContent.CAPTURE_GHOST_VIDEO -> R.string.objective_info_ghostvideo
    }