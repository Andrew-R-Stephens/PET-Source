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

data class DifficultySettingsResourceModelDto(
    val startingSanity: StartingSanity = StartingSanity.SANITY_100,
    val sanityPillRestoration: SanityPillRestoration = SanityPillRestoration.RESTORE_40,
    val sanityDrainSpeed: SanityDrainSpeed = SanityDrainSpeed.SPEED_100,
    val sprinting: Sprinting = Sprinting.ON,
    val playerSpeed: PlayerSpeed = PlayerSpeed.SPEED_100,
    val flashlights: Flashlights = Flashlights.ON,
    val loseItemsAndConsumables: LoseItemsAndConsumables = LoseItemsAndConsumables.ON,
    val ghostSpeed: GhostSpeed = GhostSpeed.SPEED_100,
    val roamingFrequency: RoamingFrequency = RoamingFrequency.MEDIUM,
    val changingFavouriteRoom: ChangingFavoriteRoom = ChangingFavoriteRoom.NONE,
    val activityLevel: ActivityLevel = ActivityLevel.HIGH,
    val eventFrequency: EventFrequency = EventFrequency.LOW,
    val friendlyGhost: FriendlyGhost = FriendlyGhost.OFF,
    val gracePeriod: GracePeriod = GracePeriod.PERIOD_5,
    val huntDuration: HuntDuration = HuntDuration.LOW,
    val killsExtendHunts: KillsExtendHunts = KillsExtendHunts.OFF,
    val evidenceGiven: EvidenceGiven = EvidenceGiven.COUNT_3,
    val fingerprintChance: FingerprintChance = FingerprintChance.CHANCE_100,
    val fingerprintDuration: FingerprintDuration = FingerprintDuration.DURATION_120,
    val setupTime: SetupTime = SetupTime.TIME_300,
    val weather: Weather = Weather.RANDOM,
    val doorsStartingOpen: DoorsStartingOpen = DoorsStartingOpen.NONE,
    val numberOfHidingPlaces: NumberOfHidingPlaces = NumberOfHidingPlaces.VERY_HIGH,
    val sanityMonitor: SanityMonitor = SanityMonitor.ON,
    val activityMonitor: ActivityMonitor = ActivityMonitor.ON,
    val fuseBoxAtStartOfContract: FuseBoxAtStartOfContract = FuseBoxAtStartOfContract.ON,
    val fuseBoxVisibleOnMap: FuseBoxVisibleOnMap = FuseBoxVisibleOnMap.ON,
    val cursedPossessionsQuantity: CursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
    val cursedPossessions: List<CursedPossession> = listOf(
        CursedPossession.RANDOM,
        CursedPossession.RANDOM,
        CursedPossession.RANDOM,
        CursedPossession.RANDOM,
        CursedPossession.RANDOM,
        CursedPossession.RANDOM,
        CursedPossession.RANDOM,
    )

)