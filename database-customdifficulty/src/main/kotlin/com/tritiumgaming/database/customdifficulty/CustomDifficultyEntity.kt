package com.tritiumgaming.database.customdifficulty

import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "custom_difficulties")
data class CustomDifficultyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val startingSanity: StartingSanity,
    val sanityPillRestoration: SanityPillRestoration,
    val sanityDrainSpeed: SanityDrainSpeed,
    val sprinting: Sprinting,
    val playerSpeed: PlayerSpeed,
    val flashlights: Flashlights,
    val loseItemsAndConsumables: LoseItemsAndConsumables,
    val ghostSpeed: GhostSpeed,
    val roamingFrequency: RoamingFrequency,
    val changingFavouriteRoom: ChangingFavoriteRoom,
    val activityLevel: ActivityLevel,
    val eventFrequency: EventFrequency,
    val friendlyGhost: FriendlyGhost,
    val gracePeriod: GracePeriod,
    val huntDuration: HuntDuration,
    val killsExtendHunts: KillsExtendHunts,
    val evidenceGiven: EvidenceGiven,
    val fingerprintChance: FingerprintChance,
    val fingerprintDuration: FingerprintDuration,
    val setupTime: SetupTime,
    val weather: Weather,
    val doorsStartingOpen: DoorsStartingOpen,
    val numberOfHidingPlaces: NumberOfHidingPlaces,
    val sanityMonitor: SanityMonitor,
    val activityMonitor: ActivityMonitor,
    val fuseBoxAtStartOfContract: FuseBoxAtStartOfContract,
    val fuseBoxVisibleOnMap: FuseBoxVisibleOnMap,
    val cursedPossessionsQuantity: CursedPossessionsQuantity,
    val cursedPossessions: List<CursedPossession>
) {
    companion object {
        fun createDefault(id: Int = 0) = CustomDifficultyEntity(
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
