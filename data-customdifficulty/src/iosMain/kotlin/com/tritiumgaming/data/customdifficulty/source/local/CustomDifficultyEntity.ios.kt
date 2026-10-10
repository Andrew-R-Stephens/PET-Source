package com.tritiumgaming.data.customdifficulty.source.local

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
