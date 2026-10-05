package com.tritiumgaming.data.difficulty.source.local

import com.tritiumgaming.data.difficulty.dto.DifficultyModelDto
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource
import com.tritiumstudios.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumstudios.data.difficultysetting.dto.DifficultySettingsResourceModelDto
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.ActivityLevel
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.ActivityMonitor
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.ChangingFavoriteRoom
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

class DifficultyLocalDataSource: DifficultyDataSource {

    private val difficultyResourceDto = listOf(
        DifficultyResourceDto(
            type = DifficultyType.AMATEUR,
            difficultyTitle = DifficultyTitle.AMATEUR,
            responseType = GhostResponsePresentation.KNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_100,
                sanityPillRestoration = SanityPillRestoration.RESTORE_40,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_100,
                sprinting = Sprinting.ON,
                playerSpeed = PlayerSpeed.SPEED_100,
                flashlights = Flashlights.ON,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.MEDIUM,
                changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                activityLevel = ActivityLevel.HIGH,
                eventFrequency = EventFrequency.LOW,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_5,
                huntDuration = HuntDuration.LOW,
                killsExtendHunts = KillsExtendHunts.OFF,
                evidenceGiven = EvidenceGiven.COUNT_3,
                fingerprintChance = FingerprintChance.CHANCE_100,
                fingerprintDuration = FingerprintDuration.DURATION_120,
                setupTime = SetupTime.TIME_300,
                weather = Weather.RANDOM,
                doorsStartingOpen = DoorsStartingOpen.NONE,
                numberOfHidingPlaces = NumberOfHidingPlaces.VERY_HIGH,
                sanityMonitor = SanityMonitor.ON,
                activityMonitor = ActivityMonitor.ON,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.ON,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.ON,
                cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.INTERMEDIATE,
            difficultyTitle = DifficultyTitle.INTERMEDIATE,
            responseType = GhostResponsePresentation.KNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_100,
                sanityPillRestoration = SanityPillRestoration.RESTORE_35,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_150,
                sprinting = Sprinting.ON,
                playerSpeed = PlayerSpeed.SPEED_100,
                flashlights = Flashlights.ON,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.MEDIUM,
                changingFavouriteRoom = ChangingFavoriteRoom.NONE,
                activityLevel = ActivityLevel.MEDIUM,
                eventFrequency = EventFrequency.MEDIUM,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_4,
                huntDuration = HuntDuration.MEDIUM,
                killsExtendHunts = KillsExtendHunts.OFF,
                evidenceGiven = EvidenceGiven.COUNT_3,
                fingerprintChance = FingerprintChance.CHANCE_100,
                fingerprintDuration = FingerprintDuration.DURATION_120,
                setupTime = SetupTime.TIME_120,
                weather = Weather.RANDOM,
                doorsStartingOpen = DoorsStartingOpen.LOW,
                numberOfHidingPlaces = NumberOfHidingPlaces.HIGH,
                sanityMonitor = SanityMonitor.ON,
                activityMonitor = ActivityMonitor.ON,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.OFF,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.ON,
                cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.PROFESSIONAL,
            difficultyTitle = DifficultyTitle.PROFESSIONAL,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
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
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.OFF,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.ON,
                cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.NIGHTMARE,
            difficultyTitle = DifficultyTitle.NIGHTMARE,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_100,
                sanityPillRestoration = SanityPillRestoration.RESTORE_25,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
                sprinting = Sprinting.ON,
                playerSpeed = PlayerSpeed.SPEED_100,
                flashlights = Flashlights.ON,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.HIGH,
                changingFavouriteRoom = ChangingFavoriteRoom.MEDIUM,
                activityLevel = ActivityLevel.LOW,
                eventFrequency = EventFrequency.HIGH,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_2,
                huntDuration = HuntDuration.HIGH,
                killsExtendHunts = KillsExtendHunts.LOW,
                evidenceGiven = EvidenceGiven.COUNT_2,
                fingerprintChance = FingerprintChance.CHANCE_100,
                fingerprintDuration = FingerprintDuration.DURATION_120,
                setupTime = SetupTime.TIME_0,
                weather = Weather.RANDOM,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.LOW,
                sanityMonitor = SanityMonitor.OFF,
                activityMonitor = ActivityMonitor.OFF,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.OFF,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.OFF,
                cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.INSANITY,
            difficultyTitle = DifficultyTitle.INSANITY,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_75,
                sanityPillRestoration = SanityPillRestoration.RESTORE_20,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
                sprinting = Sprinting.ON,
                playerSpeed = PlayerSpeed.SPEED_100,
                flashlights = Flashlights.ON,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.HIGH,
                changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                activityLevel = ActivityLevel.LOW,
                eventFrequency = EventFrequency.HIGH,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_2,
                huntDuration = HuntDuration.HIGH,
                killsExtendHunts = KillsExtendHunts.LOW,
                evidenceGiven = EvidenceGiven.COUNT_1,
                fingerprintChance = FingerprintChance.CHANCE_100,
                fingerprintDuration = FingerprintDuration.DURATION_60,
                setupTime = SetupTime.TIME_0,
                weather = Weather.RANDOM,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.LOW,
                sanityMonitor = SanityMonitor.OFF,
                activityMonitor = ActivityMonitor.OFF,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.OFF,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.OFF,
                cursedPossessionsQuantity = CursedPossessionsQuantity.NONE
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.APOCALYPSE_1,
            difficultyTitle = DifficultyTitle.APOCALYPSE_1,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_75,
                sanityPillRestoration = SanityPillRestoration.RESTORE_20,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
                sprinting = Sprinting.OFF,
                playerSpeed = PlayerSpeed.SPEED_100,
                flashlights = Flashlights.ON,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.HIGH,
                changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                activityLevel = ActivityLevel.LOW,
                eventFrequency = EventFrequency.LOW,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_2,
                huntDuration = HuntDuration.HIGH,
                killsExtendHunts = KillsExtendHunts.HIGH,
                evidenceGiven = EvidenceGiven.COUNT_2,
                fingerprintChance = FingerprintChance.CHANCE_100,
                fingerprintDuration = FingerprintDuration.DURATION_60,
                setupTime = SetupTime.TIME_0,
                weather = Weather.RANDOM,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                sanityMonitor = SanityMonitor.ON,
                activityMonitor = ActivityMonitor.ON,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.ON,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.OFF,
                cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.APOCALYPSE_2,
            difficultyTitle = DifficultyTitle.APOCALYPSE_2,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_50,
                sanityPillRestoration = SanityPillRestoration.RESTORE_5,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
                sprinting = Sprinting.OFF,
                playerSpeed = PlayerSpeed.SPEED_75,
                flashlights = Flashlights.ON,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.HIGH,
                changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                activityLevel = ActivityLevel.LOW,
                eventFrequency = EventFrequency.LOW,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_2,
                huntDuration = HuntDuration.HIGH,
                killsExtendHunts = KillsExtendHunts.HIGH,
                evidenceGiven = EvidenceGiven.COUNT_1,
                fingerprintChance = FingerprintChance.CHANCE_100,
                fingerprintDuration = FingerprintDuration.DURATION_60,
                setupTime = SetupTime.TIME_0,
                weather = Weather.HEAVY_RAIN,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                sanityMonitor = SanityMonitor.OFF,
                activityMonitor = ActivityMonitor.OFF,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.BROKEN,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.OFF,
                cursedPossessionsQuantity = CursedPossessionsQuantity.NONE
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.APOCALYPSE_3,
            difficultyTitle = DifficultyTitle.APOCALYPSE_3,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto(
                startingSanity = StartingSanity.SANITY_0,
                sanityPillRestoration = SanityPillRestoration.RESTORE_0,
                sanityDrainSpeed = SanityDrainSpeed.SPEED_200,
                sprinting = Sprinting.OFF,
                playerSpeed = PlayerSpeed.SPEED_50,
                flashlights = Flashlights.OFF,
                loseItemsAndConsumables = LoseItemsAndConsumables.ON,
                ghostSpeed = GhostSpeed.SPEED_100,
                roamingFrequency = RoamingFrequency.HIGH,
                changingFavouriteRoom = ChangingFavoriteRoom.HIGH,
                activityLevel = ActivityLevel.LOW,
                eventFrequency = EventFrequency.LOW,
                friendlyGhost = FriendlyGhost.OFF,
                gracePeriod = GracePeriod.PERIOD_0,
                huntDuration = HuntDuration.HIGH,
                killsExtendHunts = KillsExtendHunts.HIGH,
                evidenceGiven = EvidenceGiven.COUNT_0,
                fingerprintChance = FingerprintChance.CHANCE_0,
                fingerprintDuration = FingerprintDuration.DURATION_NEVER,
                setupTime = SetupTime.TIME_0,
                weather = Weather.HEAVY_RAIN,
                doorsStartingOpen = DoorsStartingOpen.HIGH,
                numberOfHidingPlaces = NumberOfHidingPlaces.NONE,
                sanityMonitor = SanityMonitor.OFF,
                activityMonitor = ActivityMonitor.OFF,
                fuseBoxAtStartOfContract = FuseBoxAtStartOfContract.BROKEN,
                fuseBoxVisibleOnMap = FuseBoxVisibleOnMap.OFF,
                cursedPossessionsQuantity = CursedPossessionsQuantity.NONE
            )
        ),
        DifficultyResourceDto(
            type = DifficultyType.CHALLENGE,
            difficultyTitle = DifficultyTitle.CHALLENGE,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto()
        ),
        DifficultyResourceDto(
            type = DifficultyType.CUSTOM,
            difficultyTitle = DifficultyTitle.CUSTOM,
            responseType = GhostResponsePresentation.UNKNOWN,
            settingsModelDto = DifficultySettingsResourceModelDto()
        ),
    )

    override fun fetchDifficulties(): Result<List<DifficultyModelDto>> =
        Result.success(difficultyResourceDto.toDifficultyModelDto())

    private fun List<DifficultyResourceDto>.toDifficultyModelDto() = map{ it.toDifficultyModelDto() }

    private fun DifficultyResourceDto.toDifficultyModelDto() = DifficultyModelDto(
        type = type,
        difficultyTitle = difficultyTitle,
        responseType = responseType,
        settingsModelDto = settingsModelDto.toDifficultySettingsModelDto()
    )

    private data class DifficultyResourceDto(
        val type: DifficultyType,
        val difficultyTitle: DifficultyTitle,
        val responseType: GhostResponsePresentation,
        val settingsModelDto: DifficultySettingsResourceModelDto
    ) {
        fun DifficultySettingsResourceModelDto.toDifficultySettingsModelDto() =
            DifficultySettingsModelDto(
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
    }


}
