package com.tritiumgaming.shared.data.difficultysetting.mapper

class DifficultySettingResources {

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
        SUNRISE
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
    enum class FuzeBoxAtStartOfContract {
        BROKEN,
        OFF,
        ON
    }
    enum class FuzeBoxVisibleOnMap {
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