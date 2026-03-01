package com.tritiumgaming.feature.investigation.app.mappers.difficultysettings

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityLevel
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityMonitor
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ChangingFavoriteRoom
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.DoorsStartingOpen
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.EventFrequency
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.EvidenceGiven
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FingerprintChance
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FingerprintDuration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Flashlights
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FriendlyGhost
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuzeBoxAtStartOfContract
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuzeBoxVisibleOnMap
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.GhostSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.GracePeriod
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.HuntDuration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.KillsExtendHunts
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.LoseItemsAndConsumables
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.NumberOfHidingPlaces
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.PlayerSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.RoamingFrequency
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SanityDrainSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SanityMonitor
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SanityPillRestoration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SetupTime
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Sprinting
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.StartingSanity
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

fun StartingSanity.toFloat(): Float =
    when(this) {
        StartingSanity.SANITY_0 -> 0f
        StartingSanity.SANITY_25 -> .25f
        StartingSanity.SANITY_50 -> .5f
        StartingSanity.SANITY_75 -> .75f
        StartingSanity.SANITY_100 -> 1f
    }

fun SanityPillRestoration.toFloat(): Float =
    when(this) {
        SanityPillRestoration.RESTORE_0 -> 0f
        SanityPillRestoration.RESTORE_5 -> .05f
        SanityPillRestoration.RESTORE_10 -> .1f
        SanityPillRestoration.RESTORE_20 -> .2f
        SanityPillRestoration.RESTORE_25 -> .25f
        SanityPillRestoration.RESTORE_30 -> .3f
        SanityPillRestoration.RESTORE_35 -> .35f
        SanityPillRestoration.RESTORE_40 -> .4f
        SanityPillRestoration.RESTORE_45 -> .45f
        SanityPillRestoration.RESTORE_50 -> .5f
        SanityPillRestoration.RESTORE_75 -> .75f
        SanityPillRestoration.RESTORE_100 -> 1f
    }

fun SanityDrainSpeed.toFloat(): Float =
    when(this) {
        SanityDrainSpeed.SPEED_0 -> 0f
        SanityDrainSpeed.SPEED_50 -> .5f
        SanityDrainSpeed.SPEED_100 -> 1f
        SanityDrainSpeed.SPEED_150 -> 1.5f
        SanityDrainSpeed.SPEED_200 -> 2.0f
    }

fun Sprinting.toStringResource(): Int =
    when(this) {
        Sprinting.OFF -> R.string.difficulty_setting_state_off
        Sprinting.ON -> R.string.difficulty_setting_state_on
        Sprinting.SPRINT_INFINITE -> R.string.difficulty_setting_state_infinite
    }

fun PlayerSpeed.toFloat(): Float =
    when(this) {
        PlayerSpeed.SPEED_50 -> .5f
        PlayerSpeed.SPEED_75 -> .75f
        PlayerSpeed.SPEED_100 -> 1f
        PlayerSpeed.SPEED_125 -> 1.25f
        PlayerSpeed.SPEED_150 -> 1.5f
    }

fun Flashlights.toStringResource(): Int =
    when(this) {
        Flashlights.OFF -> R.string.difficulty_setting_state_off
        Flashlights.ON -> R.string.difficulty_setting_state_on
    }

fun LoseItemsAndConsumables.toStringResource(): Int =
    when(this) {
        LoseItemsAndConsumables.OFF -> R.string.difficulty_setting_state_off
        LoseItemsAndConsumables.ON -> R.string.difficulty_setting_state_on
    }

fun GhostSpeed.toFloat(): Float =
    when(this) {
        GhostSpeed.SPEED_50 -> .5f
        GhostSpeed.SPEED_75 -> .75f
        GhostSpeed.SPEED_100 -> 1f
        GhostSpeed.SPEED_125 -> 1.25f
        GhostSpeed.SPEED_150 -> 1.5f
    }

@StringRes fun RoamingFrequency.toStringResource(): Int =
    when(this) {
        RoamingFrequency.LOW -> R.string.difficulty_setting_state_low
        RoamingFrequency.MEDIUM -> R.string.difficulty_setting_state_medium
        RoamingFrequency.HIGH -> R.string.difficulty_setting_state_high
    }

@StringRes fun ChangingFavoriteRoom.toStringResource(): Int =
    when(this) {
        ChangingFavoriteRoom.NONE -> R.string.difficulty_setting_state_none
        ChangingFavoriteRoom.LOW -> R.string.difficulty_setting_state_low
        ChangingFavoriteRoom.MEDIUM -> R.string.difficulty_setting_state_medium
        ChangingFavoriteRoom.HIGH -> R.string.difficulty_setting_state_high
        ChangingFavoriteRoom.VERY_HIGH -> R.string.difficulty_setting_state_veryHigh
    }

@StringRes fun ActivityLevel.toStringResource(): Int =
    when(this) {
        ActivityLevel.LOW -> R.string.difficulty_setting_state_low
        ActivityLevel.MEDIUM -> R.string.difficulty_setting_state_medium
        ActivityLevel.HIGH -> R.string.difficulty_setting_state_high
    }

@StringRes fun EventFrequency.toStringResource(): Int =
    when(this) {
        EventFrequency.LOW -> R.string.difficulty_setting_state_low
        EventFrequency.MEDIUM -> R.string.difficulty_setting_state_medium
        EventFrequency.HIGH -> R.string.difficulty_setting_state_high
    }

@StringRes fun FriendlyGhost.toStringResource(): Int =
    when(this) {
        FriendlyGhost.OFF -> R.string.difficulty_setting_state_off
        FriendlyGhost.ON -> R.string.difficulty_setting_state_on
    }

@StringRes fun GracePeriod.toLong(): Long =
    when(this) {
        GracePeriod.PERIOD_0 -> 0L
        GracePeriod.PERIOD_1 -> 1000L
        GracePeriod.PERIOD_2 -> 2000L
        GracePeriod.PERIOD_3 -> 3000L
        GracePeriod.PERIOD_4 -> 4000L
        GracePeriod.PERIOD_5 -> 5000L
    }

@StringRes fun HuntDuration.toStringResource(): Int =
    when(this) {
        HuntDuration.LOW -> R.string.difficulty_setting_state_low
        HuntDuration.MEDIUM -> R.string.difficulty_setting_state_medium
        HuntDuration.HIGH -> R.string.difficulty_setting_state_high
    }

@StringRes fun KillsExtendHunts.toStringResource(): Int =
    when(this) {
        KillsExtendHunts.OFF -> R.string.difficulty_setting_state_off
        KillsExtendHunts.LOW -> R.string.difficulty_setting_state_low
        KillsExtendHunts.MEDIUM -> R.string.difficulty_setting_state_medium
        KillsExtendHunts.HIGH -> R.string.difficulty_setting_state_high
    }

fun EvidenceGiven.toInt(): Int =
    when(this) {
        EvidenceGiven.COUNT_0 -> 1
        EvidenceGiven.COUNT_1 -> 2
        EvidenceGiven.COUNT_2 -> 3
        EvidenceGiven.COUNT_3 -> 4
    }

fun FingerprintChance.toFloat(): Float =
    when(this) {
        FingerprintChance.CHANCE_0 -> 0f
        FingerprintChance.CHANCE_25 -> .25f
        FingerprintChance.CHANCE_50 -> .50f
        FingerprintChance.CHANCE_75 -> .75f
        FingerprintChance.CHANCE_100 -> 1f
    }

fun FingerprintDuration.toLong(): Long =
    when(this) {
        FingerprintDuration.DURATION_NEVER -> 0L
        FingerprintDuration.DURATION_15 -> 15000L
        FingerprintDuration.DURATION_30 -> 30000L
        FingerprintDuration.DURATION_60 -> 60000L
        FingerprintDuration.DURATION_90 -> 90000L
        FingerprintDuration.DURATION_120 -> 120000L
        FingerprintDuration.DURATION_180 -> 180000L
        FingerprintDuration.DURATION_INFINITE -> -1L
    }

fun SetupTime.toLong(): Long =
    when(this) {
        SetupTime.TIME_0 -> 0L
        SetupTime.TIME_30 -> 30000L
        SetupTime.TIME_60 -> 60000L
        SetupTime.TIME_120 -> 120000L
        SetupTime.TIME_180 -> 180000L
        SetupTime.TIME_240 -> 240000L
        SetupTime.TIME_300 -> 300000L
    }

@StringRes fun Weather.toStringResource(): Int =
    when(this) {
        Weather.RANDOM -> R.string.difficulty_setting_state_random
        Weather.LIGHT_RAIN -> R.string.difficulty_setting_state_weather_lightrain
        Weather.HEAVY_RAIN -> R.string.difficulty_setting_state_weather_heavyrain
        Weather.SNOW -> R.string.difficulty_setting_state_weather_snow
        Weather.WINDY -> R.string.difficulty_setting_state_weather_windy
        Weather.CLEAR -> R.string.difficulty_setting_state_weather_clear
        Weather.FOG -> R.string.difficulty_setting_state_weather_fog
        Weather.SUNRISE -> R.string.difficulty_setting_state_weather_sunrise
    }

@StringRes fun DoorsStartingOpen.toStringResource(): Int =
    when(this) {
        DoorsStartingOpen.NONE -> R.string.difficulty_setting_state_none
        DoorsStartingOpen.LOW -> R.string.difficulty_setting_state_low
        DoorsStartingOpen.MEDIUM -> R.string.difficulty_setting_state_medium
        DoorsStartingOpen.HIGH -> R.string.difficulty_setting_state_high
    }

@StringRes fun NumberOfHidingPlaces.toStringResource(): Int =
    when(this) {
        NumberOfHidingPlaces.NONE -> R.string.difficulty_setting_state_none
        NumberOfHidingPlaces.LOW -> R.string.difficulty_setting_state_low
        NumberOfHidingPlaces.MEDIUM -> R.string.difficulty_setting_state_medium
        NumberOfHidingPlaces.HIGH -> R.string.difficulty_setting_state_high
        NumberOfHidingPlaces.VERY_HIGH -> R.string.difficulty_setting_state_veryHigh
    }

@StringRes fun SanityMonitor.toStringResource(): Int =
    when(this) {
        SanityMonitor.OFF -> R.string.difficulty_setting_state_off
        SanityMonitor.ON -> R.string.difficulty_setting_state_on
    }

@StringRes fun ActivityMonitor.toStringResource(): Int =
    when(this) {
        ActivityMonitor.OFF -> R.string.difficulty_setting_state_off
        ActivityMonitor.ON -> R.string.difficulty_setting_state_on
    }

@StringRes fun FuzeBoxAtStartOfContract.toStringResource(): Int =
    when(this) {
        FuzeBoxAtStartOfContract.BROKEN -> R.string.difficulty_setting_state_broken
        FuzeBoxAtStartOfContract.OFF -> R.string.difficulty_setting_state_off
        FuzeBoxAtStartOfContract.ON -> R.string.difficulty_setting_state_on
    }

@StringRes fun FuzeBoxVisibleOnMap.toStringResource(): Int =
    when(this) {
        FuzeBoxVisibleOnMap.OFF -> R.string.difficulty_setting_state_off
        FuzeBoxVisibleOnMap.ON -> R.string.difficulty_setting_state_on
    }

fun CursedPossessionsQuantity.toInt(): Int =
    when(this) {
        CursedPossessionsQuantity.NONE -> 0
        CursedPossessionsQuantity.QUANTITY_1 -> 1
        CursedPossessionsQuantity.QUANTITY_2 -> 2
        CursedPossessionsQuantity.QUANTITY_3 -> 3
        CursedPossessionsQuantity.QUANTITY_4 -> 4
        CursedPossessionsQuantity.QUANTITY_5 -> 5
        CursedPossessionsQuantity.QUANTITY_6 -> 6
        CursedPossessionsQuantity.QUANTITY_7 -> 7
    }

@StringRes fun CursedPossession.toStringResource(): Int =
    when(this) {
        CursedPossession.RANDOM -> R.string.difficulty_setting_response_unknown
        CursedPossession.TAROT_CARDS -> R.string.cursedpossessions_info_name_tarotcards
        CursedPossession.OUIJA_BOARD -> R.string.cursedpossessions_info_name_ouijaboard
        CursedPossession.HAUNTED_MIRROR -> R.string.cursedpossessions_info_name_mirror
        CursedPossession.MUSIC_BOX -> R.string.cursedpossessions_info_name_musicbox
        CursedPossession.SUMMONING_CIRCLE -> R.string.cursedpossessions_info_name_summoningcircle
        CursedPossession.VOODOO_DOLL -> R.string.cursedpossessions_info_name_voodoodoll
        CursedPossession.MONKEY_PAW -> R.string.cursedpossessions_info_name_monkeypaw
    }