package com.tritiumgaming.data.challenges.usecase

import com.tritiumgaming.data.challenges.model.ChallengeModel
import com.tritiumgaming.shared.core.common.date.calcCycleIndex

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