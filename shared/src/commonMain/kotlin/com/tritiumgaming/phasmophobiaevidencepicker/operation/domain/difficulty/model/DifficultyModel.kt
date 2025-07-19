package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.Frequency

data class DifficultyModel(
    val type: DifficultyType,
    val name: DifficultyTitle,
    val time: Long,
    val modifier: Float,
    val initialSanity: Float,
    val responseType: DifficultyResponseType,
    val playerMedicationRestorationAmount: Int,
    val playerRequiredLevel: Int,
    val payoutCashRewardMultiplier: Int,
    val payoutExperienceRewardMultiplier: Int,
    val payoutInsurancePercent: Float,
    val ghostFrequencyRoam: Frequency,
    val ghostFrequencyRoomSwap: Frequency,
    val ghostFrequencyActivity: Frequency,
    val ghostFrequencyEvents: Frequency,
    val ghostHuntDuration: Frequency,
    val ghostHuntExtendedByKills: Frequency,
    val ghostHuntGracePeriod: Long,
    val ghostFingerprintDuration: Long,
    val contractDoorsStartOpen: Frequency,
    val contractHidingPlacesCount: Frequency,
    val contractSanityMonitorAvailability: Boolean,
    val contractFuseBoxStartsEnabled: Boolean,
    val contractFuseBoxVisible: Boolean,
    val contractCursedPossessionCount: Int
)
