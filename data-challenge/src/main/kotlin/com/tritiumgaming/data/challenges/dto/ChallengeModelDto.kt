package com.tritiumgaming.data.challenges.dto

import com.tritiumgaming.data.challenges.dto.ChallengeModelDto.DifficultySettingsModelDto
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.model.ChallengeModel
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
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
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuzeBoxAtStartOfContract
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuzeBoxVisibleOnMap
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
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel.EquipmentPermission

data class ChallengeModelDto(
    val name: ChallengeResources.ChallengeTitle,
    val description: ChallengeResources.ChallengeDescription,
    val responseType: DifficultyResponseType,
    val settingsModelDto: DifficultySettingsModelDto
) {
    data class DifficultySettingsModelDto(
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
        val fuseBoxAtStartOfContract: FuzeBoxAtStartOfContract,
        val fuseBoxVisibleOnMap: FuzeBoxVisibleOnMap,
        val cursedPossessionsQuantity: CursedPossessionsQuantity,
        val cursedPossession1: CursedPossession,
        val cursedPossession2: CursedPossession,
        val cursedPossession3: CursedPossession,
        val cursedPossession4: CursedPossession,
        val cursedPossession5: CursedPossession,
        val cursedPossession6: CursedPossession,
        val cursedPossession7: CursedPossession,
        val equipmentPermissionDto: List<EquipmentPermissionDto>
    )
}

internal fun DifficultySettingsModelDto.toDomain() = DifficultySettingsModel(
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
    cursedPossession1 = cursedPossession1,
    cursedPossession2 = cursedPossession2,
    cursedPossession3 = cursedPossession3,
    cursedPossession4 = cursedPossession4,
    cursedPossession5 = cursedPossession5,
    cursedPossession6 = cursedPossession6,
    cursedPossession7 = cursedPossession7,
    equipmentPermission = equipmentPermissionDto.toDomain()
)

@JvmName("ListEquipmentPermissionDtoToDomain")
internal fun List<EquipmentPermissionDto>.toDomain() = map { dto ->
    dto.toDomain()
}

internal fun EquipmentPermissionDto.toDomain() = EquipmentPermission(
    identifier = identifier,
    quantity = quantity,
    permission = when (permission) {
        EquipmentPermissionDto.Permission.PERMITTED -> EquipmentPermission.Permission.PERMITTED
        EquipmentPermissionDto.Permission.REVOKED ->  EquipmentPermission.Permission.REVOKED
    }
)

internal fun ChallengeModelDto.toDomain() = ChallengeModel(
    name = name,
    description = description,
    responseType = responseType,
    challengeResourceSettingsModel = settingsModelDto.toDomain()
)

internal fun List<ChallengeModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
