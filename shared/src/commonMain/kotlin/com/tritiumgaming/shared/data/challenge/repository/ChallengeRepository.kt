package com.tritiumgaming.shared.data.challenge.repository

import com.tritiumgaming.shared.data.challenge.model.ChallengeModel

interface ChallengeRepository {

    fun getChallenges(): Result<List<ChallengeModel>>

}