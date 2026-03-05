package com.tritiumgaming.feature.investigation.app.mappers.difficultysettings

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityLevel
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityMonitor
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ChangingFavoriteRoom
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.DifficultySetting
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.DoorsStartingOpen
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.EventFrequency
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.EvidenceGiven
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FingerprintChance
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FingerprintDuration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Flashlights
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FriendlyGhost
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuseBoxAtStartOfContract
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuseBoxVisibleOnMap
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
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources

@StringRes fun DifficultySetting.toStringResource(): Int =
    when(this) {
        DifficultySetting.STARTING_SANITY -> R.string.difficulty_setting_title_starting_sanity
        DifficultySetting.SANITY_PILL_RESTORATION -> R.string.difficulty_setting_title_sanity_pill_restoration
        DifficultySetting.SANITY_DRAIN_SPEED -> R.string.difficulty_setting_title_sanity_drain_speed
        DifficultySetting.SPRINTING -> R.string.difficulty_setting_title_sprinting
        DifficultySetting.PLAYER_SPEED -> R.string.difficulty_setting_title_player_speed
        DifficultySetting.FLASHLIGHTS -> R.string.difficulty_setting_title_flashlights
        DifficultySetting.LOSE_ITEMS_AND_CONSUMABLES -> R.string.difficulty_setting_title_lose_items_and_consumables
        DifficultySetting.GHOST_SPEED -> R.string.difficulty_setting_title_ghost_speed
        DifficultySetting.ROAMING_FREQUENCY -> R.string.difficulty_setting_title_roaming_frequency
        DifficultySetting.CHANGING_FAVOURITE_ROOM -> R.string.difficulty_setting_title_changing_favourite_room
        DifficultySetting.ACTIVITY_LEVEL -> R.string.difficulty_setting_title_activity_level
        DifficultySetting.EVENT_FREQUENCY -> R.string.difficulty_setting_title_event_frequency
        DifficultySetting.FRIENDLY_GHOST -> R.string.difficulty_setting_title_friendly_ghost
        DifficultySetting.GRACE_PERIOD -> R.string.difficulty_setting_title_grace_period
        DifficultySetting.HUNT_DURATION -> R.string.difficulty_setting_title_hunt_duration
        DifficultySetting.KILLS_EXTEND_HUNTS -> R.string.difficulty_setting_title_kills_extend_hunts
        DifficultySetting.EVIDENCE_GIVEN -> R.string.difficulty_setting_title_evidence_given
        DifficultySetting.FINGERPRINT_CHANCE -> R.string.difficulty_setting_title_fingerprint_chance
        DifficultySetting.FINGERPRINT_DURATION -> R.string.difficulty_setting_title_fingerprint_duration
        DifficultySetting.SETUP_TIME -> R.string.difficulty_setting_title_setup_time
        DifficultySetting.WEATHER -> R.string.difficulty_setting_title_weather
        DifficultySetting.DOORS_STARTING_OPEN -> R.string.difficulty_setting_title_doors_starting_open
        DifficultySetting.NUMBER_OF_HIDING_PLACES -> R.string.difficulty_setting_title_number_of_hiding_places
        DifficultySetting.SANITY_MONITOR -> R.string.difficulty_setting_title_sanity_monitor
        DifficultySetting.ACTIVITY_MONITOR -> R.string.difficulty_setting_title_activity_monitor
        DifficultySetting.FUSE_BOX_AT_START_OF_CONTRACT -> R.string.difficulty_setting_title_fuse_box_at_start_of_contract
        DifficultySetting.FUSE_BOX_VISIBLE_ON_MAP -> R.string.difficulty_setting_title_fuse_box_visible_on_map
        DifficultySetting.CURSED_POSSESSIONS_QUANTITY -> R.string.difficulty_setting_title_cursed_possessions_quantity
        DifficultySetting.CURSED_POSSESSIONS -> R.string.difficulty_setting_title_cursed_possession
    }

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

@StringRes fun Sprinting.toStringResource(): Int =
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

@StringRes fun Flashlights.toStringResource(): Int =
    when(this) {
        Flashlights.OFF -> R.string.difficulty_setting_state_off
        Flashlights.ON -> R.string.difficulty_setting_state_on
    }

@StringRes fun LoseItemsAndConsumables.toStringResource(): Int =
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

fun GracePeriod.toLong(): Long =
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

fun HuntDuration.toLong(size: MapModifierResources.MapSize): Long =
    when(this) {
        HuntDuration.LOW -> when(size) {
            MapModifierResources.MapSize.SMALL -> 15000L
            MapModifierResources.MapSize.MEDIUM -> 30000L
            MapModifierResources.MapSize.LARGE -> 40000L
        }
        HuntDuration.MEDIUM -> when(size) {
            MapModifierResources.MapSize.SMALL -> 20000L
            MapModifierResources.MapSize.MEDIUM -> 40000L
            MapModifierResources.MapSize.LARGE -> 50000L
        }
        HuntDuration.HIGH -> when(size) {
            MapModifierResources.MapSize.SMALL -> 30000L
            MapModifierResources.MapSize.MEDIUM -> 50000L
            MapModifierResources.MapSize.LARGE -> 60000L
        }
    }

@StringRes fun KillsExtendHunts.toStringResource(): Int =
    when(this) {
        KillsExtendHunts.OFF -> R.string.difficulty_setting_state_off
        KillsExtendHunts.LOW -> R.string.difficulty_setting_state_low
        KillsExtendHunts.MEDIUM -> R.string.difficulty_setting_state_medium
        KillsExtendHunts.HIGH -> R.string.difficulty_setting_state_high
    }

fun KillsExtendHunts.toLong(size: MapModifierResources.MapSize): Long =
    when(this) {
        KillsExtendHunts.OFF -> 0L
        KillsExtendHunts.LOW -> when(size) {
            MapModifierResources.MapSize.SMALL -> 15000L
            MapModifierResources.MapSize.MEDIUM -> 20000L
            MapModifierResources.MapSize.LARGE -> 25000L
        }
        KillsExtendHunts.MEDIUM -> when(size) {
            MapModifierResources.MapSize.SMALL -> 25000L
            MapModifierResources.MapSize.MEDIUM -> 30000L
            MapModifierResources.MapSize.LARGE -> 35000L
        }
        KillsExtendHunts.HIGH -> when(size) {
            MapModifierResources.MapSize.SMALL -> 35000L
            MapModifierResources.MapSize.MEDIUM -> 40000L
            MapModifierResources.MapSize.LARGE -> 45000L
        }
    }

fun EvidenceGiven.toInt(): Int =
    when(this) {
        EvidenceGiven.COUNT_0 -> 0
        EvidenceGiven.COUNT_1 -> 1
        EvidenceGiven.COUNT_2 -> 2
        EvidenceGiven.COUNT_3 -> 3
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
        Weather.BLOOD_MOON -> R.string.difficulty_setting_state_weather_bloodmoon
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

@StringRes fun FuseBoxAtStartOfContract.toStringResource(): Int =
    when(this) {
        FuseBoxAtStartOfContract.BROKEN -> R.string.difficulty_setting_state_broken
        FuseBoxAtStartOfContract.OFF -> R.string.difficulty_setting_state_off
        FuseBoxAtStartOfContract.ON -> R.string.difficulty_setting_state_on
    }

@StringRes fun FuseBoxVisibleOnMap.toStringResource(): Int =
    when(this) {
        FuseBoxVisibleOnMap.OFF -> R.string.difficulty_setting_state_off
        FuseBoxVisibleOnMap.ON -> R.string.difficulty_setting_state_on
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