package com.tritiumgaming.data.challenges.source.local

import com.tritiumgaming.data.challenges.dto.ChallengeModelDto
import com.tritiumgaming.data.challenges.source.ChallengeDataSource
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto.Companion.ALL
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto.Permission
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier
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
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

class ChallengeLocalDataSource: ChallengeDataSource {

    private val baseChallengeResourceDto = ChallengeResourceDto(
        name = ChallengeTitle.LIGHTS_OUT,
        description = ChallengeResources.ChallengeDescription.LIGHTS_OUT,
        map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
        responseType = DifficultyResponseType.UNKNOWN,
        settingsModelDto = DifficultySettingsModelDto(
            startingSanity = StartingSanity.SANITY_100,
            sanityPillRestoration = SanityPillRestoration.RESTORE_30,
            sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
            sprinting = Sprinting.ON,
            playerSpeed = PlayerSpeed.SPEED_100,
            flashlights = Flashlights.ON,
            loseItemsAndConsumables = LoseItemsAndConsumables.ON,
            ghostSpeed = GhostSpeed.SPEED_100,
            roamingFrequency = RoamingFrequency.HIGH,
            changingFavouriteRoom = ChangingFavoriteRoom.LOW,
            activityLevel = ActivityLevel.LOW,
            eventFrequency = EventFrequency.MEDIUM,
            friendlyGhost = FriendlyGhost.OFF,
            gracePeriod = GracePeriod.PERIOD_3,
            huntDuration = HuntDuration.HIGH,
            killsExtendHunts = KillsExtendHunts.OFF,
            evidenceGiven = EvidenceGiven.COUNT_3,
            fingerprintChance = FingerprintChance.CHANCE_100,
            fingerprintDuration = FingerprintDuration.DURATION_120,
            setupTime = SetupTime.TIME_0,
            weather = Weather.RANDOM,
            doorsStartingOpen = DoorsStartingOpen.MEDIUM,
            numberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
            sanityMonitor = SanityMonitor.ON,
            activityMonitor = ActivityMonitor.ON,
            fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
            fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
            cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
            cursedPossession1 = CursedPossession.RANDOM,
            cursedPossession2 = CursedPossession.RANDOM,
            cursedPossession3 = CursedPossession.RANDOM,
            cursedPossession4 = CursedPossession.RANDOM,
            cursedPossession5 = CursedPossession.RANDOM,
            cursedPossession6 = CursedPossession.RANDOM,
            cursedPossession7 = CursedPossession.RANDOM,
            equipmentPermissionDto = emptyList()
        )
    )

    private val challengeResourceDto
        get() = listOf(
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.LIGHTS_OUT,
                map = SimpleMapResources.MapTitle.TANGLEWOOD,
                description = ChallengeResources.ChallengeDescription.LIGHTS_OUT,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    flashlights = Flashlights.OFF,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.MUSIC_BOX,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.MOTION_SENSOR),
                        EquipmentPermissionDto(EquipmentIdentifier.PARABOLIC_MICROPHONE),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_RECORDER),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_SENSOR),
                        EquipmentPermissionDto(EquipmentIdentifier.PHOTO_CAMERA, 1),
                        EquipmentPermissionDto(EquipmentIdentifier.FLASHLIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.TRIPOD)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SPEED_DEMONS,
                map = SimpleMapResources.MapTitle.CAMP_MAPLE,
                description = ChallengeResources.ChallengeDescription.SPEED_DEMONS,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_150,
                    ghostSpeed = GhostSpeed.SPEED_150,
                    changingFavouriteRoom = ChangingFavoriteRoom.VERY_HIGH,
                    activityLevel = ActivityLevel.HIGH,
                    eventFrequency = EventFrequency.HIGH,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.MONKEY_PAW,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.IGNITER),
                        EquipmentPermissionDto(EquipmentIdentifier.MOTION_SENSOR)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.DETECTIVES_ONLY,
                map = SimpleMapResources.MapTitle.EDGEFIELD,
                description = ChallengeResources.ChallengeDescription.DETECTIVES_ONLY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.MEDIUM,
                    activityLevel = ActivityLevel.MEDIUM,
                    huntDuration = HuntDuration.MEDIUM,
                    evidenceGiven = EvidenceGiven.COUNT_0,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.OUIJA_BOARD,
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.HIDE_AND_SEEK_SEEKER,
                map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
                description = ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_SEEKER,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_35,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_0,
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                    eventFrequency = EventFrequency.LOW,
                    friendlyGhost = FriendlyGhost.ON,
                    doorsStartingOpen = DoorsStartingOpen.NONE,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE),
                        EquipmentPermissionDto(EquipmentIdentifier.CRUCIFIX)
                    )                    
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.HIDE_AND_SEEK_HIDE,
                map = SimpleMapResources.MapTitle.POINT_HOPE,
                description = ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_HIDE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_150,
                    roamingFrequency = RoamingFrequency.LOW,
                    changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                    activityLevel = ActivityLevel.HIGH,
                    eventFrequency = EventFrequency.HIGH,
                    gracePeriod = GracePeriod.PERIOD_5,
                    killsExtendHunts = KillsExtendHunts.HIGH,
                    setupTime = SetupTime.TIME_60,
                    doorsStartingOpen = DoorsStartingOpen.LOW,
                    numberOfHidingPlaces = NumberOfHidingPlaces.VERY_HIGH,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.HEAD_GEAR),
                        EquipmentPermissionDto(EquipmentIdentifier.PARABOLIC_MICROPHONE),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.FROSTBITTEN,
                map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
                description = ChallengeResources.ChallengeDescription.FROSTBITTEN,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_100,
                    playerSpeed = PlayerSpeed.SPEED_75,
                    ghostSpeed = GhostSpeed.SPEED_75,
                    activityLevel = ActivityLevel.MEDIUM,
                    huntDuration = HuntDuration.MEDIUM,
                    weather = Weather.SNOW,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.IGNITER, ALL),
                        EquipmentPermissionDto(EquipmentIdentifier.FIRELIGHT, ALL),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE, ALL)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.DO_AS_I_COMMAND,
                map = SimpleMapResources.MapTitle.SUNNY_MEADOWS,
                description = ChallengeResources.ChallengeDescription.DO_AS_I_COMMAND,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_50,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_100,
                    eventFrequency = EventFrequency.LOW,
                    gracePeriod = GracePeriod.PERIOD_2,
                    huntDuration = HuntDuration.LOW,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_7,
                    cursedPossession1 = CursedPossession.TAROT_CARDS,
                    cursedPossession2 = CursedPossession.OUIJA_BOARD,
                    cursedPossession3 = CursedPossession.HAUNTED_MIRROR,
                    cursedPossession4 = CursedPossession.MUSIC_BOX,
                    cursedPossession5 = CursedPossession.SUMMONING_CIRCLE,
                    cursedPossession6 = CursedPossession.VOODOO_DOLL,
                    cursedPossession7 = CursedPossession.MONKEY_PAW
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TORTOISE_AND_THE_HARE_HARE,
                map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                description = ChallengeResources.ChallengeDescription.TORTOISE_AND_THE_HARE_HARE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_125,
                    ghostSpeed = GhostSpeed.SPEED_50,
                    doorsStartingOpen = DoorsStartingOpen.HIGH
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TORTOISE_AND_THE_HARE_TORTOISE,
                map = SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE,
                description = ChallengeResources.ChallengeDescription.TORTOISE_AND_THE_HARE_TORTOISE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_75,
                    ghostSpeed = GhostSpeed.SPEED_150,
                    changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                    activityLevel = ActivityLevel.HIGH,
                    huntDuration = HuntDuration.LOW,
                    killsExtendHunts = KillsExtendHunts.HIGH,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.FIRELIGHT, 2),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.GOTTA_GO_FAST,
                map = SimpleMapResources.MapTitle.PRISON,
                description = ChallengeResources.ChallengeDescription.GOTTA_GO_FAST,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    playerSpeed = PlayerSpeed.SPEED_125,
                    ghostSpeed = GhostSpeed.SPEED_125,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    sanityMonitor = SanityMonitor.OFF,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_RECORDER, 1),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE),
                        EquipmentPermissionDto(EquipmentIdentifier.CRUCIFIX),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SANITY_SURVIVAL,
                map = SimpleMapResources.MapTitle.RIDGEVIEW,
                description = ChallengeResources.ChallengeDescription.SANITY_SURVIVAL,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_75,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    eventFrequency = EventFrequency.LOW,
                    gracePeriod = GracePeriod.PERIOD_2,
                    killsExtendHunts = KillsExtendHunts.LOW,
                    evidenceGiven = EvidenceGiven.COUNT_2,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    sanityMonitor = SanityMonitor.OFF,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_2,
                    cursedPossession1 = CursedPossession.TAROT_CARDS,
                    cursedPossession2 = CursedPossession.VOODOO_DOLL,
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SPEEDRUN,
                map = SimpleMapResources.MapTitle.TANGLEWOOD,
                description = ChallengeResources.ChallengeDescription.SPEEDRUN,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_100,
                    roamingFrequency = RoamingFrequency.HIGH,
                    changingFavouriteRoom = ChangingFavoriteRoom.LOW,
                    activityLevel = ActivityLevel.LOW,
                    eventFrequency = EventFrequency.MEDIUM,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    numberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_7,
                    cursedPossession1 = CursedPossession.TAROT_CARDS,
                    cursedPossession2 = CursedPossession.OUIJA_BOARD,
                    cursedPossession3 = CursedPossession.HAUNTED_MIRROR,
                    cursedPossession4 = CursedPossession.MUSIC_BOX,
                    cursedPossession5 = CursedPossession.SUMMONING_CIRCLE,
                    cursedPossession6 = CursedPossession.VOODOO_DOLL,
                    cursedPossession7 = CursedPossession.MONKEY_PAW
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SURVIVAL_OF_THE_FITTEST,
                map = SimpleMapResources.MapTitle.EDGEFIELD,
                description = ChallengeResources.ChallengeDescription.SURVIVAL_OF_THE_FITTEST,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_100,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_30,
                    roamingFrequency = RoamingFrequency.HIGH,
                    changingFavouriteRoom = ChangingFavoriteRoom.LOW,
                    activityLevel = ActivityLevel.LOW,
                    eventFrequency = EventFrequency.MEDIUM,
                    gracePeriod = GracePeriod.PERIOD_3,
                    killsExtendHunts = KillsExtendHunts.OFF,
                    evidenceGiven = EvidenceGiven.COUNT_3,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    numberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.MONKEY_PAW,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.SANITY_MEDICATION, 2),
                        EquipmentPermissionDto(EquipmentIdentifier.THERMOMETER),
                        EquipmentPermissionDto(EquipmentIdentifier.UV_LIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.GHOST_WRITING_BOOK),
                        EquipmentPermissionDto(EquipmentIdentifier.DOTS),
                        EquipmentPermissionDto(EquipmentIdentifier.VIDEO_CAMERA),
                        EquipmentPermissionDto(EquipmentIdentifier.TRIPOD)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.PRIMITIVE,
                map = SimpleMapResources.MapTitle.RIDGEVIEW,
                description = ChallengeResources.ChallengeDescription.PRIMITIVE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_40,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_100,
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                    activityLevel = ActivityLevel.HIGH,
                    eventFrequency = EventFrequency.LOW,
                    gracePeriod = GracePeriod.PERIOD_5,
                    huntDuration = HuntDuration.LOW,
                    setupTime = SetupTime.TIME_300,
                    doorsStartingOpen = DoorsStartingOpen.NONE,
                    numberOfHidingPlaces = NumberOfHidingPlaces.VERY_HIGH,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.OUIJA_BOARD,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.HEAD_GEAR),
                        EquipmentPermissionDto(EquipmentIdentifier.SANITY_MEDICATION),
                        EquipmentPermissionDto(EquipmentIdentifier.MOTION_SENSOR),
                        EquipmentPermissionDto(EquipmentIdentifier.PARABOLIC_MICROPHONE),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_RECORDER),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_SENSOR),
                        EquipmentPermissionDto(EquipmentIdentifier.EMF),
                        EquipmentPermissionDto(EquipmentIdentifier.PHOTO_CAMERA),
                        EquipmentPermissionDto(EquipmentIdentifier.SPIRIT_BOX),
                        EquipmentPermissionDto(EquipmentIdentifier.DOTS),
                        EquipmentPermissionDto(EquipmentIdentifier.FLASHLIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.VIDEO_CAMERA),
                        EquipmentPermissionDto(EquipmentIdentifier.TRIPOD),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.VULNERABLE,
                map = SimpleMapResources.MapTitle.WILLOW,
                description = ChallengeResources.ChallengeDescription.VULNERABLE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.VOODOO_DOLL,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.SANITY_MEDICATION),
                        EquipmentPermissionDto(EquipmentIdentifier.FIRELIGHT, 2),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_RECORDER),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE),
                        EquipmentPermissionDto(EquipmentIdentifier.CRUCIFIX),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.MISSED_DELIVERY,
                map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
                description = ChallengeResources.ChallengeDescription.MISSED_DELIVERY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.DOTS, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.EMF, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.FLASHLIGHT, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.PHOTO_CAMERA, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.IGNITER, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.FIRELIGHT, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.UV_LIGHT, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.CRUCIFIX, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.VIDEO_CAMERA, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.SPIRIT_BOX, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.THERMOMETER, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.SALT, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.TRIPOD, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.MOTION_SENSOR, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_RECORDER, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_SENSOR, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.SANITY_MEDICATION, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.GHOST_WRITING_BOOK, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.PARABOLIC_MICROPHONE, quantity = 1, permission = Permission.PERMITTED),
                        EquipmentPermissionDto(EquipmentIdentifier.HEAD_GEAR, quantity = 1, permission = Permission.PERMITTED),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.AUDIO_ONLY,
                map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                description = ChallengeResources.ChallengeDescription.AUDIO_ONLY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_30,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
                    roamingFrequency = RoamingFrequency.HIGH,
                    changingFavouriteRoom = ChangingFavoriteRoom.LOW,
                    eventFrequency = EventFrequency.MEDIUM,
                    huntDuration = HuntDuration.HIGH,
                    setupTime = SetupTime.TIME_0,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    numberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.MUSIC_BOX,
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TECHNOPHILIA,
                map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                description = ChallengeResources.ChallengeDescription.TECHNOPHILIA,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.TAROT_CARDS,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.IGNITER),
                        EquipmentPermissionDto(EquipmentIdentifier.FIRELIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE),
                        EquipmentPermissionDto(EquipmentIdentifier.SALT),
                        EquipmentPermissionDto(EquipmentIdentifier.CRUCIFIX),
                        EquipmentPermissionDto(EquipmentIdentifier.GHOST_WRITING_BOOK),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.NO_EVIDENCE,
                map = SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE,
                description = ChallengeResources.ChallengeDescription.NO_EVIDENCE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_25,
                    changingFavouriteRoom = ChangingFavoriteRoom.MEDIUM,
                    eventFrequency = EventFrequency.HIGH,
                    gracePeriod = GracePeriod.PERIOD_2,
                    killsExtendHunts = KillsExtendHunts.LOW,
                    evidenceGiven = EvidenceGiven.COUNT_0,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.LOW,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.HAUNTED_MIRROR,
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.THE_APOCALYPSE_DRAWS_NEAR,
                map = SimpleMapResources.MapTitle.SUNNY_MEADOWS_RESTRICTED,
                description = ChallengeResources.ChallengeDescription.THE_APOCALYPSE_DRAWS_NEAR,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_100,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_30,
                    flashlights = Flashlights.ON,
                    ghostSpeed = GhostSpeed.SPEED_100,
                    changingFavouriteRoom = ChangingFavoriteRoom.LOW,
                    eventFrequency = EventFrequency.MEDIUM,
                    gracePeriod = GracePeriod.PERIOD_3,
                    killsExtendHunts = KillsExtendHunts.OFF,
                    evidenceGiven = EvidenceGiven.COUNT_3,
                    weather = Weather.RANDOM,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    numberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
                    sanityMonitor = SanityMonitor.ON,
                    activityMonitor = ActivityMonitor.ON,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SLOW_AND_STEADY,
                map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
                description = ChallengeResources.ChallengeDescription.SLOW_AND_STEADY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    ghostSpeed = GhostSpeed.SPEED_50,
                    gracePeriod = GracePeriod.PERIOD_1,
                    weather = Weather.HEAVY_RAIN,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.PARANORMAL_PAPARAZZI,
                map = SimpleMapResources.MapTitle.POINT_HOPE,
                description = ChallengeResources.ChallengeDescription.PARANORMAL_PAPARAZZI,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_35,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_150,
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                    activityLevel = ActivityLevel.MEDIUM,
                    gracePeriod = GracePeriod.PERIOD_4,
                    huntDuration = HuntDuration.MEDIUM,
                    setupTime = SetupTime.TIME_120,
                    doorsStartingOpen = DoorsStartingOpen.LOW,
                    numberOfHidingPlaces = NumberOfHidingPlaces.HIGH,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.MONKEY_PAW,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.SANITY_MEDICATION),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE),
                        EquipmentPermissionDto(EquipmentIdentifier.SALT),
                        EquipmentPermissionDto(EquipmentIdentifier.CRUCIFIX),
                        EquipmentPermissionDto(EquipmentIdentifier.EMF),
                        EquipmentPermissionDto(EquipmentIdentifier.THERMOMETER)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.HIDE_AND_SEEK_EXTREME,
                map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
                description = ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_EXTREME,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_100,
                    flashlights = Flashlights.OFF,
                    ghostSpeed = GhostSpeed.SPEED_150,
                    roamingFrequency = RoamingFrequency.LOW,
                    changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                    activityLevel = ActivityLevel.MEDIUM,
                    eventFrequency = EventFrequency.LOW,
                    gracePeriod = GracePeriod.PERIOD_5,
                    killsExtendHunts = KillsExtendHunts.HIGH,
                    setupTime = SetupTime.TIME_60,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.LOW,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.ON,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.MONKEY_PAW,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.SANITY_MEDICATION),
                        EquipmentPermissionDto(EquipmentIdentifier.INCENSE),
                        EquipmentPermissionDto(EquipmentIdentifier.UV_LIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.FLASHLIGHT),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.GLOW_IN_THE_DARK,
                map = SimpleMapResources.MapTitle.EDGEFIELD,
                description = ChallengeResources.ChallengeDescription.GLOW_IN_THE_DARK,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(EquipmentIdentifier.MOTION_SENSOR),
                        EquipmentPermissionDto(EquipmentIdentifier.FIRELIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.SOUND_RECORDER),
                        EquipmentPermissionDto(EquipmentIdentifier.FLASHLIGHT),
                        EquipmentPermissionDto(EquipmentIdentifier.VIDEO_CAMERA),
                        EquipmentPermissionDto(EquipmentIdentifier.TRIPOD),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.DEJA_VU,
                map = SimpleMapResources.MapTitle.TANGLEWOOD,
                description = ChallengeResources.ChallengeDescription.DEJA_VU,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sprinting = Sprinting.OFF,
                    ghostSpeed = GhostSpeed.SPEED_75,
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                    gracePeriod = GracePeriod.PERIOD_4,
                    weather = Weather.LIGHT_RAIN,
                    doorsStartingOpen = DoorsStartingOpen.NONE,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.OUIJA_BOARD,
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TAG_YOURE_IT,
                map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
                description = ChallengeResources.ChallengeDescription.TAG_YOURE_IT,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    playerSpeed = PlayerSpeed.SPEED_150,
                    gracePeriod = GracePeriod.PERIOD_1,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE
                )
            ),
        )

    override fun fetchChallenges(): Result<List<ChallengeModelDto>> =
        Result.success(challengeResourceDto.toChallengeModelDto())

    private fun List<ChallengeResourceDto>.toChallengeModelDto() = map{ it.toChallengeModelDto() }

    private fun ChallengeResourceDto.toChallengeModelDto() = ChallengeModelDto(
        name = name,
        description = description,
        responseType = responseType,
        settingsModelDto = settingsModelDto.toSettingsModelDto()
    )

    internal data class ChallengeResourceDto(
        val name: ChallengeTitle,
        val description: ChallengeResources.ChallengeDescription,
        val map: SimpleMapResources.MapTitle,
        val responseType: DifficultyResponseType,
        val settingsModelDto: DifficultySettingsModelDto
    ) {
        fun DifficultySettingsModelDto.toSettingsModelDto() = ChallengeModelDto.DifficultySettingsModelDto(
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
            equipmentPermissionDto = equipmentPermissionDto
        )
    }

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
    ) {
        data class EquipmentPermissionDto(
            val identifier: EquipmentIdentifier,
            val quantity: Int = ALL,
            val permission: Permission = Permission.REVOKED
        ) {
            enum class Permission {
                PERMITTED,
                REVOKED
            }

            companion object {
                const val ALL = Integer.MAX_VALUE
            }
        }

    }

}
