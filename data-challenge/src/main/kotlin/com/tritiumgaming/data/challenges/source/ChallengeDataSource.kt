package com.tritiumgaming.data.challenges.source

import com.tritiumgaming.data.challenges.dto.ChallengeModelDto

interface ChallengeDataSource {

    fun fetchChallenges(): Result<List<ChallengeModelDto>>

}