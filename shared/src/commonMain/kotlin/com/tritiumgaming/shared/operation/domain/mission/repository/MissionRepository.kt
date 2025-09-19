package com.tritiumgaming.shared.operation.domain.mission.repository

import com.tritiumgaming.shared.operation.domain.mission.model.Mission

interface MissionRepository {

    fun getMissions(): Result<List<Mission>>

}