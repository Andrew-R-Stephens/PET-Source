package com.tritiumgaming.feature.customdifficulty.app.mappers

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.*

@StringRes
fun StartingSanity.toValueStringResource(): Int = when (this) {
    StartingSanity.SANITY_0 -> R.string.difficulty_setting_value_0_percent
    StartingSanity.SANITY_25 -> R.string.difficulty_setting_value_25_percent
    StartingSanity.SANITY_50 -> R.string.difficulty_setting_value_50_percent
    StartingSanity.SANITY_75 -> R.string.difficulty_setting_value_75_percent
    StartingSanity.SANITY_100 -> R.string.difficulty_setting_value_100_percent
}

@StringRes
fun SanityPillRestoration.toValueStringResource(): Int = when (this) {
    SanityPillRestoration.RESTORE_0 -> R.string.difficulty_setting_value_0_percent
    SanityPillRestoration.RESTORE_5 -> R.string.difficulty_setting_value_5_percent
    SanityPillRestoration.RESTORE_10 -> R.string.difficulty_setting_value_10_percent
    SanityPillRestoration.RESTORE_20 -> R.string.difficulty_setting_value_20_percent
    SanityPillRestoration.RESTORE_25 -> R.string.difficulty_setting_value_25_percent
    SanityPillRestoration.RESTORE_30 -> R.string.difficulty_setting_value_30_percent
    SanityPillRestoration.RESTORE_35 -> R.string.difficulty_setting_value_35_percent
    SanityPillRestoration.RESTORE_40 -> R.string.difficulty_setting_value_40_percent
    SanityPillRestoration.RESTORE_45 -> R.string.difficulty_setting_value_45_percent
    SanityPillRestoration.RESTORE_50 -> R.string.difficulty_setting_value_50_percent
    SanityPillRestoration.RESTORE_75 -> R.string.difficulty_setting_value_75_percent
    SanityPillRestoration.RESTORE_100 -> R.string.difficulty_setting_value_100_percent
}

@StringRes
fun SanityDrainSpeed.toValueStringResource(): Int = when (this) {
    SanityDrainSpeed.SPEED_0 -> R.string.difficulty_setting_value_0_percent
    SanityDrainSpeed.SPEED_50 -> R.string.difficulty_setting_value_50_percent
    SanityDrainSpeed.SPEED_100 -> R.string.difficulty_setting_value_100_percent
    SanityDrainSpeed.SPEED_150 -> R.string.difficulty_setting_value_150_percent
    SanityDrainSpeed.SPEED_200 -> R.string.difficulty_setting_value_200_percent
}

@StringRes
fun PlayerSpeed.toValueStringResource(): Int = when (this) {
    PlayerSpeed.SPEED_50 -> R.string.difficulty_setting_value_50_percent
    PlayerSpeed.SPEED_75 -> R.string.difficulty_setting_value_75_percent
    PlayerSpeed.SPEED_100 -> R.string.difficulty_setting_value_100_percent
    PlayerSpeed.SPEED_125 -> R.string.difficulty_setting_value_125_percent
    PlayerSpeed.SPEED_150 -> R.string.difficulty_setting_value_150_percent
}

@StringRes
fun GhostSpeed.toValueStringResource(): Int = when (this) {
    GhostSpeed.SPEED_50 -> R.string.difficulty_setting_value_50_percent
    GhostSpeed.SPEED_75 -> R.string.difficulty_setting_value_75_percent
    GhostSpeed.SPEED_100 -> R.string.difficulty_setting_value_100_percent
    GhostSpeed.SPEED_125 -> R.string.difficulty_setting_value_125_percent
    GhostSpeed.SPEED_150 -> R.string.difficulty_setting_value_150_percent
}

@StringRes
fun GracePeriod.toValueStringResource(): Int = when (this) {
    GracePeriod.PERIOD_0 -> R.string.difficulty_setting_value_0_seconds
    GracePeriod.PERIOD_1 -> R.string.difficulty_setting_value_1_second
    GracePeriod.PERIOD_2 -> R.string.difficulty_setting_value_2_seconds
    GracePeriod.PERIOD_3 -> R.string.difficulty_setting_value_3_seconds
    GracePeriod.PERIOD_4 -> R.string.difficulty_setting_value_4_seconds
    GracePeriod.PERIOD_5 -> R.string.difficulty_setting_value_5_seconds
}

@StringRes
fun EvidenceGiven.toValueStringResource(): Int = when (this) {
    EvidenceGiven.COUNT_0 -> R.string.difficulty_setting_value_0
    EvidenceGiven.COUNT_1 -> R.string.difficulty_setting_value_1
    EvidenceGiven.COUNT_2 -> R.string.difficulty_setting_value_2
    EvidenceGiven.COUNT_3 -> R.string.difficulty_setting_value_3
}

@StringRes
fun FingerprintChance.toValueStringResource(): Int = when (this) {
    FingerprintChance.CHANCE_0 -> R.string.difficulty_setting_value_0_percent
    FingerprintChance.CHANCE_25 -> R.string.difficulty_setting_value_25_percent
    FingerprintChance.CHANCE_50 -> R.string.difficulty_setting_value_50_percent
    FingerprintChance.CHANCE_75 -> R.string.difficulty_setting_value_75_percent
    FingerprintChance.CHANCE_100 -> R.string.difficulty_setting_value_100_percent
}

@StringRes
fun FingerprintDuration.toValueStringResource(): Int = when (this) {
    FingerprintDuration.DURATION_NEVER -> R.string.difficulty_setting_value_0_seconds
    FingerprintDuration.DURATION_15 -> R.string.difficulty_setting_value_15_seconds
    FingerprintDuration.DURATION_30 -> R.string.difficulty_setting_value_30_seconds
    FingerprintDuration.DURATION_60 -> R.string.difficulty_setting_value_60_seconds
    FingerprintDuration.DURATION_90 -> R.string.difficulty_setting_value_90_seconds
    FingerprintDuration.DURATION_120 -> R.string.difficulty_setting_value_120_seconds
    FingerprintDuration.DURATION_180 -> R.string.difficulty_setting_value_180_seconds
    FingerprintDuration.DURATION_INFINITE -> R.string.difficulty_setting_state_infinite
}

@StringRes
fun SetupTime.toValueStringResource(): Int = when (this) {
    SetupTime.TIME_0 -> R.string.difficulty_setting_value_0_seconds
    SetupTime.TIME_30 -> R.string.difficulty_setting_value_30_seconds
    SetupTime.TIME_60 -> R.string.difficulty_setting_value_60_seconds
    SetupTime.TIME_120 -> R.string.difficulty_setting_value_120_seconds
    SetupTime.TIME_180 -> R.string.difficulty_setting_value_180_seconds
    SetupTime.TIME_240 -> R.string.difficulty_setting_value_240_seconds
    SetupTime.TIME_300 -> R.string.difficulty_setting_value_300_seconds
}

@StringRes
fun CursedPossessionsQuantity.toValueStringResource(): Int = when (this) {
    CursedPossessionsQuantity.NONE -> R.string.difficulty_setting_value_0
    CursedPossessionsQuantity.QUANTITY_1 -> R.string.difficulty_setting_value_1
    CursedPossessionsQuantity.QUANTITY_2 -> R.string.difficulty_setting_value_2
    CursedPossessionsQuantity.QUANTITY_3 -> R.string.difficulty_setting_value_3
    CursedPossessionsQuantity.QUANTITY_4 -> R.string.difficulty_setting_value_4
    CursedPossessionsQuantity.QUANTITY_5 -> R.string.difficulty_setting_value_5
    CursedPossessionsQuantity.QUANTITY_6 -> R.string.difficulty_setting_value_6
    CursedPossessionsQuantity.QUANTITY_7 -> R.string.difficulty_setting_value_7
}

@StringRes
fun Sprinting.toValueStringResource(): Int = when (this) {
    Sprinting.OFF -> R.string.difficulty_setting_state_off
    Sprinting.ON -> R.string.difficulty_setting_state_on
    Sprinting.SPRINT_INFINITE -> R.string.difficulty_setting_state_infinite
}

@StringRes
fun Flashlights.toValueStringResource(): Int = when (this) {
    Flashlights.OFF -> R.string.difficulty_setting_state_off
    Flashlights.ON -> R.string.difficulty_setting_state_on
}

@StringRes
fun LoseItemsAndConsumables.toValueStringResource(): Int = when (this) {
    LoseItemsAndConsumables.OFF -> R.string.difficulty_setting_state_off
    LoseItemsAndConsumables.ON -> R.string.difficulty_setting_state_on
}

@StringRes
fun RoamingFrequency.toValueStringResource(): Int = when (this) {
    RoamingFrequency.LOW -> R.string.difficulty_setting_state_low
    RoamingFrequency.MEDIUM -> R.string.difficulty_setting_state_medium
    RoamingFrequency.HIGH -> R.string.difficulty_setting_state_high
}

@StringRes
fun ChangingFavoriteRoom.toValueStringResource(): Int = when (this) {
    ChangingFavoriteRoom.NONE -> R.string.difficulty_setting_state_none
    ChangingFavoriteRoom.LOW -> R.string.difficulty_setting_state_low
    ChangingFavoriteRoom.MEDIUM -> R.string.difficulty_setting_state_medium
    ChangingFavoriteRoom.HIGH -> R.string.difficulty_setting_state_high
    ChangingFavoriteRoom.VERY_HIGH -> R.string.difficulty_setting_state_veryHigh
}

@StringRes
fun ActivityLevel.toValueStringResource(): Int = when (this) {
    ActivityLevel.LOW -> R.string.difficulty_setting_state_low
    ActivityLevel.MEDIUM -> R.string.difficulty_setting_state_medium
    ActivityLevel.HIGH -> R.string.difficulty_setting_state_high
}

@StringRes
fun EventFrequency.toValueStringResource(): Int = when (this) {
    EventFrequency.LOW -> R.string.difficulty_setting_state_low
    EventFrequency.MEDIUM -> R.string.difficulty_setting_state_medium
    EventFrequency.HIGH -> R.string.difficulty_setting_state_high
}

@StringRes
fun FriendlyGhost.toValueStringResource(): Int = when (this) {
    FriendlyGhost.OFF -> R.string.difficulty_setting_state_off
    FriendlyGhost.ON -> R.string.difficulty_setting_state_on
}

@StringRes
fun HuntDuration.toValueStringResource(): Int = when (this) {
    HuntDuration.LOW -> R.string.difficulty_setting_state_low
    HuntDuration.MEDIUM -> R.string.difficulty_setting_state_medium
    HuntDuration.HIGH -> R.string.difficulty_setting_state_high
}

@StringRes
fun KillsExtendHunts.toValueStringResource(): Int = when (this) {
    KillsExtendHunts.OFF -> R.string.difficulty_setting_state_off
    KillsExtendHunts.LOW -> R.string.difficulty_setting_state_low
    KillsExtendHunts.MEDIUM -> R.string.difficulty_setting_state_medium
    KillsExtendHunts.HIGH -> R.string.difficulty_setting_state_high
}

@StringRes
fun Weather.toValueStringResource(): Int = when (this) {
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

@StringRes
fun DoorsStartingOpen.toValueStringResource(): Int = when (this) {
    DoorsStartingOpen.NONE -> R.string.difficulty_setting_state_none
    DoorsStartingOpen.LOW -> R.string.difficulty_setting_state_low
    DoorsStartingOpen.MEDIUM -> R.string.difficulty_setting_state_medium
    DoorsStartingOpen.HIGH -> R.string.difficulty_setting_state_high
}

@StringRes
fun NumberOfHidingPlaces.toValueStringResource(): Int = when (this) {
    NumberOfHidingPlaces.NONE -> R.string.difficulty_setting_state_none
    NumberOfHidingPlaces.LOW -> R.string.difficulty_setting_state_low
    NumberOfHidingPlaces.MEDIUM -> R.string.difficulty_setting_state_medium
    NumberOfHidingPlaces.HIGH -> R.string.difficulty_setting_state_high
    NumberOfHidingPlaces.VERY_HIGH -> R.string.difficulty_setting_state_veryHigh
}

@StringRes
fun SanityMonitor.toValueStringResource(): Int = when (this) {
    SanityMonitor.OFF -> R.string.difficulty_setting_state_off
    SanityMonitor.ON -> R.string.difficulty_setting_state_on
}

@StringRes
fun ActivityMonitor.toValueStringResource(): Int = when (this) {
    ActivityMonitor.OFF -> R.string.difficulty_setting_state_off
    ActivityMonitor.ON -> R.string.difficulty_setting_state_on
}

@StringRes
fun FuseBoxAtStartOfContract.toValueStringResource(): Int = when (this) {
    FuseBoxAtStartOfContract.BROKEN -> R.string.difficulty_setting_state_broken
    FuseBoxAtStartOfContract.OFF -> R.string.difficulty_setting_state_off
    FuseBoxAtStartOfContract.ON -> R.string.difficulty_setting_state_on
}

@StringRes
fun FuseBoxVisibleOnMap.toValueStringResource(): Int = when (this) {
    FuseBoxVisibleOnMap.OFF -> R.string.difficulty_setting_state_off
    FuseBoxVisibleOnMap.ON -> R.string.difficulty_setting_state_on
}
