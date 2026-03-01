package com.tritiumgaming.data.difficulty.source.local

import com.tritiumgaming.data.difficulty.dto.DifficultyModelDto
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
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

class DifficultyLocalDataSource: DifficultyDataSource {

    private val difficultyResourceDto
        get() = listOf(
            DifficultyResourceDto(
                index = 0,
                name = DifficultyTitle.AMATEUR,
                responseType = DifficultyResponseType.KNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.ON,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 1,
                name = DifficultyTitle.INTERMEDIATE,
                responseType = DifficultyResponseType.KNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 2,
                name = DifficultyTitle.PROFESSIONAL,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                )
            ),
            DifficultyResourceDto(
                index = 3,
                name = DifficultyTitle.NIGHTMARE,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 4,
                name = DifficultyTitle.INSANITY,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.OFF,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 5,
                name = DifficultyTitle.APOCALYPSE_1,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.ON,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 6,
                name = DifficultyTitle.APOCALYPSE_2,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 7,
                name = DifficultyTitle.APOCALYPSE_3,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto(
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
                    fuseBoxAtStartOfContract = FuzeBoxAtStartOfContract.BROKEN,
                    fuseBoxVisibleOnMap = FuzeBoxVisibleOnMap.OFF,
                    cursedPossessionsQuantity = CursedPossessionsQuantity.NONE,
                    cursedPossession1 = CursedPossession.RANDOM,
                )
            ),
            DifficultyResourceDto(
                index = 8,
                name = DifficultyTitle.CUSTOM,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto()
            ),
            DifficultyResourceDto(
                index = 9,
                name = DifficultyTitle.CHALLENGE,
                responseType = DifficultyResponseType.UNKNOWN,
                difficultyResourceSettingsModelDto = DifficultyResourceSettingsModelDto()
            ),
        )

    override fun fetchDifficulties(): Result<List<DifficultyModelDto>> =
        Result.success(difficultyResourceDto.toDifficultyModelDto())

    private fun List<DifficultyResourceDto>.toDifficultyModelDto() = map{ it.toDifficultyModelDto() }

    private fun DifficultyResourceDto.toDifficultyModelDto() = DifficultyModelDto(
        index = index,
        name = name,
        responseType = responseType,
        difficultySettingsModelDto = difficultyResourceSettingsModelDto.toDifficultySettingsModelDto()
    )

    private data class DifficultyResourceDto(
        val index: Int,
        val name: DifficultyTitle,
        val responseType: DifficultyResponseType,
        val difficultyResourceSettingsModelDto: DifficultyResourceSettingsModelDto
    ) {
        fun DifficultyResourceSettingsModelDto.toDifficultySettingsModelDto() = DifficultyModelDto.DifficultySettingsModelDto(
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
        )
    }

    data class DifficultyResourceSettingsModelDto(
        val startingSanity: StartingSanity = StartingSanity.SANITY_100,
        val sanityPillRestoration: SanityPillRestoration = SanityPillRestoration.RESTORE_40,
        val sanityDrainSpeed: SanityDrainSpeed = SanityDrainSpeed.SPEED_100,
        val sprinting: Sprinting = Sprinting.ON,
        val playerSpeed: PlayerSpeed = PlayerSpeed.SPEED_100,
        val flashlights: Flashlights = Flashlights.ON,
        val loseItemsAndConsumables: LoseItemsAndConsumables = LoseItemsAndConsumables.ON,
        val ghostSpeed: GhostSpeed = GhostSpeed.SPEED_100,
        val roamingFrequency: RoamingFrequency = RoamingFrequency.MEDIUM,
        val changingFavouriteRoom: ChangingFavoriteRoom = ChangingFavoriteRoom.NONE,
        val activityLevel: ActivityLevel = ActivityLevel.HIGH,
        val eventFrequency: EventFrequency = EventFrequency.LOW,
        val friendlyGhost: FriendlyGhost = FriendlyGhost.OFF,
        val gracePeriod: GracePeriod = GracePeriod.PERIOD_5,
        val huntDuration: HuntDuration = HuntDuration.LOW,
        val killsExtendHunts: KillsExtendHunts = KillsExtendHunts.OFF,
        val evidenceGiven: EvidenceGiven = EvidenceGiven.COUNT_3,
        val fingerprintChance: FingerprintChance = FingerprintChance.CHANCE_100,
        val fingerprintDuration: FingerprintDuration = FingerprintDuration.DURATION_120,
        val setupTime: SetupTime = SetupTime.TIME_300,
        val weather: Weather = Weather.RANDOM,
        val doorsStartingOpen: DoorsStartingOpen = DoorsStartingOpen.NONE,
        val numberOfHidingPlaces: NumberOfHidingPlaces = NumberOfHidingPlaces.VERY_HIGH,
        val sanityMonitor: SanityMonitor = SanityMonitor.ON,
        val activityMonitor: ActivityMonitor = ActivityMonitor.ON,
        val fuseBoxAtStartOfContract: FuzeBoxAtStartOfContract = FuzeBoxAtStartOfContract.ON,
        val fuseBoxVisibleOnMap: FuzeBoxVisibleOnMap = FuzeBoxVisibleOnMap.ON,
        val cursedPossessionsQuantity: CursedPossessionsQuantity = CursedPossessionsQuantity.QUANTITY_1,
        val cursedPossession1: CursedPossession = CursedPossession.RANDOM,
        val cursedPossession2: CursedPossession = CursedPossession.RANDOM,
        val cursedPossession3: CursedPossession = CursedPossession.RANDOM,
        val cursedPossession4: CursedPossession = CursedPossession.RANDOM,
        val cursedPossession5: CursedPossession = CursedPossession.RANDOM,
        val cursedPossession6: CursedPossession = CursedPossession.RANDOM,
        val cursedPossession7: CursedPossession = CursedPossession.RANDOM,
    )
}
