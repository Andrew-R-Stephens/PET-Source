package com.tritiumgaming.data.challenges.source.local

import com.tritiumgaming.data.challenges.dto.ChallengeModelDto
import com.tritiumgaming.data.challenges.source.ChallengeDataSource
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto.Permission.PERMITTED
import com.tritiumgaming.data.challenges.source.local.ChallengeLocalDataSource.DifficultySettingsModelDto.EquipmentPermissionDto.Permission.REVOKED
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.AUDIO_ONLY
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.DEJA_VU
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.DETECTIVES_ONLY
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.DO_AS_I_COMMAND
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.FROSTBITTEN
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.GLOW_IN_THE_DARK
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.GOTTA_GO_FAST
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_EXTREME
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_HIDE
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_SEEKER
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.LIGHTS_OUT
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.MISSED_DELIVERY
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.NO_EVIDENCE
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.PARANORMAL_PAPARAZZI
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.PRIMITIVE
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.SANITY_SURVIVAL
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.SLOW_AND_STEADY
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.SPEEDRUN
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.SPEED_DEMONS
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.SURVIVAL_OF_THE_FITTEST
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.TAG_YOURE_IT
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.TECHNOPHILIA
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.THE_APOCALYPSE_DRAWS_NEAR
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.TORTOISE_AND_THE_HARE_HARE
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.TORTOISE_AND_THE_HARE_TORTOISE
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeDescription.VULNERABLE
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.CRUCIFIX
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.DOTS
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.EMF
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.FIRELIGHT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.FLASHLIGHT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.GHOST_WRITING_BOOK
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.HEAD_GEAR
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.IGNITER
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.INCENSE
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.MOTION_SENSOR
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.PARABOLIC_MICROPHONE
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.PHOTO_CAMERA
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SALT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SANITY_MEDICATION
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SOUND_RECORDER
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SOUND_SENSOR
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.SPIRIT_BOX
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.THERMOMETER
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.TRIPOD
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.UV_LIGHT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier.VIDEO_CAMERA
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityLevel
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityMonitor
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ChangingFavoriteRoom
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.HAUNTED_MIRROR
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.MONKEY_PAW
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.MUSIC_BOX
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.OUIJA_BOARD
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.RANDOM
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.SUMMONING_CIRCLE
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.TAROT_CARDS
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.VOODOO_DOLL
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity.NONE
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_1
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_2
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_7
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
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel.EquipmentPermission.Companion.ALL
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

class ChallengeLocalDataSource: ChallengeDataSource {

    private val baseChallengeResourceDto = ChallengeResourceDto(
        name = ChallengeTitle.LIGHTS_OUT,
        description = LIGHTS_OUT,
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
            cursedPossessionsQuantity = QUANTITY_1,
            cursedPossessions = listOf(RANDOM),
            equipmentPermissionDto = emptyList()
        )
    )

    private val challengeResourceDto = listOf(
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.LIGHTS_OUT,
                map = SimpleMapResources.MapTitle.TANGLEWOOD,
                description = LIGHTS_OUT,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    flashlights = Flashlights.OFF,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(MUSIC_BOX),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(MOTION_SENSOR),
                        EquipmentPermissionDto(PARABOLIC_MICROPHONE),
                        EquipmentPermissionDto(SOUND_RECORDER),
                        EquipmentPermissionDto(SOUND_SENSOR),
                        EquipmentPermissionDto(PHOTO_CAMERA, 1),
                        EquipmentPermissionDto(FLASHLIGHT),
                        EquipmentPermissionDto(TRIPOD)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SPEED_DEMONS,
                map = SimpleMapResources.MapTitle.CAMP_MAPLE,
                description = SPEED_DEMONS,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_150,
                    ghostSpeed = GhostSpeed.SPEED_150,
                    changingFavouriteRoom = ChangingFavoriteRoom.VERY_HIGH,
                    activityLevel = ActivityLevel.HIGH,
                    eventFrequency = EventFrequency.HIGH,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(
                        MONKEY_PAW),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(IGNITER),
                        EquipmentPermissionDto(MOTION_SENSOR)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.DETECTIVES_ONLY,
                map = SimpleMapResources.MapTitle.EDGEFIELD,
                description = DETECTIVES_ONLY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.MEDIUM,
                    activityLevel = ActivityLevel.MEDIUM,
                    huntDuration = HuntDuration.MEDIUM,
                    evidenceGiven = EvidenceGiven.COUNT_0,
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(OUIJA_BOARD)
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.HIDE_AND_SEEK_SEEKER,
                map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
                description = HIDE_AND_SEEK_SEEKER,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_35,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_0,
                    roamingFrequency = RoamingFrequency.MEDIUM,
                    changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                    eventFrequency = EventFrequency.LOW,
                    friendlyGhost = FriendlyGhost.ON,
                    doorsStartingOpen = DoorsStartingOpen.NONE,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    cursedPossessionsQuantity = NONE,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(INCENSE),
                        EquipmentPermissionDto(CRUCIFIX)
                    )                    
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.HIDE_AND_SEEK_HIDE,
                map = SimpleMapResources.MapTitle.POINT_HOPE,
                description = HIDE_AND_SEEK_HIDE,
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
                    cursedPossessionsQuantity = NONE,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(HEAD_GEAR),
                        EquipmentPermissionDto(PARABOLIC_MICROPHONE),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.FROSTBITTEN,
                map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
                description = FROSTBITTEN,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_100,
                    playerSpeed = PlayerSpeed.SPEED_75,
                    ghostSpeed = GhostSpeed.SPEED_75,
                    activityLevel = ActivityLevel.MEDIUM,
                    huntDuration = HuntDuration.MEDIUM,
                    weather = Weather.SNOW,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    cursedPossessionsQuantity = NONE,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(IGNITER, ALL),
                        EquipmentPermissionDto(FIRELIGHT, ALL),
                        EquipmentPermissionDto(INCENSE, ALL)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.DO_AS_I_COMMAND,
                map = SimpleMapResources.MapTitle.SUNNY_MEADOWS,
                description = DO_AS_I_COMMAND,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    sanityPillRestoration = SanityPillRestoration.RESTORE_50,
                    sanityDrainSpeed = SanityDrainSpeed.SPEED_100,
                    eventFrequency = EventFrequency.LOW,
                    gracePeriod = GracePeriod.PERIOD_2,
                    huntDuration = HuntDuration.LOW,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    cursedPossessionsQuantity = QUANTITY_7,
                    cursedPossessions = listOf(
                        TAROT_CARDS,
                        OUIJA_BOARD,
                        HAUNTED_MIRROR,
                        MUSIC_BOX,
                        SUMMONING_CIRCLE,
                        VOODOO_DOLL,
                        MONKEY_PAW
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TORTOISE_AND_THE_HARE_HARE,
                map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                description = TORTOISE_AND_THE_HARE_HARE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_125,
                    ghostSpeed = GhostSpeed.SPEED_50,
                    doorsStartingOpen = DoorsStartingOpen.HIGH
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TORTOISE_AND_THE_HARE_TORTOISE,
                map = SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE,
                description = TORTOISE_AND_THE_HARE_TORTOISE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_75,
                    ghostSpeed = GhostSpeed.SPEED_150,
                    changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                    activityLevel = ActivityLevel.HIGH,
                    huntDuration = HuntDuration.LOW,
                    killsExtendHunts = KillsExtendHunts.HIGH,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(FIRELIGHT, 2),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.GOTTA_GO_FAST,
                map = SimpleMapResources.MapTitle.PRISON,
                description = GOTTA_GO_FAST,
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
                        EquipmentPermissionDto(SOUND_RECORDER, 1),
                        EquipmentPermissionDto(INCENSE),
                        EquipmentPermissionDto(CRUCIFIX),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SANITY_SURVIVAL,
                map = SimpleMapResources.MapTitle.RIDGEVIEW,
                description = SANITY_SURVIVAL,
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
                    cursedPossessionsQuantity = QUANTITY_2,
                    cursedPossessions = listOf(
                        TAROT_CARDS,
                        VOODOO_DOLL
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SPEEDRUN,
                map = SimpleMapResources.MapTitle.TANGLEWOOD,
                description = SPEEDRUN,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    playerSpeed = PlayerSpeed.SPEED_100,
                    roamingFrequency = RoamingFrequency.HIGH,
                    changingFavouriteRoom = ChangingFavoriteRoom.LOW,
                    activityLevel = ActivityLevel.LOW,
                    eventFrequency = EventFrequency.MEDIUM,
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    numberOfHidingPlaces = NumberOfHidingPlaces.MEDIUM,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    cursedPossessionsQuantity = QUANTITY_7,
                    cursedPossessions = listOf(
                        TAROT_CARDS,
                        OUIJA_BOARD,
                        HAUNTED_MIRROR,
                        MUSIC_BOX,
                        SUMMONING_CIRCLE,
                        VOODOO_DOLL,
                        MONKEY_PAW
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SURVIVAL_OF_THE_FITTEST,
                map = SimpleMapResources.MapTitle.EDGEFIELD,
                description = SURVIVAL_OF_THE_FITTEST,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(MONKEY_PAW),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(SANITY_MEDICATION, 2),
                        EquipmentPermissionDto(THERMOMETER),
                        EquipmentPermissionDto(UV_LIGHT),
                        EquipmentPermissionDto(GHOST_WRITING_BOOK),
                        EquipmentPermissionDto(DOTS),
                        EquipmentPermissionDto(VIDEO_CAMERA),
                        EquipmentPermissionDto(TRIPOD)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.PRIMITIVE,
                map = SimpleMapResources.MapTitle.RIDGEVIEW,
                description = PRIMITIVE,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(OUIJA_BOARD),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(HEAD_GEAR),
                        EquipmentPermissionDto(SANITY_MEDICATION),
                        EquipmentPermissionDto(MOTION_SENSOR),
                        EquipmentPermissionDto(PARABOLIC_MICROPHONE),
                        EquipmentPermissionDto(SOUND_RECORDER),
                        EquipmentPermissionDto(SOUND_SENSOR),
                        EquipmentPermissionDto(EMF),
                        EquipmentPermissionDto(PHOTO_CAMERA),
                        EquipmentPermissionDto(SPIRIT_BOX),
                        EquipmentPermissionDto(DOTS),
                        EquipmentPermissionDto(FLASHLIGHT),
                        EquipmentPermissionDto(VIDEO_CAMERA),
                        EquipmentPermissionDto(TRIPOD),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.VULNERABLE,
                map = SimpleMapResources.MapTitle.WILLOW,
                description = VULNERABLE,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(VOODOO_DOLL),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(SANITY_MEDICATION),
                        EquipmentPermissionDto(FIRELIGHT, 2),
                        EquipmentPermissionDto(SOUND_RECORDER),
                        EquipmentPermissionDto(INCENSE),
                        EquipmentPermissionDto(CRUCIFIX),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.MISSED_DELIVERY,
                map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
                description = MISSED_DELIVERY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(DOTS, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(EMF, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(FLASHLIGHT, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(PHOTO_CAMERA, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(IGNITER, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(FIRELIGHT, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(UV_LIGHT, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(CRUCIFIX, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(VIDEO_CAMERA, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(SPIRIT_BOX, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(THERMOMETER, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(SALT, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(INCENSE, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(TRIPOD, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(MOTION_SENSOR, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(SOUND_RECORDER, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(SOUND_SENSOR, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(SANITY_MEDICATION, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(GHOST_WRITING_BOOK, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(PARABOLIC_MICROPHONE, quantity = 1, permission = PERMITTED),
                        EquipmentPermissionDto(HEAD_GEAR, quantity = 1, permission = PERMITTED),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.AUDIO_ONLY,
                map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                description = AUDIO_ONLY,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(MUSIC_BOX),
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TECHNOPHILIA,
                map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                description = TECHNOPHILIA,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(TAROT_CARDS),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(IGNITER),
                        EquipmentPermissionDto(FIRELIGHT),
                        EquipmentPermissionDto(INCENSE),
                        EquipmentPermissionDto(SALT),
                        EquipmentPermissionDto(CRUCIFIX),
                        EquipmentPermissionDto(GHOST_WRITING_BOOK),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.NO_EVIDENCE,
                map = SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE,
                description = NO_EVIDENCE,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(HAUNTED_MIRROR),
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.THE_APOCALYPSE_DRAWS_NEAR,
                map = SimpleMapResources.MapTitle.SUNNY_MEADOWS_RESTRICTED,
                description = THE_APOCALYPSE_DRAWS_NEAR,
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
                    cursedPossessionsQuantity = NONE
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.SLOW_AND_STEADY,
                map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
                description = SLOW_AND_STEADY,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    ghostSpeed = GhostSpeed.SPEED_50,
                    gracePeriod = GracePeriod.PERIOD_1,
                    weather = Weather.HEAVY_RAIN,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                    cursedPossessionsQuantity = NONE
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.PARANORMAL_PAPARAZZI,
                map = SimpleMapResources.MapTitle.POINT_HOPE,
                description = PARANORMAL_PAPARAZZI,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(MONKEY_PAW),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(SANITY_MEDICATION),
                        EquipmentPermissionDto(INCENSE),
                        EquipmentPermissionDto(SALT),
                        EquipmentPermissionDto(CRUCIFIX),
                        EquipmentPermissionDto(EMF),
                        EquipmentPermissionDto(THERMOMETER)
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.HIDE_AND_SEEK_EXTREME,
                map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
                description = HIDE_AND_SEEK_EXTREME,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(MONKEY_PAW),
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(SANITY_MEDICATION),
                        EquipmentPermissionDto(INCENSE),
                        EquipmentPermissionDto(UV_LIGHT),
                        EquipmentPermissionDto(FLASHLIGHT),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.GLOW_IN_THE_DARK,
                map = SimpleMapResources.MapTitle.EDGEFIELD,
                description = GLOW_IN_THE_DARK,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    equipmentPermissionDto = listOf(
                        EquipmentPermissionDto(MOTION_SENSOR),
                        EquipmentPermissionDto(FIRELIGHT),
                        EquipmentPermissionDto(SOUND_RECORDER),
                        EquipmentPermissionDto(FLASHLIGHT),
                        EquipmentPermissionDto(VIDEO_CAMERA),
                        EquipmentPermissionDto(TRIPOD),
                    )
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.DEJA_VU,
                map = SimpleMapResources.MapTitle.TANGLEWOOD,
                description = DEJA_VU,
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
                    cursedPossessionsQuantity = QUANTITY_1,
                    cursedPossessions = listOf(OUIJA_BOARD)
                )
            ),
            baseChallengeResourceDto.copy(
                name = ChallengeTitle.TAG_YOURE_IT,
                map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
                description = TAG_YOURE_IT,
                settingsModelDto = baseChallengeResourceDto.settingsModelDto.copy(
                    startingSanity = StartingSanity.SANITY_0,
                    sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                    playerSpeed = PlayerSpeed.SPEED_150,
                    gracePeriod = GracePeriod.PERIOD_1,
                    doorsStartingOpen = DoorsStartingOpen.HIGH,
                    cursedPossessionsQuantity = NONE
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
        val cursedPossessions: List<CursedPossession>,
        val equipmentPermissionDto: List<EquipmentPermissionDto>
    ) {
        data class EquipmentPermissionDto(
            val identifier: EquipmentIdentifier,
            val quantity: Int = ALL,
            val permission: Permission = REVOKED
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
