package com.tritiumgaming.data.customdifficulty.source.local

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

actual data class CustomDifficultyEntity actual constructor(
    actual val id: Int,
    actual val name: String?,
    actual val startingSanity: StartingSanity,
    actual val sanityPillRestoration: SanityPillRestoration,
    actual val sanityDrainSpeed: SanityDrainSpeed,
    actual val sprinting: Sprinting,
    actual val playerSpeed: PlayerSpeed,
    actual val flashlights: Flashlights,
    actual val loseItemsAndConsumables: LoseItemsAndConsumables,
    actual val ghostSpeed: GhostSpeed,
    actual val roamingFrequency: RoamingFrequency,
    actual val changingFavouriteRoom: ChangingFavoriteRoom,
    actual val activityLevel: ActivityLevel,
    actual val eventFrequency: EventFrequency,
    actual val friendlyGhost: FriendlyGhost,
    actual val gracePeriod: GracePeriod,
    actual val huntDuration: HuntDuration,
    actual val killsExtendHunts: KillsExtendHunts,
    actual val evidenceGiven: EvidenceGiven,
    actual val fingerprintChance: FingerprintChance,
    actual val fingerprintDuration: FingerprintDuration,
    actual val setupTime: SetupTime,
    actual val weather: Weather,
    actual val doorsStartingOpen: DoorsStartingOpen,
    actual val numberOfHidingPlaces: NumberOfHidingPlaces,
    actual val sanityMonitor: SanityMonitor,
    actual val activityMonitor: ActivityMonitor,
    actual val fuseBoxAtStartOfContract: FuseBoxAtStartOfContract,
    actual val fuseBoxVisibleOnMap: FuseBoxVisibleOnMap,
    actual val cursedPossessionsQuantity: CursedPossessionsQuantity,
    actual val cursedPossessions: List<CursedPossession>
) {
    actual companion object {
        actual fun createDefault(id: Int) = CustomDifficultyEntity(
            id = id,
            name = null,
            startingSanity = StartingSanity.SANITY_100,
            sanityPillRestoration = SanityPillRestoration.RESTORE_40,
            sanityDrainSpeed = SanityDrainSpeed.SPEED_100,
            sprinting = Sprinting.ON,
            playerSpeed = PlayerSpeed.SPEED_100,
            flashlights = Flashlights.ON,
            loseItemsAndConsumables = LoseItemsAndConsumables.ON,
            ghostSpeed = GhostSpeed.SPEED_100,
            roamingFrequency = RoamingFrequency.MEDIUM,
            changingFavouriteRoom = ChangingFavoriteRoom.NONE,
            activityLevel = ActivityLevel.HIGH,
            eventFrequency = EventFrequency.LOW,
            friendlyGhost = FriendlyGhost.OFF,
            gracePeriod = GracePeriod.PERIOD_5,
            huntDuration = HuntDuration.LOW,
            killsExtendHunts = KillsExtendHunts.OFF,
            evidenceGiven = EvidenceGiven.COUNT_3,
            fingerprintChance = FingerprintChance.CHANCE_100,
            fingerprintDuration = FingerprintDuration.DURATION_120,
            setupTime = SetupTime.TIME_300,
            weather = Weather.RANDOM,
            doorsStartingOpen = DoorsStartingOpen.NONE,
            numberOfHidingPlaces = NumberOfHidingPlaces.VERY_HIGH,
            sanityMonitor = SanityMonitor.ON,
            activityMonitor = ActivityMonitor.ON,
            fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.ON,
            fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.ON,
            cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
            cursedPossessions = List(7) { CursedPossession.RANDOM }
        )
    }
}
