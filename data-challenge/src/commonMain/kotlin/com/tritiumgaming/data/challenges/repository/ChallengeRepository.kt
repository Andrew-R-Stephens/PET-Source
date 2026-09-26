package com.tritiumgaming.data.challenges.repository

import com.tritiumgaming.data.challenges.model.ChallengeModel

interface ChallengeRepository {

    fun getChallenges(): Result<List<ChallengeModel>>

}