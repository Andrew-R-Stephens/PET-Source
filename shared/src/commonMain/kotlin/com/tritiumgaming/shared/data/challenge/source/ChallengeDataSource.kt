package com.tritiumgaming.shared.data.challenge.source

import com.tritiumgaming.shared.data.challenge.dto.ChallengeModelDto

interface ChallengeDataSource {

    fun fetchChallenges(): Result<List<ChallengeModelDto>>

}
