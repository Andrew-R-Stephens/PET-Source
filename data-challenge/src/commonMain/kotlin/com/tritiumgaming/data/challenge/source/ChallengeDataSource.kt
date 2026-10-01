package com.tritiumgaming.data.challenge.source

import com.tritiumgaming.data.challenge.dto.ChallengeModelDto

interface ChallengeDataSource {

    fun fetchChallenges(): Result<List<ChallengeModelDto>>

}