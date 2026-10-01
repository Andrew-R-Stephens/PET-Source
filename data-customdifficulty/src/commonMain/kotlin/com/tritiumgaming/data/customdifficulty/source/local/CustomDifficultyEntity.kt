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

expect class CustomDifficultyEntity(
    id: Int = 0,
    name: String? = null,
    startingSanity: StartingSanity,
    sanityPillRestoration: SanityPillRestoration,
    sanityDrainSpeed: SanityDrainSpeed,
    sprinting: Sprinting,
    playerSpeed: PlayerSpeed,
    flashlights: Flashlights,
    loseItemsAndConsumables: LoseItemsAndConsumables,
    ghostSpeed: GhostSpeed,
    roamingFrequency: RoamingFrequency,
    changingFavouriteRoom: ChangingFavoriteRoom,
    activityLevel: ActivityLevel,
    eventFrequency: EventFrequency,
    friendlyGhost: FriendlyGhost,
    gracePeriod: GracePeriod,
    huntDuration: HuntDuration,
    killsExtendHunts: KillsExtendHunts,
    evidenceGiven: EvidenceGiven,
    fingerprintChance: FingerprintChance,
    fingerprintDuration: FingerprintDuration,
    setupTime: SetupTime,
    weather: Weather,
    doorsStartingOpen: DoorsStartingOpen,
    numberOfHidingPlaces: NumberOfHidingPlaces,
    sanityMonitor: SanityMonitor,
    activityMonitor: ActivityMonitor,
    fuseBoxAtStartOfContract: FuseBoxAtStartOfContract,
    fuseBoxVisibleOnMap: FuseBoxVisibleOnMap,
    cursedPossessionsQuantity: CursedPossessionsQuantity,
    cursedPossessions: List<CursedPossession>
) {
    val id: Int
    val name: String?
    val startingSanity: StartingSanity
    val sanityPillRestoration: SanityPillRestoration
    val sanityDrainSpeed: SanityDrainSpeed
    val sprinting: Sprinting
    val playerSpeed: PlayerSpeed
    val flashlights: Flashlights
    val loseItemsAndConsumables: LoseItemsAndConsumables
    val ghostSpeed: GhostSpeed
    val roamingFrequency: RoamingFrequency
    val changingFavouriteRoom: ChangingFavoriteRoom
    val activityLevel: ActivityLevel
    val eventFrequency: EventFrequency
    val friendlyGhost: FriendlyGhost
    val gracePeriod: GracePeriod
    val huntDuration: HuntDuration
    val killsExtendHunts: KillsExtendHunts
    val evidenceGiven: EvidenceGiven
    val fingerprintChance: FingerprintChance
    val fingerprintDuration: FingerprintDuration
    val setupTime: SetupTime
    val weather: Weather
    val doorsStartingOpen: DoorsStartingOpen
    val numberOfHidingPlaces: NumberOfHidingPlaces
    val sanityMonitor: SanityMonitor
    val activityMonitor: ActivityMonitor
    val fuseBoxAtStartOfContract: FuseBoxAtStartOfContract
    val fuseBoxVisibleOnMap: FuseBoxVisibleOnMap
    val cursedPossessionsQuantity: CursedPossessionsQuantity
    val cursedPossessions: List<CursedPossession>

    companion object {
        fun createDefault(id: Int = 0): CustomDifficultyEntity
    }
}
