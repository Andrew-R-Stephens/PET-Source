package com.tritiumgaming.shared.data.challenge.usecase

import com.tritiumgaming.shared.core.common.date.calcCycleIndex
import com.tritiumgaming.shared.core.common.date.getCurrentDate
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.model.ChallengeModel

actual class GetCurrentChallengeUseCase actual constructor(
    private val useCase: GetChallengesUseCase
) {
    operator fun invoke(): Result<ChallengeModel> {
        val result = useCase()

        val challenges = result.getOrThrow()
        val count = challenges.size

        val date = getCurrentDate()
        val index = date.calcCycleIndex(count, 7)

        return Result.success(challenges[index])
    }

}