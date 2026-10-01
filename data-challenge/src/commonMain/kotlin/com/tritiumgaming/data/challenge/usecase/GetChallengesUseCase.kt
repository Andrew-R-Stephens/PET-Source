package com.tritiumgaming.data.challenge.usecase

import com.tritiumgaming.data.challenge.repository.ChallengeRepository

class GetChallengesUseCase(
    private val repository: ChallengeRepository
) {
    operator fun invoke() = repository.getChallenges()
}