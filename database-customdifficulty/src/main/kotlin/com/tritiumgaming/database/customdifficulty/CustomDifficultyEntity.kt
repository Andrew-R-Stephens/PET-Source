package com.tritiumgaming.database.customdifficulty

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.*

@Entity(tableName = "custom_difficulties")
data class CustomDifficultyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
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
)
