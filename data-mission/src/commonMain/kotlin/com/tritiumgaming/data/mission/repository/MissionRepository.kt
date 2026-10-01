package com.tritiumgaming.data.mission.repository

import com.tritiumgaming.data.mission.model.Mission

interface MissionRepository {

    fun getMissions(): Result<List<Mission>>

}