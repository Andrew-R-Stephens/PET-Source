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