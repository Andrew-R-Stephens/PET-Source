package com.tritiumgaming.data.challenges.dto

import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.model.ChallengeModel
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel.EquipmentPermission

data class ChallengeModelDto(
    val name: ChallengeResources.ChallengeTitle,
    val description: ChallengeResources.ChallengeDescription,
    val responseType: DifficultyResponseType,
    val settingsModelDto: ChallengeLocalDataSource.DifficultySettingsModelDto
)

internal fun ChallengeLocalDataSource.DifficultySettingsModelDto.toDomain() = DifficultySettingsModel(
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
