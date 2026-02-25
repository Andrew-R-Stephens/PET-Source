package com.tritiumgaming.feature.investigation.app.mappers.ghost

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntSanityBounds
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntCooldown
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostSpeed
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.data.ghost.model.SanityBounds

@StringRes fun GhostIdentifier.toStringResource(): Int =
    when (this) {
        GhostIdentifier.BANSHEE -> R.string.ghost_id_banshee
        GhostIdentifier.DAYAN -> R.string.ghost_id_dayan
        GhostIdentifier.DEMON -> R.string.ghost_id_demon
        GhostIdentifier.DEOGEN -> R.string.ghost_id_deogen
        GhostIdentifier.GALLU -> R.string.ghost_id_gallu
        GhostIdentifier.GORYO -> R.string.ghost_id_goryo
        GhostIdentifier.HANTU -> R.string.ghost_id_hantu
        GhostIdentifier.JINN -> R.string.ghost_id_jinn
        GhostIdentifier.MARE -> R.string.ghost_id_mare
        GhostIdentifier.MOROI -> R.string.ghost_id_moroi
        GhostIdentifier.MYLING -> R.string.ghost_id_myling
        GhostIdentifier.OBAKE -> R.string.ghost_id_obake
        GhostIdentifier.OBAMBO -> R.string.ghost_id_obambo
        GhostIdentifier.ONI -> R.string.ghost_id_oni
        GhostIdentifier.ONRYO -> R.string.ghost_id_onryo
        GhostIdentifier.PHANTOM -> R.string.ghost_id_phantom
        GhostIdentifier.POLTERGEIST -> R.string.ghost_id_poltergeist
        GhostIdentifier.RAIJU -> R.string.ghost_id_raiju
        GhostIdentifier.REVENANT -> R.string.ghost_id_revenant
        GhostIdentifier.SHADE -> R.string.ghost_id_shade
        GhostIdentifier.SPIRIT -> R.string.ghost_id_spirit
        GhostIdentifier.THAYE -> R.string.ghost_id_thaye
        GhostIdentifier.THE_TWINS -> R.string.ghost_id_thetwins
        GhostIdentifier.THE_MIMIC -> R.string.ghost_id_themimic
        GhostIdentifier.WRAITH -> R.string.ghost_id_wraith
        GhostIdentifier.YOKAI -> R.string.ghost_id_yokai
        GhostIdentifier.YUREI -> R.string.ghost_id_yurei
    }

@StringRes fun GhostTitle.toStringResource(): Int =
    when (this) {
        GhostTitle.BANSHEE -> R.string.ghost_type_banshee
        GhostTitle.DAYAN -> R.string.ghost_type_dayan
        GhostTitle.DEMON -> R.string.ghost_type_demon
        GhostTitle.DEOGEN -> R.string.ghost_type_deogen
        GhostTitle.GALLU -> R.string.ghost_type_gallu
        GhostTitle.GORYO -> R.string.ghost_type_goryo
        GhostTitle.HANTU -> R.string.ghost_type_hantu
        GhostTitle.JINN -> R.string.ghost_type_jinn
        GhostTitle.MARE -> R.string.ghost_type_mare
        GhostTitle.MOROI -> R.string.ghost_type_moroi
        GhostTitle.MYLING -> R.string.ghost_type_myling
        GhostTitle.OBAKE -> R.string.ghost_type_obake
        GhostTitle.OBAMBO -> R.string.ghost_type_obambo
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

@DrawableRes fun GhostIcon.toDrawableResource(): Int =
    when (this) {
        GhostIcon.BANSHEE -> R.drawable.icon_ghost_banshee
        GhostIcon.DAYAN -> R.drawable.icon_ghost_unknown
        GhostIcon.DEMON -> R.drawable.icon_ghost_demon
        GhostIcon.DEOGEN -> R.drawable.icon_ghost_deogen
        GhostIcon.GALLU -> R.drawable.icon_ghost_unknown
        GhostIcon.GORYO -> R.drawable.icon_ghost_goryo
        GhostIcon.HANTU -> R.drawable.icon_ghost_hantu
        GhostIcon.JINN -> R.drawable.icon_ghost_jinn
        GhostIcon.MARE -> R.drawable.icon_ghost_mare
        GhostIcon.MOROI -> R.drawable.icon_ghost_moroi
        GhostIcon.MYLING -> R.drawable.icon_ghost_myling
        GhostIcon.OBAKE -> R.drawable.icon_ghost_obake
        GhostIcon.OBAMBO -> R.drawable.icon_ghost_unknown
        GhostIcon.ONI -> R.drawable.icon_ghost_oni
        GhostIcon.ONRYO -> R.drawable.icon_ghost_onryo
        GhostIcon.PHANTOM -> R.drawable.icon_ghost_phantom
        GhostIcon.POLTERGEIST -> R.drawable.icon_ghost_poltergeist
        GhostIcon.RAIJU -> R.drawable.icon_ghost_raiju
        GhostIcon.REVENANT -> R.drawable.icon_ghost_revenant
        GhostIcon.SHADE -> R.drawable.icon_ghost_shade
        GhostIcon.SPIRIT -> R.drawable.icon_ghost_spirit
        GhostIcon.THAYE -> R.drawable.icon_ghost_thaye
        GhostIcon.THE_MIMIC -> R.drawable.icon_ghost_the_mimic
        GhostIcon.THE_TWINS -> R.drawable.icon_ghost_the_twins
        GhostIcon.WRAITH -> R.drawable.icon_ghost_wraith
        GhostIcon.YOKAI -> R.drawable.icon_ghost_yokai
        GhostIcon.YUREI -> R.drawable.icon_ghost_yurei
}

@StringRes fun GhostDescription.toStringResource(): Int =
    when (this) {
        GhostDescription.BANSHEE -> R.string.ghost_info_banshee
        GhostDescription.DAYAN -> R.string.ghost_info_dayan
        GhostDescription.DEMON -> R.string.ghost_info_demon
        GhostDescription.DEOGEN -> R.string.ghost_info_deogen
        GhostDescription.GALLU -> R.string.ghost_info_gallu
        GhostDescription.GORYO -> R.string.ghost_info_goryo
        GhostDescription.HANTU -> R.string.ghost_info_hantu
        GhostDescription.JINN -> R.string.ghost_info_jinn
        GhostDescription.MARE -> R.string.ghost_info_mare
        GhostDescription.MOROI -> R.string.ghost_info_moroi
        GhostDescription.MYLING -> R.string.ghost_info_myling
        GhostDescription.OBAKE -> R.string.ghost_info_obake
        GhostDescription.OBAMBO -> R.string.ghost_info_obambo
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
        GhostStrength.DAYAN -> R.string.ghost_strengths_dayan
        GhostStrength.DEMON -> R.string.ghost_strengths_demon
        GhostStrength.DEOGEN -> R.string.ghost_strengths_deogen
        GhostStrength.GALLU -> R.string.ghost_strengths_gallu
        GhostStrength.GORYO -> R.string.ghost_strengths_goryo
        GhostStrength.HANTU -> R.string.ghost_strengths_hantu
        GhostStrength.JINN -> R.string.ghost_strengths_jinn
        GhostStrength.MARE -> R.string.ghost_strengths_mare
        GhostStrength.MOROI -> R.string.ghost_strengths_moroi
        GhostStrength.MYLING -> R.string.ghost_strengths_myling
        GhostStrength.OBAKE -> R.string.ghost_strengths_obake
        GhostStrength.OBAMBO -> R.string.ghost_strengths_obambo
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
        GhostWeakness.DAYAN -> R.string.ghost_weaknesses_dayan
        GhostWeakness.DEMON -> R.string.ghost_weaknesses_demon
        GhostWeakness.DEOGEN -> R.string.ghost_weaknesses_deogen
        GhostWeakness.GALLU -> R.string.ghost_weaknesses_gallu
        GhostWeakness.GORYO -> R.string.ghost_weaknesses_goryo
        GhostWeakness.HANTU -> R.string.ghost_weaknesses_hantu
        GhostWeakness.JINN -> R.string.ghost_weaknesses_jinn
        GhostWeakness.MARE -> R.string.ghost_weaknesses_mare
        GhostWeakness.MOROI -> R.string.ghost_weaknesses_moroi
        GhostWeakness.MYLING -> R.string.ghost_weaknesses_myling
        GhostWeakness.OBAKE -> R.string.ghost_weaknesses_obake
        GhostWeakness.OBAMBO -> R.string.ghost_weaknesses_obambo
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
        GhostHuntInfo.DAYAN -> R.string.ghost_huntingdata_dayan
        GhostHuntInfo.DEMON -> R.string.ghost_huntingdata_demon
        GhostHuntInfo.DEOGEN -> R.string.ghost_huntingdata_deogen
        GhostHuntInfo.GALLU -> R.string.ghost_huntingdata_gallu
        GhostHuntInfo.GORYO -> R.string.ghost_huntingdata_goryo
        GhostHuntInfo.HANTU -> R.string.ghost_huntingdata_hantu
        GhostHuntInfo.JINN -> R.string.ghost_huntingdata_jinn
        GhostHuntInfo.MARE -> R.string.ghost_huntingdata_mare
        GhostHuntInfo.MOROI -> R.string.ghost_huntingdata_moroi
        GhostHuntInfo.MYLING -> R.string.ghost_huntingdata_myling
        GhostHuntInfo.OBAKE -> R.string.ghost_huntingdata_obake
        GhostHuntInfo.OBAMBO -> R.string.ghost_huntingdata_obambo
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

fun GhostSpeed.toMinimumAsInt(): Int =
    when (this) {
        GhostSpeed.BANSHEE -> 102
        GhostSpeed.DAYAN -> 72
        GhostSpeed.DEMON -> 102
        GhostSpeed.DEOGEN -> 24
        GhostSpeed.GALLU -> 81
        GhostSpeed.GORYO -> 102
        GhostSpeed.HANTU -> 84
        GhostSpeed.JINN -> 102
        GhostSpeed.MARE -> 102
        GhostSpeed.MOROI -> 90
        GhostSpeed.MYLING -> 102
        GhostSpeed.OBAKE -> 102
        GhostSpeed.OBAMBO -> 87
        GhostSpeed.ONI -> 102
        GhostSpeed.ONRYO -> 102
        GhostSpeed.PHANTOM -> 102
        GhostSpeed.POLTERGEIST -> 102
        GhostSpeed.RAIJU -> 102
        GhostSpeed.REVENANT -> 60
        GhostSpeed.SHADE -> 102
        GhostSpeed.SPIRIT -> 102
        GhostSpeed.THAYE -> 60
        GhostSpeed.THE_TWINS -> 102
        GhostSpeed.THE_MIMIC -> 22
        GhostSpeed.WRAITH -> 102
        GhostSpeed.YOKAI -> 102
        GhostSpeed.YUREI -> 102
    }

fun GhostSpeed.toMaximumAsInt(): Int =
    when (this) {
        GhostSpeed.BANSHEE -> -1
        GhostSpeed.DAYAN -> 135
        GhostSpeed.DEMON -> -1
        GhostSpeed.DEOGEN -> 180
        GhostSpeed.GALLU -> 118
        GhostSpeed.GORYO -> -1
        GhostSpeed.HANTU -> 162
        GhostSpeed.JINN -> 150
        GhostSpeed.MARE -> -1
        GhostSpeed.MOROI -> 135
        GhostSpeed.MYLING -> -1
        GhostSpeed.OBAKE -> -1
        GhostSpeed.OBAMBO -> 118
        GhostSpeed.ONI -> -1
        GhostSpeed.ONRYO -> -1
        GhostSpeed.PHANTOM -> -1
        GhostSpeed.POLTERGEIST -> -1
        GhostSpeed.RAIJU -> 150
        GhostSpeed.REVENANT -> 180
        GhostSpeed.SHADE -> -1
        GhostSpeed.SPIRIT -> -1
        GhostSpeed.THAYE -> 165
        GhostSpeed.THE_TWINS -> -1
        GhostSpeed.THE_MIMIC -> 114
        GhostSpeed.WRAITH -> -1
        GhostSpeed.YOKAI -> -1
        GhostSpeed.YUREI -> -1
    }

fun GhostSpeed.toHasLosMultiplierBoolean(): Boolean =
      when (this) {
        GhostSpeed.BANSHEE -> true
        GhostSpeed.DAYAN -> false
        GhostSpeed.DEMON -> false
        GhostSpeed.DEOGEN -> true
        GhostSpeed.GALLU -> true
        GhostSpeed.GORYO -> true
        GhostSpeed.HANTU -> false
        GhostSpeed.JINN -> false
        GhostSpeed.MARE -> true
        GhostSpeed.MOROI -> true
        GhostSpeed.MYLING -> true
        GhostSpeed.OBAKE -> true
        GhostSpeed.OBAMBO -> true
        GhostSpeed.ONI -> true
        GhostSpeed.ONRYO -> true
        GhostSpeed.PHANTOM -> true
        GhostSpeed.POLTERGEIST -> true
        GhostSpeed.RAIJU -> false
        GhostSpeed.REVENANT -> false
        GhostSpeed.SHADE -> true
        GhostSpeed.SPIRIT -> true
        GhostSpeed.THAYE -> false
        GhostSpeed.THE_TWINS -> true
        GhostSpeed.THE_MIMIC -> true
        GhostSpeed.WRAITH -> true
        GhostSpeed.YOKAI -> true
        GhostSpeed.YUREI -> true
    }

fun HuntSanityBounds.toSanityBounds(): SanityBounds =
    when (this) {
        HuntSanityBounds.BANSHEE -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.DAYAN -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = 45, empowered = 65)
        HuntSanityBounds.DEMON -> SanityBounds(
            normal = 70, suppressed = SanityBounds.UNSET, empowered = 100)
        HuntSanityBounds.DEOGEN -> SanityBounds(
            normal = 40, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.GALLU -> SanityBounds(
            normal = 50, suppressed = 40, empowered = 60)
        HuntSanityBounds.GORYO -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.HANTU -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.JINN -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.MARE -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = 40, empowered = 60)
        HuntSanityBounds.MOROI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.MYLING -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.OBAKE -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.OBAMBO -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = 65, empowered = 10)
        HuntSanityBounds.ONI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.ONRYO -> SanityBounds(
            normal = 60, suppressed = 40, empowered = 100)
        HuntSanityBounds.PHANTOM -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.POLTERGEIST -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.RAIJU -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = 65)
        HuntSanityBounds.REVENANT -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.SHADE -> SanityBounds(
            normal = 35, suppressed = 0, empowered = SanityBounds.UNSET)
        HuntSanityBounds.SPIRIT -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.THAYE -> SanityBounds(
            normal = 75, suppressed = 15, empowered = SanityBounds.UNSET)
        HuntSanityBounds.THE_TWINS -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.THE_MIMIC -> SanityBounds(
            normal = 50, suppressed = 0, empowered = 100)
        HuntSanityBounds.WRAITH -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.YOKAI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = 80)
        HuntSanityBounds.YUREI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
    }

fun HuntCooldown.toLong(): Long =
    when (this) {
        HuntCooldown.BANSHEE -> 25000L
        HuntCooldown.DAYAN -> 25000L
        HuntCooldown.DEMON -> 20000L
        HuntCooldown.DEOGEN -> 25000L
        HuntCooldown.GALLU -> 25000L
        HuntCooldown.GORYO -> 25000L
        HuntCooldown.HANTU -> 25000L
        HuntCooldown.JINN -> 25000L
        HuntCooldown.MARE -> 25000L
        HuntCooldown.MOROI -> 25000L
        HuntCooldown.MYLING -> 25000L
        HuntCooldown.OBAKE -> 25000L
        HuntCooldown.OBAMBO -> 25000L
        HuntCooldown.ONI -> 25000L
        HuntCooldown.ONRYO -> 25000L
        HuntCooldown.PHANTOM -> 25000L
        HuntCooldown.POLTERGEIST -> 25000L
        HuntCooldown.RAIJU -> 25000L
        HuntCooldown.REVENANT -> 25000L
        HuntCooldown.SHADE -> 25000L
        HuntCooldown.SPIRIT -> 25000L
        HuntCooldown.THAYE -> 25000L
        HuntCooldown.THE_TWINS -> 25000L
        HuntCooldown.THE_MIMIC -> 25000L
        HuntCooldown.WRAITH -> 25000L
        HuntCooldown.YOKAI -> 25000L
        HuntCooldown.YUREI -> 25000L
    }
