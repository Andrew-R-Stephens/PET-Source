package com.tritiumgaming.shared.data.difficultysetting.mapper

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.EvidenceGiven
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FingerprintChance
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FingerprintDuration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.GhostSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.GracePeriod
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.HuntDuration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.KillsExtendHunts
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.PlayerSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SanityDrainSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SanityPillRestoration
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SetupTime
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.StartingSanity
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.data.weather.model.TemperatureRange
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class DifficultySettingResources {

    enum class DifficultySetting {
        STARTING_SANITY,
        SANITY_PILL_RESTORATION,
        SANITY_DRAIN_SPEED,
        SPRINTING,
        PLAYER_SPEED,
        FLASHLIGHTS,
        LOSE_ITEMS_AND_CONSUMABLES,
        GHOST_SPEED,
        ROAMING_FREQUENCY,
        CHANGING_FAVOURITE_ROOM,
        ACTIVITY_LEVEL,
        EVENT_FREQUENCY,
        FRIENDLY_GHOST,
        GRACE_PERIOD,
        HUNT_DURATION,
        KILLS_EXTEND_HUNTS,
        EVIDENCE_GIVEN,
        FINGERPRINT_CHANCE,
        FINGERPRINT_DURATION,
        SETUP_TIME,
        WEATHER,
        DOORS_STARTING_OPEN,
        NUMBER_OF_HIDING_PLACES,
        SANITY_MONITOR,
        ACTIVITY_MONITOR,
        FUSE_BOX_AT_START_OF_CONTRACT,
        FUSE_BOX_VISIBLE_ON_MAP,
        CURSED_POSSESSIONS_QUANTITY,
        CURSED_POSSESSIONS
    }

    enum class StartingSanity {
        SANITY_0,
        SANITY_25,
        SANITY_50,
        SANITY_75,
        SANITY_100
    }
    enum class SanityPillRestoration {
        RESTORE_0,
        RESTORE_5,
        RESTORE_10,
        RESTORE_20,
        RESTORE_25,
        RESTORE_30,
        RESTORE_35,
        RESTORE_40,
        RESTORE_45,
        RESTORE_50,
        RESTORE_75,
        RESTORE_100
    }
    enum class SanityDrainSpeed {
        SPEED_0,
        SPEED_50,
        SPEED_100,
        SPEED_150,
        SPEED_200
    }
    enum class Sprinting {
        OFF,
        ON,
        SPRINT_INFINITE
    }
    enum class PlayerSpeed {
        SPEED_50,
        SPEED_75,
        SPEED_100,
        SPEED_125,
        SPEED_150
    }
    enum class Flashlights {
        OFF,
        ON
    }
    enum class LoseItemsAndConsumables {
        OFF,
        ON
    }
    enum class GhostSpeed {
        SPEED_50,
        SPEED_75,
        SPEED_100,
        SPEED_125,
        SPEED_150
    }
    enum class RoamingFrequency {
        LOW,
        MEDIUM,
        HIGH
    }
    enum class ChangingFavoriteRoom {
        NONE,
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH
    }
    enum class ActivityLevel {
        LOW,
        MEDIUM,
        HIGH
    }
    enum class EventFrequency {
        LOW,
        MEDIUM,
        HIGH
    }
    enum class FriendlyGhost {
        OFF,
        ON
    }
    enum class GracePeriod {
        PERIOD_0,
        PERIOD_1,
        PERIOD_2,
        PERIOD_3,
        PERIOD_4,
        PERIOD_5,
    }
    enum class HuntDuration {
        LOW,
        MEDIUM,
        HIGH
    }
    enum class KillsExtendHunts {
        OFF,
        LOW,
        MEDIUM,
        HIGH
    }
    enum class EvidenceGiven {
        COUNT_0,
        COUNT_1,
        COUNT_2,
        COUNT_3
    }
    enum class FingerprintChance {
        CHANCE_0,
        CHANCE_25,
        CHANCE_50,
        CHANCE_75,
        CHANCE_100
    }
    enum class FingerprintDuration {
        DURATION_NEVER,
        DURATION_15,
        DURATION_30,
        DURATION_60,
        DURATION_90,
        DURATION_120,
        DURATION_180,
        DURATION_INFINITE
    }
    enum class SetupTime {
        TIME_0,
        TIME_30,
        TIME_60,
        TIME_120,
        TIME_180,
        TIME_240,
        TIME_300
    }
    enum class Weather {
        RANDOM,
        LIGHT_RAIN,
        HEAVY_RAIN,
        SNOW,
        WINDY,
        CLEAR,
        FOG,
        SUNRISE,
        BLOOD_MOON
    }
    enum class DoorsStartingOpen {
        NONE,
        LOW,
        MEDIUM,
        HIGH
    }
    enum class NumberOfHidingPlaces {
        NONE,
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH
    }
    enum class SanityMonitor {
        OFF,
        ON
    }
    enum class ActivityMonitor {
        OFF,
        ON
    }
    enum class FuseBoxAtStartOfContract {
        BROKEN,
        OFF,
        ON
    }
    enum class FuseBoxVisibleOnMap {
        OFF,
        ON
    }
    enum class CursedPossessionsQuantity {
        NONE,
        QUANTITY_1,
        QUANTITY_2,
        QUANTITY_3,
        QUANTITY_4,
        QUANTITY_5,
        QUANTITY_6,
        QUANTITY_7
    }
    enum class CursedPossession {
        RANDOM,
        TAROT_CARDS,
        OUIJA_BOARD,
        HAUNTED_MIRROR,
        MUSIC_BOX,
        SUMMONING_CIRCLE,
        VOODOO_DOLL,
        MONKEY_PAW
    }
}

fun Weather.toTemperatureRange(): TemperatureRange =
    when(this) {
        Weather.SUNRISE -> TemperatureRange(low = 10f, high = 16f)
        Weather.CLEAR -> TemperatureRange(low = 8f, high = 13f)
        Weather.FOG -> TemperatureRange(low = 8f, high = 13f)
        Weather.BLOOD_MOON -> TemperatureRange(low = 8f, high = 13f)
        Weather.WINDY -> TemperatureRange(low = 5f, high = 8f)
        Weather.LIGHT_RAIN -> TemperatureRange(low = 5f, high = 8f)
        Weather.HEAVY_RAIN -> TemperatureRange(low = 5f, high = 8f)
        Weather.SNOW -> TemperatureRange(low = 3f, high = 5f)
        else -> TemperatureRange(low = -1f, high = -1f)
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

fun PlayerSpeed.toFloat(): Float =
    when(this) {
        PlayerSpeed.SPEED_50 -> .5f
        PlayerSpeed.SPEED_75 -> .75f
        PlayerSpeed.SPEED_100 -> 1f
        PlayerSpeed.SPEED_125 -> 1.25f
        PlayerSpeed.SPEED_150 -> 1.5f
    }

fun GhostSpeed.toFloat(): Float =
    when(this) {
        GhostSpeed.SPEED_50 -> .5f
        GhostSpeed.SPEED_75 -> .75f
        GhostSpeed.SPEED_100 -> 1f
        GhostSpeed.SPEED_125 -> 1.25f
        GhostSpeed.SPEED_150 -> 1.5f
    }

fun GracePeriod.toLong(): Long =
    when(this) {
        GracePeriod.PERIOD_0 -> 0L
        GracePeriod.PERIOD_1 -> 1.seconds.inWholeMilliseconds
        GracePeriod.PERIOD_2 -> 2.seconds.inWholeMilliseconds
        GracePeriod.PERIOD_3 -> 3.seconds.inWholeMilliseconds
        GracePeriod.PERIOD_4 -> 4.seconds.inWholeMilliseconds
        GracePeriod.PERIOD_5 -> 5.seconds.inWholeMilliseconds
    }

fun HuntDuration.toLong(size: MapModifierResources.MapSize): Long =
    when(this) {
        HuntDuration.LOW -> when(size) {
            MapModifierResources.MapSize.SMALL -> 15.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.MEDIUM -> 30.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.LARGE -> 40.seconds.inWholeMilliseconds
        }
        HuntDuration.MEDIUM -> when(size) {
            MapModifierResources.MapSize.SMALL -> 20.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.MEDIUM -> 40.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.LARGE -> 50.seconds.inWholeMilliseconds
        }
        HuntDuration.HIGH -> when(size) {
            MapModifierResources.MapSize.SMALL -> 30.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.MEDIUM -> 50.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.LARGE -> 1.minutes.inWholeMilliseconds
        }
    }

fun KillsExtendHunts.toLong(size: MapModifierResources.MapSize): Long =
    when(this) {
        KillsExtendHunts.OFF -> 0L
        KillsExtendHunts.LOW -> when(size) {
            MapModifierResources.MapSize.SMALL -> 15.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.MEDIUM -> 20.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.LARGE -> 25.seconds.inWholeMilliseconds
        }
        KillsExtendHunts.MEDIUM -> when(size) {
            MapModifierResources.MapSize.SMALL -> 25.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.MEDIUM -> 30.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.LARGE -> 35.seconds.inWholeMilliseconds
        }
        KillsExtendHunts.HIGH -> when(size) {
            MapModifierResources.MapSize.SMALL -> 35.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.MEDIUM -> 40.seconds.inWholeMilliseconds
            MapModifierResources.MapSize.LARGE -> 45.seconds.inWholeMilliseconds
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
        FingerprintDuration.DURATION_15 -> 15.seconds.inWholeMilliseconds
        FingerprintDuration.DURATION_30 -> 30.seconds.inWholeMilliseconds
        FingerprintDuration.DURATION_60 -> 1.minutes.inWholeMilliseconds
        FingerprintDuration.DURATION_90 -> 90.seconds.inWholeMilliseconds
        FingerprintDuration.DURATION_120 -> 2.minutes.inWholeMilliseconds
        FingerprintDuration.DURATION_180 -> 3.minutes.inWholeMilliseconds
        FingerprintDuration.DURATION_INFINITE -> -1L
    }

fun SetupTime.toLong(): Long =
    when(this) {
        SetupTime.TIME_0 -> 0L
        SetupTime.TIME_30 -> 30.seconds.inWholeMilliseconds
        SetupTime.TIME_60 -> 1.minutes.inWholeMilliseconds
        SetupTime.TIME_120 -> 2.minutes.inWholeMilliseconds
        SetupTime.TIME_180 -> 3.minutes.inWholeMilliseconds
        SetupTime.TIME_240 -> 4.minutes.inWholeMilliseconds
        SetupTime.TIME_300 -> 5.minutes.inWholeMilliseconds
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
