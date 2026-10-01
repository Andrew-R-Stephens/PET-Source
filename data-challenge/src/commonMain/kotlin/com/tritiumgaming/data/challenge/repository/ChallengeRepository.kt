package com.tritiumgaming.data.challenge.repository

import com.tritiumgaming.data.challenge.model.ChallengeModel

interface ChallengeRepository {

    fun getChallenges(): Result<List<ChallengeModel>>

}