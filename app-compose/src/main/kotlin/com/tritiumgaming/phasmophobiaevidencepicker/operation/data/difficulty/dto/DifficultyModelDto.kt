package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto

import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.Frequency
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel

data class DifficultyModelDto(
    val index: Int,
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

fun DifficultyModelDto.toDomain() = DifficultyModel(
    type = DifficultyType.entries[index],
    name = name,
    time = time,
    modifier = modifier,
    initialSanity = initialSanity,
    responseType = responseType,
    playerMedicationRestorationAmount = playerMedicationRestorationAmount,
    playerRequiredLevel = playerRequiredLevel,
    payoutCashRewardMultiplier = payoutCashRewardMultiplier,
    payoutExperienceRewardMultiplier = payoutExperienceRewardMultiplier,
    payoutInsurancePercent = payoutInsurancePercent,
    ghostFrequencyRoam = ghostFrequencyRoam,
    ghostFrequencyRoomSwap = ghostFrequencyRoomSwap,
    ghostFrequencyActivity = ghostFrequencyActivity,
    ghostFrequencyEvents = ghostFrequencyEvents,
    ghostHuntDuration = ghostHuntDuration,
    ghostHuntExtendedByKills = ghostHuntExtendedByKills,
    ghostHuntGracePeriod = ghostHuntGracePeriod,
    ghostFingerprintDuration = ghostFingerprintDuration,
    contractDoorsStartOpen = contractDoorsStartOpen,
    contractHidingPlacesCount = contractHidingPlacesCount,
    contractSanityMonitorAvailability = contractSanityMonitorAvailability,
    contractFuseBoxStartsEnabled = contractFuseBoxStartsEnabled,
    contractFuseBoxVisible = contractFuseBoxVisible,
    contractCursedPossessionCount = contractCursedPossessionCount
)

fun List<DifficultyModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
