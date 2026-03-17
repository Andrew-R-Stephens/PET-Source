package com.tritiumgaming.shared.data.difficultysetting.dto

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
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel

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
