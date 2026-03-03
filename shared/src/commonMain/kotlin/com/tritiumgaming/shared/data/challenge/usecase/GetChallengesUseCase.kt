package com.tritiumgaming.shared.data.challenge.usecase

import com.tritiumgaming.shared.data.challenge.repository.ChallengeRepository

class GetChallengesUseCase(
    private val repository: ChallengeRepository
) {
    operator fun invoke() = repository.getChallenges()
}