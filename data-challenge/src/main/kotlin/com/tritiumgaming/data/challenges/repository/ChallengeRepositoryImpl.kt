package com.tritiumgaming.data.challenges.repository

import com.tritiumgaming.data.challenges.dto.toDomain
import com.tritiumgaming.data.challenges.source.ChallengeDataSource
import com.tritiumgaming.shared.data.challenge.model.ChallengeModel
import com.tritiumgaming.shared.data.challenge.repository.ChallengeRepository

class ChallengeRepositoryImpl(
    val localSource: ChallengeDataSource
): ChallengeRepository {

    var challenges: List<ChallengeModel> = emptyList()

    override fun getChallenges(): Result<List<ChallengeModel>> {

        if(challenges.isEmpty()) {
            challenges = localSource.fetchChallenges()
                .map { dto -> dto.toDomain() }
                .getOrDefault(emptyList())
        }

        return Result.success(challenges)
    }

}