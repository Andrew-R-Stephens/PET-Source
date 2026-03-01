package com.tritiumgaming.data.challenges.source

import com.tritiumgaming.data.challenges.dto.ChallengeModelDto
import com.tritiumgaming.data.difficulty.dto.DifficultyModelDto

interface ChallengeDataSource {

    fun fetchChallenges(): Result<List<ChallengeModelDto>>

}