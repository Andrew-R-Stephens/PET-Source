package com.tritiumgaming.data.challenges.usecase

import com.tritiumgaming.core.common.date.calcCycleIndex
import com.tritiumgaming.data.challenges.model.ChallengeModel

actual class GetCurrentChallengeUseCase actual constructor(
    private val useCase: GetChallengesUseCase
) {
    operator fun invoke(): Result<ChallengeModel> {
        val result = useCase()

        val challenges = result.getOrThrow()
        val count = challenges.size

        val index = calcCycleIndex(count)

        return Result.success(challenges[index])
    }

}