package com.tritiumgaming.shared.data.difficulty.model

data class DifficultyModel(
    val type: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType,
    val name: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle,
    val time: Long,
    val modifier: Float,
    val initialSanity: Float,
    val responseType: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType,
    val playerMedicationRestorationAmount: Int,
    val playerRequiredLevel: Int,
    val payoutCashRewardMultiplier: Int,
    val payoutExperienceRewardMultiplier: Int,
    val payoutInsurancePercent: Float,
    val ghostFrequencyRoam: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val ghostFrequencyRoomSwap: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val ghostFrequencyActivity: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val ghostFrequencyEvents: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val ghostHuntDuration: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val ghostHuntExtendedByKills: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val ghostHuntGracePeriod: Long,
    val ghostFingerprintDuration: Long,
    val contractDoorsStartOpen: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val contractHidingPlacesCount: com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.Frequency,
    val contractSanityMonitorAvailability: Boolean,
    val contractFuseBoxStartsEnabled: Boolean,
    val contractFuseBoxVisible: Boolean,
    val contractCursedPossessionCount: Int
)
