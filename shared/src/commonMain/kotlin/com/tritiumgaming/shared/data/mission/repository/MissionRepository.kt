package com.tritiumgaming.shared.data.mission.repository

import com.tritiumgaming.shared.data.mission.model.Mission

interface MissionRepository {

    fun getMissions(): Result<List<Mission>>

}