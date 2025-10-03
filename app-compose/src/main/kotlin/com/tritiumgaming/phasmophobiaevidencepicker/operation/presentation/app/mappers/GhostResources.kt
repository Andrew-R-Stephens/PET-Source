package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostWeakness

@StringRes fun GhostTitle.toStringResource(): Int =
    when (this) {
        GhostTitle.BANSHEE -> R.string.ghost_type_banshee
        GhostTitle.DEMON -> R.string.ghost_type_demon
        GhostTitle.DEOGEN -> R.string.ghost_type_deogen
        GhostTitle.GORYO -> R.string.ghost_type_goryo
        GhostTitle.HANTU -> R.string.ghost_type_hantu
        GhostTitle.JINN -> R.string.ghost_type_jinn
        GhostTitle.MARE -> R.string.ghost_type_mare
        GhostTitle.MOROI -> R.string.ghost_type_moroi
        GhostTitle.MYLING -> R.string.ghost_type_myling
        GhostTitle.OBAKE -> R.string.ghost_type_obake
        GhostTitle.ONI -> R.string.ghost_type_oni
        GhostTitle.ONRYO -> R.string.ghost_type_onryo
        GhostTitle.PHANTOM -> R.string.ghost_type_phantom
        GhostTitle.POLTERGEIST -> R.string.ghost_type_poltergeist
        GhostTitle.RAIJU -> R.string.ghost_type_raiju
        GhostTitle.REVENANT -> R.string.ghost_type_revenant
        GhostTitle.SHADE -> R.string.ghost_type_shade
        GhostTitle.SPIRIT -> R.string.ghost_type_spirit
        GhostTitle.THAYE -> R.string.ghost_type_thaye
        GhostTitle.THE_TWINS -> R.string.ghost_type_thetwins
        GhostTitle.THE_MIMIC -> R.string.ghost_type_themimic
        GhostTitle.WRAITH -> R.string.ghost_type_wraith
        GhostTitle.YOKAI -> R.string.ghost_type_yokai
        GhostTitle.YUREI -> R.string.ghost_type_yurei
    }

@StringRes fun GhostDescription.toStringResource(): Int =
    when (this) {
        GhostDescription.BANSHEE -> R.string.ghost_info_banshee
        GhostDescription.DEMON -> R.string.ghost_info_demon
        GhostDescription.DEOGEN -> R.string.ghost_info_deogen
        GhostDescription.GORYO -> R.string.ghost_info_goryo
        GhostDescription.HANTU -> R.string.ghost_info_hantu
        GhostDescription.JINN -> R.string.ghost_info_jinn
        GhostDescription.MARE -> R.string.ghost_info_mare
        GhostDescription.MOROI -> R.string.ghost_info_moroi
        GhostDescription.MYLING -> R.string.ghost_info_myling
        GhostDescription.OBAKE -> R.string.ghost_info_obake
        GhostDescription.ONI -> R.string.ghost_info_oni
        GhostDescription.ONRYO -> R.string.ghost_info_onryo
        GhostDescription.PHANTOM -> R.string.ghost_info_phantom
        GhostDescription.POLTERGEIST -> R.string.ghost_info_poltergeist
        GhostDescription.RAIJU -> R.string.ghost_info_raiju
        GhostDescription.REVENANT -> R.string.ghost_info_revenant
        GhostDescription.SHADE -> R.string.ghost_info_shade
        GhostDescription.SPIRIT -> R.string.ghost_info_spirit
        GhostDescription.THAYE -> R.string.ghost_info_thaye
        GhostDescription.THE_TWINS -> R.string.ghost_info_thetwins
        GhostDescription.THE_MIMIC -> R.string.ghost_info_themimic
        GhostDescription.WRAITH -> R.string.ghost_info_wraith
        GhostDescription.YOKAI -> R.string.ghost_info_yokai
        GhostDescription.YUREI -> R.string.ghost_info_yurei
    }

@StringRes fun GhostStrength.toStringResource(): Int =
    when (this) {
        GhostStrength.BANSHEE -> R.string.ghost_strengths_banshee
        GhostStrength.DEMON -> R.string.ghost_strengths_demon
        GhostStrength.DEOGEN -> R.string.ghost_strengths_deogen
        GhostStrength.GORYO -> R.string.ghost_strengths_goryo
        GhostStrength.HANTU -> R.string.ghost_strengths_hantu
        GhostStrength.JINN -> R.string.ghost_strengths_jinn
        GhostStrength.MARE -> R.string.ghost_strengths_mare
        GhostStrength.MOROI -> R.string.ghost_strengths_moroi
        GhostStrength.MYLING -> R.string.ghost_strengths_myling
        GhostStrength.OBAKE -> R.string.ghost_strengths_obake
        GhostStrength.ONI -> R.string.ghost_strengths_oni
        GhostStrength.ONRYO -> R.string.ghost_strengths_onryo
        GhostStrength.PHANTOM -> R.string.ghost_strengths_phantom
        GhostStrength.POLTERGEIST -> R.string.ghost_strengths_poltergeist
        GhostStrength.RAIJU -> R.string.ghost_strengths_raiju
        GhostStrength.REVENANT -> R.string.ghost_strengths_revenant
        GhostStrength.SHADE -> R.string.ghost_strengths_shade
        GhostStrength.SPIRIT -> R.string.ghost_strengths_spirit
        GhostStrength.THAYE -> R.string.ghost_strengths_thaye
        GhostStrength.THE_TWINS -> R.string.ghost_strengths_thetwins
        GhostStrength.THE_MIMIC -> R.string.ghost_strengths_themimic
        GhostStrength.WRAITH -> R.string.ghost_strengths_wraith
        GhostStrength.YOKAI -> R.string.ghost_strengths_yokai
        GhostStrength.YUREI -> R.string.ghost_strengths_yurei
    }

@StringRes fun GhostWeakness.toStringResource(): Int =
    when (this) {
        GhostWeakness.BANSHEE -> R.string.ghost_weaknesses_banshee
        GhostWeakness.DEMON -> R.string.ghost_weaknesses_demon
        GhostWeakness.DEOGEN -> R.string.ghost_weaknesses_deogen
        GhostWeakness.GORYO -> R.string.ghost_weaknesses_goryo
        GhostWeakness.HANTU -> R.string.ghost_weaknesses_hantu
        GhostWeakness.JINN -> R.string.ghost_weaknesses_jinn
        GhostWeakness.MARE -> R.string.ghost_weaknesses_mare
        GhostWeakness.MOROI -> R.string.ghost_weaknesses_moroi
        GhostWeakness.MYLING -> R.string.ghost_weaknesses_myling
        GhostWeakness.OBAKE -> R.string.ghost_weaknesses_obake
        GhostWeakness.ONI -> R.string.ghost_weaknesses_oni
        GhostWeakness.ONRYO -> R.string.ghost_weaknesses_onryo
        GhostWeakness.PHANTOM -> R.string.ghost_weaknesses_phantom
        GhostWeakness.POLTERGEIST -> R.string.ghost_weaknesses_poltergeist
        GhostWeakness.RAIJU -> R.string.ghost_weaknesses_raiju
        GhostWeakness.REVENANT -> R.string.ghost_weaknesses_revenant
        GhostWeakness.SHADE -> R.string.ghost_weaknesses_shade
        GhostWeakness.SPIRIT -> R.string.ghost_weaknesses_spirit
        GhostWeakness.THAYE -> R.string.ghost_weaknesses_thaye
        GhostWeakness.THE_TWINS -> R.string.ghost_weaknesses_thetwins
        GhostWeakness.THE_MIMIC -> R.string.ghost_weaknesses_themimic
        GhostWeakness.WRAITH -> R.string.ghost_weaknesses_wraith
        GhostWeakness.YOKAI -> R.string.ghost_weaknesses_yokai
        GhostWeakness.YUREI -> R.string.ghost_weaknesses_yurei
    }

@StringRes fun GhostHuntInfo.toStringResource(): Int =
    when (this) {
        GhostHuntInfo.BANSHEE -> R.string.ghost_huntingdata_banshee
        GhostHuntInfo.DEMON -> R.string.ghost_huntingdata_demon
        GhostHuntInfo.DEOGEN -> R.string.ghost_huntingdata_deogen
        GhostHuntInfo.GORYO -> R.string.ghost_huntingdata_goryo
        GhostHuntInfo.HANTU -> R.string.ghost_huntingdata_hantu
        GhostHuntInfo.JINN -> R.string.ghost_huntingdata_jinn
        GhostHuntInfo.MARE -> R.string.ghost_huntingdata_mare
        GhostHuntInfo.MOROI -> R.string.ghost_huntingdata_moroi
        GhostHuntInfo.MYLING -> R.string.ghost_huntingdata_myling
        GhostHuntInfo.OBAKE -> R.string.ghost_huntingdata_obake
        GhostHuntInfo.ONI -> R.string.ghost_huntingdata_oni
        GhostHuntInfo.ONRYO -> R.string.ghost_huntingdata_onryo
        GhostHuntInfo.PHANTOM -> R.string.ghost_huntingdata_phantom
        GhostHuntInfo.POLTERGEIST -> R.string.ghost_huntingdata_poltergeist
        GhostHuntInfo.RAIJU -> R.string.ghost_huntingdata_raiju
        GhostHuntInfo.REVENANT -> R.string.ghost_huntingdata_revenant
        GhostHuntInfo.SHADE -> R.string.ghost_huntingdata_shade
        GhostHuntInfo.SPIRIT -> R.string.ghost_huntingdata_spirit
        GhostHuntInfo.THAYE -> R.string.ghost_huntingdata_thaye
        GhostHuntInfo.THE_TWINS -> R.string.ghost_huntingdata_thetwins
        GhostHuntInfo.THE_MIMIC -> R.string.ghost_huntingdata_themimic
        GhostHuntInfo.WRAITH -> R.string.ghost_huntingdata_wraith
        GhostHuntInfo.YOKAI -> R.string.ghost_huntingdata_yokai
        GhostHuntInfo.YUREI -> R.string.ghost_huntingdata_yurei
    }
