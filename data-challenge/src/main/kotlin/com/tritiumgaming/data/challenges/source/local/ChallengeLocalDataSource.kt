package com.tritiumgaming.data.challenges.source.local

import com.tritiumgaming.data.challenges.dto.ChallengeModelDto
import com.tritiumgaming.data.challenges.dto.ChallengeResourceDto
import com.tritiumgaming.data.challenges.source.ChallengeDataSource
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
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.CRUCIFIX
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.DOTS
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.EMF
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.FIRELIGHT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.FLASHLIGHT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.GHOST_WRITING_BOOK
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.HEAD_GEAR
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.IGNITER
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.INCENSE
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.MOTION_SENSOR
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.PARABOLIC_MICROPHONE
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.PHOTO_CAMERA
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.SALT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.SANITY_MEDICATION
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.SOUND_RECORDER
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.SOUND_SENSOR
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.SPIRIT_BOX
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.THERMOMETER
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.TRIPOD
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.UV_LIGHT
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle.VIDEO_CAMERA
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumgaming.shared.data.difficultysetting.dto.DifficultySettingsResourceModelDto
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission.Companion.ALL
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission.Permission.PERMITTED
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityLevel
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ActivityMonitor
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.ChangingFavoriteRoom
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.HAUNTED_MIRROR
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.MONKEY_PAW
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.MUSIC_BOX
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.OUIJA_BOARD
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.RANDOM
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.SUMMONING_CIRCLE
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.TAROT_CARDS
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession.VOODOO_DOLL
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
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

class ChallengeLocalDataSource: ChallengeDataSource {

    private val challengeResourceDto = listOf(
        ChallengeResourceDto(
            name = ChallengeTitle.LIGHTS_OUT,
            map = SimpleMapResources.MapTitle.TANGLEWOOD,
            description = LIGHTS_OUT,
            settingsModelDto = DifficultySettingsModelDto(
                flashlights = Flashlights.OFF,
                doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
                cursedPossessionsQuantity = QUANTITY_1,
                cursedPossessions = listOf(MUSIC_BOX),
                equipmentPermission = listOf(
                    EquipmentPermission(MOTION_SENSOR),
                    EquipmentPermission(PARABOLIC_MICROPHONE),
                    EquipmentPermission(SOUND_RECORDER),
                    EquipmentPermission(SOUND_SENSOR),
                    EquipmentPermission(PHOTO_CAMERA, 1),
                    EquipmentPermission(FLASHLIGHT),
                    EquipmentPermission(TRIPOD)
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.SPEED_DEMONS,
            map = SimpleMapResources.MapTitle.CAMP_MAPLE,
            description = SPEED_DEMONS,
            settingsModelDto = DifficultySettingsModelDto(
                playerSpeed = PlayerSpeed.SPEED_150,
                ghostSpeed = GhostSpeed.SPEED_150,
                changingFavouriteRoom = ChangingFavoriteRoom.VERY_HIGH,
                activityLevel = ActivityLevel.HIGH,
                eventFrequency = EventFrequency.HIGH,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                cursedPossessionsQuantity = QUANTITY_1,
                cursedPossessions = listOf(
                    MONKEY_PAW),
                equipmentPermission = listOf(
                    EquipmentPermission(IGNITER),
                    EquipmentPermission(MOTION_SENSOR)
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.DETECTIVES_ONLY,
            map = SimpleMapResources.MapTitle.EDGEFIELD,
            description = DETECTIVES_ONLY,
            settingsModelDto = DifficultySettingsModelDto(
                roamingFrequency = RoamingFrequency.MEDIUM,
                changingFavouriteRoom = ChangingFavoriteRoom.MEDIUM,
                activityLevel = ActivityLevel.MEDIUM,
                huntDuration = HuntDuration.MEDIUM,
                evidenceGiven = EvidenceGiven.COUNT_0,
                cursedPossessionsQuantity = QUANTITY_1,
                cursedPossessions = listOf(OUIJA_BOARD)
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.HIDE_AND_SEEK_SEEKER,
            map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
            description = HIDE_AND_SEEK_SEEKER,
            settingsModelDto = DifficultySettingsModelDto(
                sanityPillRestoration = SanityPillRestoration.RESTORE_35,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_0,
                roamingFrequency = RoamingFrequency.MEDIUM,
                changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                eventFrequency = EventFrequency.LOW,
                friendlyGhost = FriendlyGhost.ON,
                doorsStartingOpen = DoorsStartingOpen.NONE,
                numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                cursedPossessionsQuantity = NONE,
                equipmentPermission = listOf(
                    EquipmentPermission(INCENSE),
                    EquipmentPermission(CRUCIFIX)
                )                    
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.HIDE_AND_SEEK_HIDE,
            map = SimpleMapResources.MapTitle.POINT_HOPE,
            description = HIDE_AND_SEEK_HIDE,
            settingsModelDto = DifficultySettingsModelDto(
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
                equipmentPermission = listOf(
                    EquipmentPermission(HEAD_GEAR),
                    EquipmentPermission(PARABOLIC_MICROPHONE),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.FROSTBITTEN,
            map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
            description = FROSTBITTEN,
            settingsModelDto = DifficultySettingsModelDto(
                sanityPillRestoration = SanityPillRestoration.RESTORE_100,
                playerSpeed = PlayerSpeed.SPEED_75,
                ghostSpeed = GhostSpeed.SPEED_75,
                activityLevel = ActivityLevel.MEDIUM,
                huntDuration = HuntDuration.MEDIUM,
                weather = Weather.SNOW,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                cursedPossessionsQuantity = NONE,
                equipmentPermission = listOf(
                    EquipmentPermission(IGNITER, ALL),
                    EquipmentPermission(FIRELIGHT, ALL),
                    EquipmentPermission(INCENSE, ALL)
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.DO_AS_I_COMMAND,
            map = SimpleMapResources.MapTitle.SUNNY_MEADOWS,
            description = DO_AS_I_COMMAND,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.TORTOISE_AND_THE_HARE_HARE,
            map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
            description = TORTOISE_AND_THE_HARE_HARE,
            settingsModelDto = DifficultySettingsModelDto(
                playerSpeed = PlayerSpeed.SPEED_125,
                ghostSpeed = GhostSpeed.SPEED_50,
                doorsStartingOpen = DoorsStartingOpen.HIGH
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.TORTOISE_AND_THE_HARE_TORTOISE,
            map = SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE,
            description = TORTOISE_AND_THE_HARE_TORTOISE,
            settingsModelDto = DifficultySettingsModelDto(
                playerSpeed = PlayerSpeed.SPEED_75,
                ghostSpeed = GhostSpeed.SPEED_150,
                changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                activityLevel = ActivityLevel.HIGH,
                huntDuration = HuntDuration.LOW,
                killsExtendHunts = KillsExtendHunts.HIGH,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                equipmentPermission = listOf(
                    EquipmentPermission(FIRELIGHT, 2),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.GOTTA_GO_FAST,
            map = SimpleMapResources.MapTitle.PRISON,
            description = GOTTA_GO_FAST,
            settingsModelDto = DifficultySettingsModelDto(
                startingSanity = StartingSanity.SANITY_0,
                sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                playerSpeed = PlayerSpeed.SPEED_125,
                ghostSpeed = GhostSpeed.SPEED_125,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                sanityMonitor = SanityMonitor.OFF,
                fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                equipmentPermission = listOf(
                    EquipmentPermission(SOUND_RECORDER, 1),
                    EquipmentPermission(INCENSE),
                    EquipmentPermission(CRUCIFIX),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.SANITY_SURVIVAL,
            map = SimpleMapResources.MapTitle.RIDGEVIEW,
            description = SANITY_SURVIVAL,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.SPEEDRUN,
            map = SimpleMapResources.MapTitle.TANGLEWOOD,
            description = SPEEDRUN,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.SURVIVAL_OF_THE_FITTEST,
            map = SimpleMapResources.MapTitle.EDGEFIELD,
            description = SURVIVAL_OF_THE_FITTEST,
            settingsModelDto = DifficultySettingsModelDto(
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
                equipmentPermission = listOf(
                    EquipmentPermission(SANITY_MEDICATION, 2),
                    EquipmentPermission(THERMOMETER),
                    EquipmentPermission(UV_LIGHT),
                    EquipmentPermission(GHOST_WRITING_BOOK),
                    EquipmentPermission(DOTS),
                    EquipmentPermission(VIDEO_CAMERA),
                    EquipmentPermission(TRIPOD)
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.PRIMITIVE,
            map = SimpleMapResources.MapTitle.RIDGEVIEW,
            description = PRIMITIVE,
            settingsModelDto = DifficultySettingsModelDto(
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
                equipmentPermission = listOf(
                    EquipmentPermission(HEAD_GEAR),
                    EquipmentPermission(SANITY_MEDICATION),
                    EquipmentPermission(MOTION_SENSOR),
                    EquipmentPermission(PARABOLIC_MICROPHONE),
                    EquipmentPermission(SOUND_RECORDER),
                    EquipmentPermission(SOUND_SENSOR),
                    EquipmentPermission(EMF),
                    EquipmentPermission(PHOTO_CAMERA),
                    EquipmentPermission(SPIRIT_BOX),
                    EquipmentPermission(DOTS),
                    EquipmentPermission(FLASHLIGHT),
                    EquipmentPermission(VIDEO_CAMERA),
                    EquipmentPermission(TRIPOD),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.VULNERABLE,
            map = SimpleMapResources.MapTitle.WILLOW,
            description = VULNERABLE,
            settingsModelDto = DifficultySettingsModelDto(
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                cursedPossessionsQuantity = QUANTITY_1,
                cursedPossessions = listOf(VOODOO_DOLL),
                equipmentPermission = listOf(
                    EquipmentPermission(SANITY_MEDICATION),
                    EquipmentPermission(FIRELIGHT, 2),
                    EquipmentPermission(SOUND_RECORDER),
                    EquipmentPermission(INCENSE),
                    EquipmentPermission(CRUCIFIX),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.MISSED_DELIVERY,
            map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
            description = MISSED_DELIVERY,
            settingsModelDto = DifficultySettingsModelDto(
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                equipmentPermission = listOf(
                    EquipmentPermission(DOTS, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(EMF, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(FLASHLIGHT, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(PHOTO_CAMERA, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(IGNITER, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(FIRELIGHT, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(UV_LIGHT, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(CRUCIFIX, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(VIDEO_CAMERA, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(SPIRIT_BOX, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(THERMOMETER, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(SALT, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(INCENSE, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(TRIPOD, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(MOTION_SENSOR, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(SOUND_RECORDER, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(SOUND_SENSOR, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(SANITY_MEDICATION, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(GHOST_WRITING_BOOK, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(PARABOLIC_MICROPHONE, quantity = 1, permission = PERMITTED),
                    EquipmentPermission(HEAD_GEAR, quantity = 1, permission = PERMITTED),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.AUDIO_ONLY,
            map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
            description = AUDIO_ONLY,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.TECHNOPHILIA,
            map = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
            description = TECHNOPHILIA,
            settingsModelDto = DifficultySettingsModelDto(
                doorsStartingOpen = DoorsStartingOpen.MEDIUM,
                cursedPossessionsQuantity = QUANTITY_1,
                cursedPossessions = listOf(TAROT_CARDS),
                equipmentPermission = listOf(
                    EquipmentPermission(IGNITER),
                    EquipmentPermission(FIRELIGHT),
                    EquipmentPermission(INCENSE),
                    EquipmentPermission(SALT),
                    EquipmentPermission(CRUCIFIX),
                    EquipmentPermission(GHOST_WRITING_BOOK),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.NO_EVIDENCE,
            map = SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE,
            description = NO_EVIDENCE,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.THE_APOCALYPSE_DRAWS_NEAR,
            map = SimpleMapResources.MapTitle.SUNNY_MEADOWS_RESTRICTED,
            description = THE_APOCALYPSE_DRAWS_NEAR,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.SLOW_AND_STEADY,
            map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
            description = SLOW_AND_STEADY,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.PARANORMAL_PAPARAZZI,
            map = SimpleMapResources.MapTitle.POINT_HOPE,
            description = PARANORMAL_PAPARAZZI,
            settingsModelDto = DifficultySettingsModelDto(
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
                equipmentPermission = listOf(
                    EquipmentPermission(SANITY_MEDICATION),
                    EquipmentPermission(INCENSE),
                    EquipmentPermission(SALT),
                    EquipmentPermission(CRUCIFIX),
                    EquipmentPermission(EMF),
                    EquipmentPermission(THERMOMETER)
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.HIDE_AND_SEEK_EXTREME,
            map = SimpleMapResources.MapTitle.CAMP_WOODWIND,
            description = HIDE_AND_SEEK_EXTREME,
            settingsModelDto = DifficultySettingsModelDto(
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
                equipmentPermission = listOf(
                    EquipmentPermission(SANITY_MEDICATION),
                    EquipmentPermission(INCENSE),
                    EquipmentPermission(UV_LIGHT),
                    EquipmentPermission(FLASHLIGHT),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.GLOW_IN_THE_DARK,
            map = SimpleMapResources.MapTitle.EDGEFIELD,
            description = GLOW_IN_THE_DARK,
            settingsModelDto = DifficultySettingsModelDto(
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                equipmentPermission = listOf(
                    EquipmentPermission(MOTION_SENSOR),
                    EquipmentPermission(FIRELIGHT),
                    EquipmentPermission(SOUND_RECORDER),
                    EquipmentPermission(FLASHLIGHT),
                    EquipmentPermission(VIDEO_CAMERA),
                    EquipmentPermission(TRIPOD),
                )
            )
        ),
        ChallengeResourceDto(
            name = ChallengeTitle.DEJA_VU,
            map = SimpleMapResources.MapTitle.TANGLEWOOD,
            description = DEJA_VU,
            settingsModelDto = DifficultySettingsModelDto(
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
        ChallengeResourceDto(
            name = ChallengeTitle.TAG_YOURE_IT,
            map = SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL,
            description = TAG_YOURE_IT,
            settingsModelDto = DifficultySettingsModelDto(
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
        challengeTitle = name,
        description = description,
        responseType = responseType,
        map = map,
        settingsModelDto = settingsModelDto
    )

}
