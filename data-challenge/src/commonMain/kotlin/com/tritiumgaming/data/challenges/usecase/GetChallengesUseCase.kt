package com.tritiumgaming.data.challenges.usecase

import com.tritiumgaming.data.challenges.repository.ChallengeRepository

class GetChallengesUseCase(
    private val repository: ChallengeRepository
) {
    operator fun invoke() = repository.getChallenges()
}