package com.tritiumstudios.feature.missions.app.mappers.mission

import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.mission.mappers.MissionResources

fun MissionResources.MissionContent.toStringResource(): Int =
    when (this) {
        MissionResources.MissionContent.WITNESS_A_GHOST_EVENT -> R.string.objective_info_ghostevent
        MissionResources.MissionContent.PHOTOGRAPH_THE_GHOST -> R.string.objective_info_ghostphotograph
        MissionResources.MissionContent.GET_MOTION_SENSOR_ACTIVITY -> R.string.objective_info_motionsensor
        MissionResources.MissionContent.SMUDGE_THE_GHOST_LOCATION -> R.string.objective_info_smudgestick
        MissionResources.MissionContent.PREVENT_HUNT_WITH_CRUCIFIX -> R.string.objective_info_crucifix
        MissionResources.MissionContent.ESCAPE_A_GHOST_HUNT -> R.string.objective_info_escapehunt
        MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE -> R.string.objective_info_repelwithsmudge
        MissionResources.MissionContent.GHOST_BLOW_OUT_CANDLE -> R.string.objective_info_extinguishcandle
        MissionResources.MissionContent.GET_AVERAGE_SANITY_AT_OR_BELOW_25 -> R.string.objective_info_lowsanity
        MissionResources.MissionContent.DETECT_SOUND_PARABOLIC_MICROPHONE -> R.string.objective_info_paranormalsound
        MissionResources.MissionContent.CAPTURE_UNIQUE_AUDIO -> R.string.objective_info_uniqueaudio
        MissionResources.MissionContent.CAPTURE_UNIQUE_PHOTOGRAPH -> R.string.objective_info_uniquephotograph
        MissionResources.MissionContent.CAPTURE_UNIQUE_VIDEO -> R.string.objective_info_uniquevideo
        MissionResources.MissionContent.CAPTURE_EMF_PHOTOGRAPH -> R.string.objective_info_emfphotograph
        MissionResources.MissionContent.CAPTURE_GHOST_VIDEO -> R.string.objective_info_ghostvideo
    }