package com.tritiumgaming.data.challenges.dto

import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

internal data class ChallengeResourceDto(
    val name: ChallengeTitle = ChallengeTitle.LIGHTS_OUT,
    val description: ChallengeDescription = ChallengeDescription.LIGHTS_OUT,
    val map: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.TANGLEWOOD,
    val responseType: DifficultyResponseType = DifficultyResponseType.UNKNOWN,
    val settingsModelDto: DifficultySettingsModelDto = DifficultySettingsModelDto()
) {
    fun DifficultySettingsModelDto.toSettingsModelDto() = DifficultySettingsModelDto(
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
}
