package com.tritiumgaming.shared.data.challenge.repository

import com.tritiumgaming.shared.data.challenge.dto.toDomain
import com.tritiumgaming.shared.data.challenge.model.ChallengeModel
import com.tritiumgaming.shared.data.challenge.source.ChallengeDataSource

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
