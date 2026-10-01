package com.tritiumstudios.data.difficultysetting.dto

import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.ActivityLevel
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.ActivityMonitor
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.ChangingFavoriteRoom
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.CursedPossession
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.CursedPossessionsQuantity
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.DoorsStartingOpen
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.EventFrequency
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.EvidenceGiven
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.FingerprintChance
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.FingerprintDuration
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Flashlights
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.FriendlyGhost
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.FuseBoxAtStartOfContract
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.FuseBoxVisibleOnMap
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.GhostSpeed
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.GracePeriod
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.HuntDuration
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.KillsExtendHunts
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.LoseItemsAndConsumables
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.NumberOfHidingPlaces
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.PlayerSpeed
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.RoamingFrequency
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.SanityDrainSpeed
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.SanityMonitor
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.SanityPillRestoration
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.SetupTime
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Sprinting
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.StartingSanity
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Weather
import com.tritiumstudios.data.difficultysetting.model.DifficultySettingsModel

data class DifficultySettingsModelDto(
    val startingSanity: StartingSanity = StartingSanity.SANITY_100,
    val sanityPillRestoration: SanityPillRestoration = SanityPillRestoration.RESTORE_30,
    val sanityDrainSpeed: SanityDrainSpeed = SanityDrainSpeed.SPEED_200,
    val sprinting: Sprinting = Sprinting.ON,
    val playerSpeed: PlayerSpeed = PlayerSpeed.SPEED_100,
    val flashlights: Flashlights = Flashlights.ON,
    val loseItemsAndConsumables: LoseItemsAndConsumables = LoseItemsAndConsumables.ON,
    val ghostSpeed: GhostSpeed = GhostSpeed.SPEED_100,
    val roamingFrequency: RoamingFrequency = RoamingFrequency.HIGH,
    val changingFavouriteRoom: ChangingFavoriteRoom = ChangingFavoriteRoom.LOW,
    val activityLevel: ActivityLevel = ActivityLevel.LOW,
    val eventFrequency: EventFrequency = EventFrequency.MEDIUM,
    val friendlyGhost: FriendlyGhost = FriendlyGhost.OFF,
    val gracePeriod: GracePeriod = GracePeriod.PERIOD_3,
    val huntDuration: HuntDuration = HuntDuration.HIGH,
    val killsExtendHunts: KillsExtendHunts = KillsExtendHunts.OFF,
    val evidenceGiven: EvidenceGiven = EvidenceGiven.COUNT_3,
    val fingerprintChance: FingerprintChance = FingerprintChance.CHANCE_100,
    val fingerprintDuration: FingerprintDuration = FingerprintDuration.DURATION_120,
    val setupTime: SetupTime = SetupTime.TIME_0,
    val weather: Weather = Weather.RANDOM,
    val doorsStartingOpen: DoorsStartingOpen = DoorsStartingOpen.MEDIUM,
    val numberOfHidingPlaces: NumberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
    val sanityMonitor: SanityMonitor = SanityMonitor.ON,
    val activityMonitor: ActivityMonitor = ActivityMonitor.ON,
    val fuseBoxAtStartOfContract: FuseBoxAtStartOfContract = FuseBoxAtStartOfContract.OFF,
    val fuseBoxVisibleOnMap: FuseBoxVisibleOnMap = FuseBoxVisibleOnMap.ON,
    val cursedPossessionsQuantity: CursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
    val cursedPossessions: List<CursedPossession> = listOf(CursedPossession.RANDOM),
    val equipmentPermission: List<EquipmentPermission> = emptyList()
)

fun DifficultySettingsModelDto.toDomain() = DifficultySettingsModel(
    startingSanity = startingSanity,
    sanityPillRestoration = sanityPillRestoration,
    sanityDrainSpeed = sanityDrainSpeed,
    sprinting = sprinting,
    playerSpeed = playerSpeed,
    flashlights = flashlights,
    loseItemsAndConsumables = loseItemsAndConsumables,
    ghostSpeed = ghostSpeed,
    roamingFrequency = roamingFrequency,
    changingFavouriteRoom = changingFavouriteRoom,
    activityLevel = activityLevel,
    eventFrequency = eventFrequency,
    friendlyGhost = friendlyGhost,
    gracePeriod = gracePeriod,
    huntDuration = huntDuration,
    killsExtendHunts = killsExtendHunts,
    evidenceGiven = evidenceGiven,
    fingerprintChance = fingerprintChance,
    fingerprintDuration = fingerprintDuration,
    setupTime = setupTime,
    weather = weather,
    doorsStartingOpen = doorsStartingOpen,
    numberOfHidingPlaces = numberOfHidingPlaces,
    sanityMonitor = sanityMonitor,
    activityMonitor = activityMonitor,
    fuseBoxAtStartOfContract = fuseBoxAtStartOfContract,
    fuseBoxVisibleOnMap = fuseBoxVisibleOnMap,
    cursedPossessionsQuantity = cursedPossessionsQuantity,
    cursedPossessions = cursedPossessions,
    equipmentPermission = equipmentPermission
)
