package com.tritiumgaming.data.challenge.dto

import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeDescription
import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources
import com.tritiumstudios.data.difficultysetting.dto.DifficultySettingsModelDto

internal data class ChallengeResourceDto(
    val name: ChallengeTitle = ChallengeTitle.LIGHTS_OUT,
    val description: ChallengeDescription = ChallengeDescription.LIGHTS_OUT,
    val map: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.TANGLEWOOD,
    val responseType: GhostResponsePresentation = GhostResponsePresentation.UNKNOWN,
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
