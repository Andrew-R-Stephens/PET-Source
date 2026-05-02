package com.tritiumgaming.data.customdifficulty.mapper

import com.tritiumgaming.database.customdifficulty.CustomDifficultyEntity
import com.tritiumgaming.shared.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel

fun CustomDifficultyEntity.toDomain() = CustomDifficultyModel(
    id = id,
    name = name,
    settings = DifficultySettingsModel(
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
        cursedPossessions = cursedPossessions
    )
)

fun CustomDifficultyModel.toEntity() = CustomDifficultyEntity(
    id = id,
    name = name,
    startingSanity = settings.startingSanity,
    sanityPillRestoration = settings.sanityPillRestoration,
    sanityDrainSpeed = settings.sanityDrainSpeed,
    sprinting = settings.sprinting,
    playerSpeed = settings.playerSpeed,
    flashlights = settings.flashlights,
    loseItemsAndConsumables = settings.loseItemsAndConsumables,
    ghostSpeed = settings.ghostSpeed,
    roamingFrequency = settings.roamingFrequency,
    changingFavouriteRoom = settings.changingFavouriteRoom,
    activityLevel = settings.activityLevel,
    eventFrequency = settings.eventFrequency,
    friendlyGhost = settings.friendlyGhost,
    gracePeriod = settings.gracePeriod,
    huntDuration = settings.huntDuration,
    killsExtendHunts = settings.killsExtendHunts,
    evidenceGiven = settings.evidenceGiven,
    fingerprintChance = settings.fingerprintChance,
    fingerprintDuration = settings.fingerprintDuration,
    setupTime = settings.setupTime,
    weather = settings.weather,
    doorsStartingOpen = settings.doorsStartingOpen,
    numberOfHidingPlaces = settings.numberOfHidingPlaces,
    sanityMonitor = settings.sanityMonitor,
    activityMonitor = settings.activityMonitor,
    fuseBoxAtStartOfContract = settings.fuseBoxAtStartOfContract,
    fuseBoxVisibleOnMap = settings.fuseBoxVisibleOnMap,
    cursedPossessionsQuantity = settings.cursedPossessionsQuantity,
    cursedPossessions = settings.cursedPossessions
)
